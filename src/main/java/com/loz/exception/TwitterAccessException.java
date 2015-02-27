package com.loz.exception;

import java.io.UnsupportedEncodingException;

/**
 * Created by loz on 05/02/15.
 */
public class TwitterAccessException extends Exception {
    public TwitterAccessException() {}

    public TwitterAccessException(String message)
    {
        super(message);
    }
}
