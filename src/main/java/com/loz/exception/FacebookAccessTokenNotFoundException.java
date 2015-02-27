package com.loz.exception;

public class FacebookAccessTokenNotFoundException extends FacebookAccessException {
    public FacebookAccessTokenNotFoundException() {}

    public FacebookAccessTokenNotFoundException(String message)
    {
        super(message);
    }
}
