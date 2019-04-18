package com.rngay.common.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogService {

    private final static Logger LOGGER_FAIL = LoggerFactory.getLogger("failLogger");
    private final static Logger LOGGER_DEBUG = LoggerFactory.getLogger("debugLogger");
    private final static Logger LOGGER_INFO = LoggerFactory.getLogger("infoLogger");

    public static void error(String message) {
        LOGGER_FAIL.error(message);
    }

    public static void error(Throwable e) {
        LOGGER_FAIL.error("", e);
    }

    public static void error(String message, Throwable e) {
        LOGGER_FAIL.error(message, e);
    }

    public static void debug(String message) {
        LOGGER_DEBUG.debug(message);
    }

    public static void debug(String message, Throwable e) {
        LOGGER_DEBUG.debug(message, e);
    }

    public static void info(String message) {
        LOGGER_INFO.info(message);
    }

    public static void info(String loggerName, String message) {
        LoggerFactory.getLogger(loggerName).info(message);
    }

}
