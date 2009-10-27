/*
 * GenieConnector java library to connect with
 * Canberra Genie 2000 library
 * Copyright (C) 2009 Jacek Bzdak jbzdak@gmail.com
 *
 * This program was written for my BA in Faculty of Physics of
 * Warsaw University of Technology.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package cx.jbzdak.diesIrae.genieConnector;

import org.junit.Assert;
import org.junit.Test;


/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */

public class LongWordParamTest {

    LongWordParam param = new LongWordParam();

    long[] testedLongs = new long[]{1,5,0,-60,15000, (long) (Math.pow(2,54) - 108)};

//    void testLong(long tested){
//        Assert.assertEquals(tested, param.readArray(param.writeArray(tested)).longValue());
//    }
//
//    @Test
//    public void testPrepared(){
//        for(long test : testedLongs){
//            testLong(test);
//        }
//    }
//
//    @Test
//    public void testRandom(){
//        Random random = new SecureRandom();
//        for(int ii =0; ii< 10000; ii++){
//            testLong(random.nextLong());
//        }
//    }
}
