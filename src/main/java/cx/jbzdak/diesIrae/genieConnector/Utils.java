package cx.jbzdak.diesIrae.genieConnector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
public class Utils { 
    public static Logger getLogger(){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return LoggerFactory.getLogger(ste.getClassName());
    }
}
