package genieConnector.test;

import cx.jbzdak.diesIrae.genieConnector.ErrorDescription;
import cx.jbzdak.diesIrae.genieConnector.enums.ErrorClass;
import cx.jbzdak.diesIrae.genieConnector.enums.ErrorLevel;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
public class ErrorDescriptionTest {

     ErrorDescription desc;

    @Before
    public void before(){
         desc = new ErrorDescription(0x278e2aL);
    }

    @Test
    public void testSpecificCode(){
        Assert.assertEquals(desc.getSpecificErrorCode().intValue(), 0x8e2a);
    }

    @Test
    public void testErrorLevel(){
        Assert.assertEquals(desc.getErrorLevel(), ErrorLevel.VDM_DRIVER_ERROR);
    }

    @Test
    public void testErrorClass(){
         Assert.assertEquals(desc.getErrorClass(), ErrorClass.CAM_CLASS);
    }


}
