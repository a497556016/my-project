package com.heshaowei.myproj.flowable.display.service.exception;

public class FlowableServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public FlowableServiceException(String message) {
        super(message);
    }

    public FlowableServiceException(String message, Throwable t) {
        super(message, t);
    }
}
