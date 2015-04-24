package com.loz.service;

import com.loz.dao.MessageDao;
import com.loz.dao.TweetDao;
import com.loz.dao.model.MessageData;
import com.loz.dao.model.TweetData;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MessageService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    MessageDao messageDao;

    public List<MessageData> getMessages(Pageable pageable) {
        return messageDao.findMessagesOrderByDateDesc(pageable);
    }

    public long getTotal() {
        return messageDao.count();
    }

    public MessageData saveMessage(String name, String text, String profilePic) {
        MessageData messageData = new MessageData();
        messageData.setCreatedDate(new Date());
        messageData.setName(name);
        messageData.setProfilePic(profilePic);
        if (passesProfanityFilter(text)) {
            messageData.setText(text);
        } else {
            messageData.setText("Message removed by profanity filter");
        }
        MessageData saved = messageDao.save(messageData);
        return saved;
    }

    private boolean passesProfanityFilter(String text) {
        text = text.toLowerCase();
        if (text.indexOf("fuck") != -1) {
            return false;
        }
        if (text.indexOf("cunt") != -1) {
            return false;
        }
        if (text.indexOf("nigger") != -1) {
            return false;
        }
        if (text.indexOf("bitch") != -1) {
            return false;
        }
        if (text.indexOf("whore") != -1) {
            return false;
        }
        if (text.indexOf("shit") != -1) {
            return false;
        }
        return true;
    }


}

