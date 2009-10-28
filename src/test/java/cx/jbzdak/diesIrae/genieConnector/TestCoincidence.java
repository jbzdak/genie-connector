package cx.jbzdak.diesIrae.genieConnector;

import cx.jbzdak.diesIrae.genieConnector.enums.OpCode;
import cx.jbzdak.diesIrae.genieConnector.enums.OpenMode;
import cx.jbzdak.diesIrae.genieConnector.enums.SourceType;
import cx.jbzdak.diesIrae.genieConnector.enums.Status;
import cx.jbzdak.diesIrae.genieConnector.enums.param.ParamAlias;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.EnumSet;

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
      connector.setLiveTime(5);
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

}
