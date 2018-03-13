package com.loz.feeder.service;

import com.loz.feeder.exception.TwitterAccessException;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;

@Service
public class TwitterAccessTokenService {

    @Value("${twitter.consumerKey}")
    private String key;
    @Value("${twitter.consumerSecret}")
    private String secret;
    @Value("${twitter.url.get_token}")
    private String urlGetToken;

    private String token;
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(TwitterAccessTokenService.class);

    @Value("${facebook.url.get_token}")
    private String URL_GET_TOKEN;

    public String getToken() {
        if (token == null) {
            try {
                token = getNewToken();
            } catch (TwitterAccessException e) {
                LOGGER.error("Twitter Access token not found with credentials");
            }
        }
        return token;
    }

    private String getNewToken() throws TwitterAccessException {
        String bearerTokenEncoded;
        try {
            String bearerToken = URLEncoder.encode(key, "UTF-8")+":"+URLEncoder.encode(secret, "UTF-8");
            byte[] bytesEncoded = Base64.encodeBase64(bearerToken.getBytes());
            bearerTokenEncoded = new String(bytesEncoded);
        } catch (UnsupportedEncodingException e) {
            throw new TwitterAccessException(e.getMessage());
        }
        if (bearerTokenEncoded == null) {
            throw new TwitterAccessException("Cannot create bearer token");
        }
        URL obj = null;
        try {
            obj = new URL(urlGetToken);
            LOGGER.info("Using URL "+urlGetToken);
        } catch (MalformedURLException e) {
            throw new TwitterAccessException("Cannot create URL from "+urlGetToken);
        }
        HttpsURLConnection con = null;
        try {
            con = (HttpsURLConnection) obj.openConnection();
        } catch (IOException e) {
            throw new TwitterAccessException("Cannot open HTTPS connection to "+urlGetToken);
        }
        try {
            con.setRequestMethod("POST");
        } catch (ProtocolException e) {
            throw new TwitterAccessException("Protocol exception, cannot handle POST");
        }
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        con.setRequestProperty("Authorization", "Basic "+bearerTokenEncoded);

        String parameters = "grant_type=client_credentials";
        byte[] postData = parameters.getBytes( Charset.forName("UTF-8"));

        con.setDoOutput(true);
        DataOutputStream wr = null;
        try {
            wr = new DataOutputStream(con.getOutputStream());
            wr.write(postData);
            wr.flush();
            wr.close();
            BufferedReader in = null;
            in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));

            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            BasicJsonParser parser = new BasicJsonParser();
            Map<String, Object> json = parser.parseMap(response.toString());
            String accessToken = json.get("access_token").toString();
            String tokenType = json.get("token_type").toString();
            if (!tokenType.equals("bearer")) {
                 throw new TwitterAccessException("Token type received was not 'bearer'");
            }
            return accessToken;
        } catch (IOException e) {
            throw new TwitterAccessException("Stream error");
        }
    }
}

