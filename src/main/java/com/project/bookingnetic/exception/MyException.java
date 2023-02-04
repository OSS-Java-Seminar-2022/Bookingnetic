package com.project.bookingnetic.exception;

import java.io.Serializable;

public class MyException extends Exception implements Serializable
{
    private static final long serialVersionUID = 1L;

    public MyException() {
        super();
    }
    public MyException(String msg) {
        super(msg);
    }
    public MyException(String msg, Exception e)  {
        super(msg, e);
    }
}
