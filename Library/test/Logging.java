import java.io.IOException;
import java.util.logging.*;

public class Logging {
    public static void main(String[] args) throws IOException {

        Logger logger = Logger.getLogger("lk.ijse.");
        logger.setLevel(Level.INFO);
        logger.setUseParentHandlers(false);
        FileHandler fileHandler = new FileHandler("error.log", true);
        fileHandler.setFormatter(new SimpleFormatter());
        //logger.addHandler(fileHandler);
        logger.addHandler(new ConsoleHandler());
        //System.out.println(logger.getParent());
        try {
            throw new RuntimeException();
        } catch (Exception e) {
            logger.log(Level.FINE, null, e);
        }
    }

}
