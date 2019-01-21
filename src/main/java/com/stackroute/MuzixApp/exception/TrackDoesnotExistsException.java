package com.stackroute.MuzixApp.exception;

public class TrackDoesnotExistsException extends Exception {


    private String message;


    public TrackDoesnotExistsException(){}

    public TrackDoesnotExistsException(String message) {
        super(message);
        this.message = message;
    }

}
