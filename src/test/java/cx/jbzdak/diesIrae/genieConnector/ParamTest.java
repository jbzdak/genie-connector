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

import com.sun.jna.NativeLong;
import cx.jbzdak.diesIrae.genieConnector.enums.FlushType;
import cx.jbzdak.diesIrae.genieConnector.enums.OpenMode;
import cx.jbzdak.diesIrae.genieConnector.enums.param.ParamAlias;
import cx.jbzdak.diesIrae.genieConnector.enums.param.Parameters;
import org.apache.commons.io.FileUtils;
import org.junit.*;

import java.io.File;
import java.rmi.server.UID;
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

   SimpleConnector connector;

   private static Random random = new SecureRandom();
   @BeforeClass
   public static void beforeClass() throws Exception{
      Class.forName("cx.jbzdak.diesIrae.genieConnector.GenieConnector");
   }

   @Before
   public void startup()throws Exception{
      connector = new SimpleConnector();
      File f = new File("C:\\GENIE2K\\CAMFILES\\NBSSTD.CNF");
      File temp = File.createTempFile("gcTest", "CNF");
      temp.deleteOnExit();
      FileUtils.copyFile(f, temp);
      connector.setFlush(FlushType.AUTO_COMMIT);
      connector.openFile(temp, EnumSet.of(OpenMode.READ_WRITE, OpenMode.SYSTEM_WRITE, OpenMode.EXCLUSIVE, OpenMode.TAKE_CONTROL, OpenMode.TAKE_OVER));
      //connector.openSource("DET_1", EnumSet.of(OpenMode.READ_WRITE, OpenMode.SYSTEM_WRITE, OpenMode.EXCLUSIVE), SourceType.DETECTOR);
   }

   @After
   public void after( ) throws Exception{
      connector.close();
   }

   @Test
   public void testReadLong(){
      Assert.assertEquals(connector.getParam(Parameters.L_PAEND).longValue(), 4096L);
   }

   @Test
   public void testWriteLong(){
      for(int ii=100; ii <=4096; ii++){
         connector.setParam(Parameters.L_PEAKSTART, (long) ii);
         Assert.assertEquals((long)(ii), connector.getParam(Parameters.L_PEAKSTART).longValue());
      }
   }

    @Test
   public void testReadChar(){
       System.out.println(connector.getParam(ParamAlias.SAMPLE_IDENTIFIER)); 
   }

   @Test
   public void testWriteChar(){
      String ident = new UID().toString().substring(0, 8);
      connector.setParam(ParamAlias.SAMPLE_IDENTIFIER, ident);
      Assert.assertEquals(ident, connector.getParam(ParamAlias.SAMPLE_IDENTIFIER));
   }

}
