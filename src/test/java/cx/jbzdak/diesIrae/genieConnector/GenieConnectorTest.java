package cx.jbzdak.diesIrae.genieConnector;

import cx.jbzdak.diesIrae.genieConnector.enums.OpenMode;
import cx.jbzdak.diesIrae.genieConnector.enums.param.ParamAlias;
import org.junit.*;

import java.io.File;
import java.security.SecureRandom;
import java.util.EnumSet;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
@SuppressWarnings({"MagicNumber"})
public class GenieConnectorTest {
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
    }

    @After
    public void after( ) throws Exception{
        connector.close();
    }

    /**
     * Rekonstrukcja Example2.c 1
     */
    @Test
    public void test(){
        Assert.assertEquals(connector.getParam(ParamAlias.SAMPLE_IDENTIFIER, 0,0), "NBS Std.");
    }

    @Test
    public void test2(){
        float randomFloat = random.nextFloat();
        connector.setParam(ParamAlias.SAMPLE_QUANTITY, randomFloat);
        Assert.assertEquals(connector.getParam(ParamAlias.SAMPLE_QUANTITY), randomFloat, 0.0001);
        connector.setParam(ParamAlias.SAMPLE_QUANTITY, 0.0f);
    }



}
