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

import cx.jbzdak.diesIrae.genieConnector.enums.*;
import cx.jbzdak.diesIrae.genieConnector.enums.param.ParamAlias;
import cx.jbzdak.diesIrae.genieConnector.structs.DSPreset;
import cx.jbzdak.diesIrae.struct.Preset;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: jbzdak
 * Date: 2009-10-27
 * Time: 15:18:30
 */
public class TestCoincidence {

   SimpleConnector connector;

   @Before
   public void startup()throws Exception{
      //ACC_Exclusive|ACC_AppWrite|ACC_ReadWrite|ACC_TakeControl|ACC_TakeOver
      connector = new SimpleConnector();
      connector.openSource("DET_1", EnumSet.of(OpenMode.READ_WRITE, OpenMode.SYSTEM_WRITE), SourceType.DETECTOR);
      if(connector.getStatus().contains(Status.BUSY)){
         connector.controlDSC(OpCode.ABORT_ACQUISITION);
      }   
      connector.controlDSC(OpCode.CLEAR_DATA);
      //connector.setLiveTime(5);
   }

   @After
   public void after( ) throws Exception{
      connector.close();
   }

   @Test(timeout = 500)
   public void test1(){
      connector.getParam(ParamAlias.COINCIDENCE_MODE);
      connector.setParam(ParamAlias.COINCIDENCE_MODE, 0L);
      Assert.assertEquals(Long.valueOf(0L), connector.getParam(ParamAlias.COINCIDENCE_MODE));
   }

   @Test(timeout =500)
   public void test2(){
      connector.getParam(ParamAlias.COINCIDENCE_MODE);
      connector.setParam(ParamAlias.COINCIDENCE_MODE, 1L);
      Assert.assertEquals(Long.valueOf(1L), connector.getParam(ParamAlias.COINCIDENCE_MODE));
   }


   @Test(timeout =500)
   public void test3(){
      long result = connector.getParam(ParamAlias.COINCIDENCE_MODE);
      Assert.assertTrue(result == 1 || result == 0);
   }


   @Test
   public void test4(){
      Preset preset =  new Preset();
      preset.setPresetMode(PresetMode.LIVE);
      preset.setTime(10, TimeUnit.MINUTES);
      connector.setPreset(preset);
      connector.flush();
      System.out.println("DSPreset().size()" + new DSPreset().size());
   }

}
