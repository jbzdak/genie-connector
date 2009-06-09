package cx.jbzdak.diesIrae.genieConnector;

import com.sun.jna.Pointer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
public class Utils {

    private static final Field POINTER_PEER;
    static{
        Field peer;
        try {
            peer = Pointer.class.getDeclaredField("peer");
            peer.setAccessible(true);
        } catch (NoSuchFieldException e) {
            peer = null;
        }
        POINTER_PEER = peer;
    }

    public static long getPeer(Pointer pointer){
        if(pointer==null){
            return 0;
        }
        try {
            return (Long)POINTER_PEER.get(pointer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Logger getLogger(){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return LoggerFactory.getLogger(ste.getClassName());
    }
}
