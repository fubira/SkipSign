import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class ModLog {
    private static final Logger logger = LogManager.getLogger();

    public static void Log(String str) {
        Logger.logger.log(Level.INFO, str);
    }

    public static void Error(String str) {
        Logger.logger.log(Level.FATAL, str);
    }

}