package com.example.transportpassmanagementsystem.exception;

import java.util.List;

public class TransportException extends RuntimeException{
    public TransportException(String message){
        super(message);
    }
    public TransportException(String message, Throwable cause){
        super(message, cause);
    }
    public TransportException(List<String> messages, Throwable cause){
        super(String.join(",",messages), cause);
    }
}
