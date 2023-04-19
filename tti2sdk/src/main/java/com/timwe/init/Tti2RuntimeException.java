package com.timwe.init;

import androidx.annotation.Keep;

@Keep
public class Tti2RuntimeException extends RuntimeException {

    public Tti2RuntimeException() {
        super();
    }

    public Tti2RuntimeException(String message) {
        super(message);
    }

    public Tti2RuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public Tti2RuntimeException(Throwable cause) {
        super(cause);
    }

    protected Tti2RuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
