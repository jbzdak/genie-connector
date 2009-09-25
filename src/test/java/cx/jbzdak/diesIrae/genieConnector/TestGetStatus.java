package cx.jbzdak.diesIrae.genieConnector;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
public class TestGetStatus {

      GenieConnector connector;

    private static Random random = new SecureRandom();
    @BeforeClass
    public static void beforeClass() throws Exception{
        Class.forName("cx.jbzdak.diesIrae.genieConnector.GenieConnector");
    }

    @Before
    public void startup()throws Exception{
        connector = new GenieConnector();
    }

    @After
    public void after( ) throws Exception{
        connector.close();
    }

      @Test
    public void testGetStatus() throws Exception{
        Field f = GenieConnector.class.getDeclaredField("dsc");
        f.setAccessible(true);
        DscPointer pointer =(DscPointer) f.get(connector);
        LibraryWrapper.getDetailedError(pointer);
    }
    
}
