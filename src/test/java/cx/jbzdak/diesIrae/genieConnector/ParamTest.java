package cx.jbzdak.diesIrae.genieConnector;

import cx.jbzdak.diesIrae.genieConnector.enums.OpenMode;
import cx.jbzdak.diesIrae.genieConnector.enums.param.Parameters;
import org.junit.*;

import java.io.File;
import java.security.SecureRandom;
import java.util.EnumSet;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: jbzdak
 * Date: 2009-10-26
 * Time: 16:39:34
 */

public class ParamTest {

       GenieConnector connector;

    private static Random random = new SecureRandom();
    @BeforeClass
    public static void beforeClass() throws Exception{
        Class.forName("cx.jbzdak.diesIrae.genieConnector.GenieConnector");
    }

    @Before
    public void startup()throws Exception{
        connector = new GenieConnector();
        connector.openFile(new File("C:\\GENIE2K\\CAMFILES\\NBSSTD.CNF"), EnumSet.of(OpenMode.READ_WRITE, OpenMode.SYSTEM_WRITE, OpenMode.EXCLUSIVE));
       //connector.openSource("DET_1", EnumSet.of(OpenMode.READ_WRITE, OpenMode.SYSTEM_WRITE, OpenMode.EXCLUSIVE), SourceType.DETECTOR);
    }

    @After
    public void after( ) throws Exception{
        connector.close();
    }

    @Test
    public void test(){
       Assert.assertEquals(connector.getParam(Parameters.L_PAEND).longValue(), 4096L);
    }

   @Test
    public void test2(){
       Assert.assertEquals(connector.getParam(Parameters.L_MAXGAINPASS).longValue(), 4096L);
    }
}
