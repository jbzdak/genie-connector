package cx.jbzdak.diesIrae.genieConnector;

import cx.jbzdak.diesIrae.genieConnector.enums.OpenMode;
import cx.jbzdak.diesIrae.genieConnector.enums.SourceType;
import org.junit.Before;
import org.junit.Test;

import java.util.EnumSet;

/**
 * Created by IntelliJ IDEA.
 * User: jbzdak
 * Date: 2009-07-15
 * Time: 13:55:48
 * To change this template use File | Settings | File Templates.
 */
public class TestDET {

    GenieConnector connector;

    @Before
    public void before(){
        connector = new GenieConnector();
        connector.openSource("DET_1", EnumSet.of(OpenMode.READ_WRITE, OpenMode.SYSTEM_WRITE, OpenMode.EXCLUSIVE), SourceType.DETECTOR);
    }

    @Test
    public void test1(){
        System.out.println(""+connector.getSpectrometricData(1,100));
    }
}
