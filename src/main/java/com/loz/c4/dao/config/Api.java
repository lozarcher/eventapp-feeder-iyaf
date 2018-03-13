package com.loz.c4.dao.config;

/**
 * Created by loz on 05/10/2017.
 */
public class Api {
    private RequestParameters requestParameters;
    private RequestHeaders requestHeaders;

    public RequestParameters getRequestParameters() {
        return requestParameters;
    }

    public void setRequestParameters(RequestParameters requestParameters) {
        this.requestParameters = requestParameters;
    }

    public RequestHeaders getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(RequestHeaders requestHeaders) {
        this.requestHeaders = requestHeaders;
    }
}
