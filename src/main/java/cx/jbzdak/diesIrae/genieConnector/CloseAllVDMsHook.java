package cx.jbzdak.diesIrae.genieConnector;

import org.slf4j.Logger;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
class CloseAllVDMsHook extends Thread{

    private static final CloseAllVDMsHook HOOK;

    static{
        HOOK = new CloseAllVDMsHook();
        Runtime.getRuntime().addShutdownHook(HOOK);
    }

    public static void registerConnector(GenieConnector connector){
        HOOK.openConnectors.add(connector);
    }

    public static void deregisterConnector(GenieConnector connector){
        HOOK.openConnectors.remove(connector);
    }

    private final Collection<GenieConnector> openConnectors = new ConcurrentLinkedQueue<GenieConnector>();
    private static final Logger LOGGER = Utils.getLogger();
    @Override
    public void run() {
        for(GenieConnector connector: openConnectors){
            try {
                connector.closeNoCheck();
            } catch (Throwable e) {
                LOGGER.warn("",e);
            }
        }
    }
}
