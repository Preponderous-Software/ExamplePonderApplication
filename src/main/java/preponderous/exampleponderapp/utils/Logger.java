package preponderous.exampleponderapp.utils;

import preponderous.exampleponderapp.ExamplePonderApplication;

public class Logger {
    private static Logger instance;

    private Logger() {

    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void log(String message) {
        if (ExamplePonderApplication.getInstance().isDebugEnabled()) {
            System.out.println("[DEBUG] " + message);
        }
    }
}