package cx.jbzdak.diesIrae.genieConnector;

import cx.jbzdak.diesIrae.genieConnector.enums.OpenMode;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
@SuppressWarnings({"MagicNumber"})
public class SpectrometryTest {

    GenieConnector connector;

    SpectrometricResult data;

    Map<Integer, Integer> expectedValues = new HashMap<Integer, Integer>();

    {
        expectedValues.put(1, 4000);
        expectedValues.put(8,30);
        expectedValues.put(177,1252);
        expectedValues.put(1133, 262);
        expectedValues.put(1429, 1001);
        expectedValues.put(1946, 3994);
        expectedValues.put(2898, 1051);
        expectedValues.put(4000, 1869);
        expectedValues.put(4058, 2);
    }

    @BeforeClass
    public static void beforeClass() throws Exception{
        Class.forName("cx.jbzdak.diesIrae.genieConnector.GenieConnector");
    }

    @Before
    public void startup()throws Exception{
        connector = new GenieConnector();
        connector.openFile(new File("C:\\GENIE2K\\CAMFILES\\NBSSTD.CNF"), EnumSet.of(OpenMode.READ_WRITE, OpenMode.SYSTEM_WRITE, OpenMode.EXCLUSIVE));
        //connector.setStartChannel((short)1);
        //connector.setEndChannel((short )4095);
        //data = connector.getSpectrometricData();
        data = connector.getSpectrometricData(1, 4095);
    }

    @After
    public void after( ) throws Exception{
        connector.close();
    }

     @Test
    public void testSpectrometricData(){
         System.out.println(data.getCountForChannel(1));
    }

    @Test
    public void testZero(){
        Assert.assertEquals(0,data.getCountForChannel(3));
    }

    @Test
     public void testChannels(){
         for(Map.Entry<Integer, Integer> entry : expectedValues.entrySet()){
             System.out.println(entry);
             Assert.assertEquals(entry.toString(),entry.getValue().intValue(), data.getCountForChannel(entry.getKey()));
         }
     }


  
}

