package cx.jbzdak.diesIrae.genieConnector;

import com.sun.jna.NativeLong;
import cx.jbzdak.diesIrae.genieConnector.enums.OpenMode;
import cx.jbzdak.diesIrae.genieConnector.enums.SourceType;
import cx.jbzdak.diesIrae.genieConnector.enums.param.Parameters;
import org.junit.*;

import java.io.File;
import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: jbzdak
 * Date: 2009-10-26
 * Time: 16:39:34
 */

public class ParamTest {

       SimpleConnector connector;

    private static Random random = new SecureRandom();
    @BeforeClass
    public static void beforeClass() throws Exception{
        Class.forName("cx.jbzdak.diesIrae.genieConnector.GenieConnector");
    }

    @Before
    public void startup()throws Exception{
        connector = new SimpleConnector();
        //connector.openFile(new File("C:\\GENIE2K\\CAMFILES\\NBSSTD.CNF"), EnumSet.of(OpenMode.READ_WRITE, OpenMode.SYSTEM_WRITE, OpenMode.EXCLUSIVE, OpenMode.TAKE_CONTROL, OpenMode.TAKE_OVER));
        connector.openSource("DET_1", EnumSet.of(OpenMode.READ_WRITE, OpenMode.SYSTEM_WRITE, OpenMode.EXCLUSIVE), SourceType.DETECTOR);
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
       Assert.assertEquals(connector.getParam(Parameters.L_ADCFANTIC).longValue(), 4096L);
    }


    @Test
    public void test3(){
       System.out.println(connector.getParam(Parameters.T_AMPTYPE));
    }



}
