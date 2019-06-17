package mods.skipsign;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class ModLog {
    private static final Logger logger = LogManager.getLogger();

    public static void Log(String str) {
        ModLog.logger.log(Level.INFO, str);
    }

    public static void Error(String str) {
        ModLog.logger.log(Level.FATAL, str);
    }

}