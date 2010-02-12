/*
 * GenieConnector java library to connect with
 * Canberra Genie 2000 library
 * Copyright (C) 2009 - 2010 Jacek Bzdak jbzdak@gmail.com
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

