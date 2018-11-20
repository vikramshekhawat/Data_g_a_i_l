package com.gail.utility;



public class GailNominationServiceException extends Throwable{


    private static final long serialVersionUID = 1L;
    private ErrorDetails errorDetails;
    private Throwable throwable;


    public GailNominationServiceException(ErrorDetails errorDetails,Throwable throwable) {
        super();
        this.errorDetails = errorDetails;
        this.throwable = throwable;
    }
    public GailNominationServiceException(ErrorDetails errorDetails) {
        super();
        this.errorDetails = errorDetails;
        this.throwable = null;
    }

    public ErrorDetails getErrorDetails() {
        return errorDetails;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}
