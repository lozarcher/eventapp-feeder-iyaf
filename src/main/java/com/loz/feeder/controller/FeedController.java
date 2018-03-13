package com.loz.feeder.controller;

import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.loz.feeder.dao.model.GalleryData;
import com.loz.feeder.dao.model.PostData;
import com.loz.feeder.dao.model.TweetData;
import com.loz.feeder.dao.responseVo.*;
import com.loz.feeder.service.*;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.http.HttpResponse;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class FeedController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FeedController.class);

    @Value("${twitter.pagesize}")
    private int PAGESIZE;

    @Value("${s3.key.access}")
    private String s3AccessKey;

    @Value("${s3.key.secret}")
    private String s3SecretKey;

    @Value("${s3.folder}")
    private String s3Folder;

    @Value("${s3.url}")
    private String s3Url;

    @Value("${s3.bucket}")
    private String s3Bucket;

    @Autowired
    FacebookService facebookService;

    @Autowired
    TwitterService twitterService;

    @Autowired
    VoucherService voucherService;

    @Autowired
    TwitterFeedService twitterFeedService;


    @RequestMapping(value="/events", produces = "application/json")
    @ResponseBody
    public EventResponse events() {
        EventResponse response = new EventResponse();
        response.setDate(new Date());
        response.setData(facebookService.getEvents());
        // response.setData(new ArrayList<EventData>());   // Empty response for testing UI
        return response;
    }

    @RequestMapping(value="/vouchers", produces = "application/json")
    @ResponseBody
    public VoucherResponse vouchers() {
        VoucherResponse response = new VoucherResponse();
        response.setDate(new Date());
        response.setData(voucherService.getVouchers());
        return response;
    }

    @RequestMapping(value="/posts/{offset}", produces = "application/json")
    @ResponseBody
    public PostResponse postsPaginated(@PathVariable("offset") int page) {
        PostResponse response = new PostResponse();
        response.setDate(new Date());
        Pageable pageable = new PageRequest(page, PAGESIZE);
        List<PostData> posts = facebookService.getPosts(pageable);
        response.setData(posts);
        long total = facebookService.getTotalPosts();
        if ((page * PAGESIZE) + posts.size() < total) {
            page++;
            response.setNext("/posts/"+page);
        } else {
            response.setNext(null);
        }
        return response;
    }

    @RequestMapping(value="/posts", produces = "application/json")
    @ResponseBody
    public PostResponse posts() {
        return postsPaginated(0);
    }

    @RequestMapping(value="/tweets/{offset}", produces = "application/json")
    @ResponseBody
    public TweetResponse tweetsPaginated(@PathVariable("offset") int page) {
        TweetResponse response = new TweetResponse();
        response.setDate(new Date());
        Pageable pageable = new PageRequest(page, PAGESIZE);
        List<TweetData> tweets = twitterService.getTweets(pageable);
        response.setData(tweets);
        long total = twitterService.getTotal();
        if ((page * PAGESIZE) + tweets.size() < total) {
            page++;
            response.setNext("/tweets/"+page);
        } else {
            response.setNext(null);
        }
        return response;
    }

    @RequestMapping(value="/tweets", produces = "application/json")
    @ResponseBody
    public TweetResponse tweets() {
        return tweetsPaginated(0);
    }

    @RequestMapping("/traders")
    @ResponseBody
    public PageResponse performers() {
        PageResponse response = new PageResponse();
        response.setDate(new Date());
        response.setData(facebookService.getPerformers());
        return response;
    }

    @RequestMapping(value="/info", produces = "application/json")
    @ResponseBody
    public InfoResponse info() {
        InfoResponse response = new InfoResponse();
        response.setDate(new Date());
        response.setData(facebookService.getInfo());
        return response;
    }

    @RequestMapping(value="/venues", produces = "application/json")
    @ResponseBody
    public VenueResponse venues() {
        VenueResponse response = new VenueResponse();
        response.setDate(new Date());
        response.setData(facebookService.getVenues());
        return response;
    }

//    public String handleFileUpload(@RequestParam("name") String name,
//                                   @RequestParam("file") MultipartFile file,
//                                   RedirectAttributes redirectAttributes) {
//
//    }
//
    @RequestMapping(method = RequestMethod.POST, value = "/gallery", produces = "application/json")
    @ResponseBody
    public GalleryResponse handleFileUpload(@RequestParam("filename") String filename,
                                            @RequestParam("name") String name,
                                            @RequestParam("caption") String caption,
                                         @RequestParam("photo") MultipartFile multipartFile) {

        LOGGER.info("Posting gallery image");

        if (filename.contains("/")) {
            throw new RuntimeException("invalid name");
        }

        if (!multipartFile.isEmpty()) {
            try {
                LOGGER.info("Gallery image not empty");

//                BufferedOutputStream stream = new BufferedOutputStream(
//                        new FileOutputStream(new File(Application.ROOT + "/" + name)));
//                FileCopyUtils.copy(file.getInputStream(), stream);
                //stream.close();
                LOGGER.debug("User name :"+name);
                LOGGER.debug("Caption :"+caption);
                LOGGER.debug("Filename :"+filename);
                LOGGER.debug("File :"+multipartFile.getContentType()+" "+multipartFile.getSize());

                System.setProperty(SDKGlobalConfiguration.ENFORCE_S3_SIGV4_SYSTEM_PROPERTY, "true");

                AWSCredentials credentials = new BasicAWSCredentials(
                        s3AccessKey,
                        s3SecretKey
                );

                LOGGER.debug("credentials "+credentials.toString());

                AmazonS3 s3client = AmazonS3ClientBuilder
                        .standard()
                        .withCredentials(new AWSStaticCredentialsProvider(credentials))
                        .withRegion(Regions.EU_WEST_2)
                        .build();

                String s3Filename = s3Folder+"/"+filename;

                File s3File = multipartToFile(multipartFile);
//                PutObjectResult result = s3client.putObject(new PutObjectRequest(s3Bucket, s3Filename,
//                        s3File)
//                        .withCannedAcl(CannedAccessControlList.PublicRead));

                PutObjectResult result = s3client.putObject(new PutObjectRequest(
                        s3Bucket,
                        s3Filename,
                        s3File
                ).withCannedAcl(CannedAccessControlList.PublicRead));;

                LOGGER.debug("Result: "+result.toString());
                String url = s3Url+s3Bucket+"/"+s3Filename;
                LOGGER.debug("URL: "+url);

                String previewFilename = filename.replaceFirst("\\.", "_thumb.");
                String s3PreviewFilename = s3Folder+"/"+previewFilename;

                File thumbFile = new File( System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") +
                        previewFilename);
                Thumbnails.of(s3File)
                        .size(300, 300)
                        .toFile(thumbFile);
                String thumbUrl = s3Url+s3Bucket+"/"+s3PreviewFilename;

                PutObjectResult thumbResult = s3client.putObject(new PutObjectRequest(s3Bucket, s3PreviewFilename,
                        thumbFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead));

                LOGGER.debug("Result: "+thumbResult.toString());
                LOGGER.debug("Thumb URL: "+thumbUrl);

                GalleryData galleryData = new GalleryData();
                galleryData.setCaption(caption);
                galleryData.setUser(name);
                galleryData.setCreatedDate(new Date());
                galleryData.setPicture(url);
                galleryData.setThumb(thumbUrl);
                LOGGER.debug("Saving gallery data: "+galleryData.toString());

                facebookService.saveGallery(galleryData);
                return gallery();
            }
            catch (Exception e) {
                throw new RuntimeException("Cannot save image: "+e.getMessage());
            }
        }
        else {
            throw new RuntimeException("File is empty ");
        }
    }

    public File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException
    {
        File convFile = new File( System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") +
                multipart.getOriginalFilename());
        multipart.transferTo(convFile);
        return convFile;
    }

    @RequestMapping(value = "/gallery", produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public GalleryResponse gallery() {
        LOGGER.info("Getting gallery data");
        GalleryResponse response = new GalleryResponse();
        response.setDate(new Date());
        response.setData(facebookService.getGallery());
        return response;
    }

    @RequestMapping(value="/iyaf.ics", produces="text/calendar")
    @ResponseBody
    public String iCal() {
        return facebookService.getEventsAsICal();
    }

}
