package com.gail.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ExceptionUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionUtil.class);

    public static void handleException(ErrorDetails errorDetails, Throwable throwable, String params) throws GailNominationServiceException {
        if (throwable instanceof GailNominationServiceException) {
            GailNominationServiceException mpe = (GailNominationServiceException) throwable;
            LOGGER.error("Error occured with " + mpe.getErrorDetails());
            if (params != null) {
                LOGGER.error("Method Params : " + params);
            }
            LOGGER.error("Error occured :", throwable);
            throw mpe;
        }

        LOGGER.error("Error occured with " + errorDetails);
        LOGGER.error("Method Params : " + params);
        LOGGER.error("Error occured :", throwable);

        errorDetails.setMessage(errorDetails.getMessage() + " : "+ throwable.getMessage());
        throw new GailNominationServiceException(errorDetails, throwable);
    }

}
