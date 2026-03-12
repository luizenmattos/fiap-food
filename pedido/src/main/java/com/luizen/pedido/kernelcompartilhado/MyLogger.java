package com.luizen.pedido.kernelcompartilhado;

import java.util.logging.Logger;

public class MyLogger {

    @SuppressWarnings("unused")
    private Logger logger;

    public static MyLogger getInstance(Class<?> clazz) {
         MyLogger myLogger = new MyLogger();
         myLogger.logger = Logger.getLogger(clazz.getName());
         return myLogger;
    }

    public void error(MyLoggerMessage message) {
        System.err.println(message.getStructuredLogging());
    }

    public void info(MyLoggerMessage message) {
        System.out.println(message.getStructuredLogging());
    }
}
