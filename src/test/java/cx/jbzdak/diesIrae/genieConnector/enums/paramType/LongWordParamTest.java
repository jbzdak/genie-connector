package cx.jbzdak.diesIrae.genieConnector.enums.paramType;

import org.junit.Assert;
import org.junit.Test;

import java.security.SecureRandom;
import java.util.Random;


/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */

public class LongWordParamTest {

    LongWordParam param = new LongWordParam();

    long[] testedLongs = new long[]{1,5,0,-60,15000, (long) (Math.pow(2,54) - 108)};

    void testLong(long tested){
        Assert.assertEquals(tested, param.readArray(param.writeArray(tested)).longValue());
    }

    @Test
    public void testPrepared(){
        for(long test : testedLongs){
            testLong(test);
        }
    }

    @Test
    public void testRandom(){
        Random random = new SecureRandom();
        for(int ii =0; ii< 10000; ii++){
            testLong(random.nextLong());
        }
    }
}
