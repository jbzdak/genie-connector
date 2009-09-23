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

    public static void registerConnector(SimpleConnector connector){
        HOOK.openConnectors.add(connector);
    }

    public static void deregisterConnector(SimpleConnector connector){
        HOOK.openConnectors.remove(connector);
    }

    private final Collection<SimpleConnector> openConnectors = new ConcurrentLinkedQueue<SimpleConnector>();
    private static final Logger LOGGER = Utils.getLogger();
    @Override
    public void run() {
        for(SimpleConnector connector: openConnectors){
            try {
                connector.closeNoCheck();
            } catch (Throwable e) {
                LOGGER.warn("",e);
            }
        }
    }
}
