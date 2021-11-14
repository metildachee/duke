import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

public class Logger {
    java.util.logging.Logger logger;

    public Logger() {

    }

    public void init(String path) {
        logger = java.util.logging.Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
        logger.setLevel(Level.FINEST);
        try {
            FileHandler fileTxt = new FileHandler(path);

            SimpleFormatter formatterTxt = new SimpleFormatter();
            fileTxt.setFormatter(formatterTxt);
            logger.addHandler(fileTxt);
        } catch (IOException e) {
            logger.finest("init logger got error");
        }
    }

    public java.util.logging.Logger getLogger() {
        return logger;
    }

    public void info(String msg) {
        logger.finest(msg);
    }
}
