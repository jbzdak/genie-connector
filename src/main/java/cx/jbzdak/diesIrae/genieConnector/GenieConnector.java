package cx.jbzdak.diesIrae.genieConnector;

import com.sun.jna.*;
import com.sun.jna.ptr.PointerByReference;
import cx.jbzdak.diesIrae.genieConnector.enums.*;
import cx.jbzdak.diesIrae.genieConnector.enums.param.Parameter;

import java.io.File;
import java.lang.reflect.Method;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
public class GenieConnector {

   /**
    * Nazwa dll-a dll musi być w java.lib.path
    */
   static final String DLL_FILENAME = "cxAthJbzdakGenieConnector";

   /**
    * Prefix jaki dodajemy do magicznych funkcji z DLL-a
    */
   static final String FUNCTION_PREFIX = "DLL_WRAPPER_";

   private final CallWrapper callWrapper = new CallWrapper();

   /**
    * hDSC, whatever it is ;)
    *
    * Patrz ten magiczny manual ;)
    */
   private final DscPointer dsc;

   private ConnectorState connectorState = ConnectorState.NOT_OPENED;

   private FlushType flush = FlushType.AUTO_COMMIT;

   private DeviceType deviceType = DeviceType.MCA;

   private short startChannel;

   private short endChannel;

   public GenieConnector() {
      PointerByReference dsc = new PointerByReference(Pointer.NULL);
      try {
         LibraryConnector.iUtlCreateFileDSC2(dsc);
         CloseAllVDMsHook.registerConnector(this);
      } catch (ConnectorException e) {
         throw new GenieException("Error while opening VDM", e.getCode());
      }
      this.dsc = new DscPointer(dsc.getValue());
   }

   public void openFile(final File file, final EnumSet<OpenMode> mode){
      assertMayOpen();
      callWrapper.doCall(new Call<Void>(){
         @Override
         Void doCall() throws ConnectorException {
            LibraryConnector.openDatasource(dsc,file.getAbsolutePath(), SourceType.FILE,  mode, false, "");
            connectorState = ConnectorState.OPEN;
            return null;
         }
      });
   }

   public <T> T getParam(final Parameter<T> parameter, final int record, final int entry){
      assertOpened();
      return callWrapper.doCall(new Call<T>() {
         @Override
         public T doCall() throws ConnectorException {
            return LibraryConnector.getParam(dsc, parameter, (short) record, (short) entry);
         }
      });
   }

   public <T> T getParam(final Parameter<T> parameter){
      return  getParam(parameter, 0, 0);
   }

   public <T> void setParam(final Parameter<T> parameter, final T value, final int record, final int entry){
      assertOpened();
      callWrapper.doCall(new Call<Void>() {
         @Override
         public Void doCall() throws ConnectorException {
            LibraryConnector.setParam(dsc, parameter, value, (short) record, (short) entry);
            if(flush == FlushType.AUTO_COMMIT){
               flush();
            }
            return null;
         }
      });
   }

   public void controlDSC(final OpCode opCode){
      assertOpened();
      callWrapper.doCall(new Call<Void>(){
         @Override
         Void doCall() throws ConnectorException {
            LibraryConnector.controlDSC(dsc, deviceType, opCode);
            return null;
         }
      });
   }

   public <T> void setParam(final Parameter<T> parameter, final T value){
      setParam(parameter, value, 0, 0);
   }

   public void flush(){
      callWrapper.doCall(new Call<Void>(){
         @Override
         Void doCall() throws ConnectorException {
            LibraryConnector.flush(dsc);
            return null;
         }
      });
   }

   public SpectrometricResult getSpectrometricData(final int start, final int end){
      return callWrapper.doCall(new Call<SpectrometricResult>() {
         @Override
         SpectrometricResult doCall() throws ConnectorException {
            return new SpectrometricResult((short) start, (short) end,LibraryConnector.getSpectralData(dsc, (short)start, (short)end));
         }
      });
   }

   public SpectrometricResult getSpectrometricData(){
      return getSpectrometricData(startChannel, endChannel);
   }

   void closeNoCheck(){
      callWrapper.doCall(new Call<Void>(){
         @Override
         Void doCall() throws ConnectorException {
            if(connectorState == ConnectorState.OPEN){
               LibraryConnector.closeDataSource(dsc);
            }
            LibraryConnector.close(dsc);
            CloseAllVDMsHook.deregisterConnector(GenieConnector.this);

            return null;
         }
         @Override
         void doFinally() {
            connectorState = ConnectorState.CLOSED;
         }
      });
   }

   public void close(){
      if(connectorState == ConnectorState.CLOSED){
         throw new IllegalStateException();
      }
      closeNoCheck();
   }

   public FlushType getFlush() {
      return flush;
   }

   public void setFlush(FlushType flush) {
      this.flush = flush;
   }

   public DeviceType getDeviceType() {
      return deviceType;
   }

   public void setDeviceType(DeviceType deviceType) {
      this.deviceType = deviceType;
   }

   public int getEndChannel() {
      return endChannel;
   }

   public void setEndChannel(short endChannel) {
      this.endChannel = endChannel;
   }

   public int getStartChannel() {
      return startChannel;
   }

   public void setStartChannel(short startChannel) {
      this.startChannel = startChannel;
   }

   private void assertOpened(){
      if(connectorState != ConnectorState.OPEN){
         throw new IllegalStateException("Can't call this method on closed or uninitialized Connector");
      }
   }

   private void assertMayOpen(){
      if(connectorState != ConnectorState.NOT_OPENED){
         throw new IllegalStateException("Can't call this method on opened Connector");
      }
   }

   class CallWrapper{
      public <T> T doCall(Call<T> call) throws GenieException{
         try {
            return call.doCall();
         } catch (ConnectorException e) {
            throw new GenieException(e.getCode(), dsc);
         }finally {
            call.doFinally();
         }
      }
   }

   abstract class Call<T>{
      abstract T doCall() throws ConnectorException;
      void doFinally(){}
   }
}




/**
 * Klasa robi całą magię potrzebna do wczytania biblioteki - czyli:
 * <p/>
 * Wczytuje <code>dll</code>a - dll powinien być w system32
 * <p/>
 * Ustawia mapowanie nazw funkcji (dodawanie przed FUNCTION_PREFIX)
 * <p/>
 * Woła GENIE_LIBRARY.vG2KEnv(), bo i tak kiedyś trzeba i trzeba raz to lepiej od razu.
 * <p/>
 * Ustawia Native#setProtected na true.
 *
 */
class GenieMapperStaticMagic{

   static final GenieLibrary GENIE_LIBRARY;
   static{
      GENIE_LIBRARY = (GenieLibrary)
              Native.loadLibrary(GenieConnector.DLL_FILENAME, GenieLibrary.class,createLibOptions());
      Native.setProtected(true);
      GENIE_LIBRARY.vG2KEnv();
   }

   private static Map<String,Object> createLibOptions(){
      Map<String, Object> result = new HashMap<String, Object>();
      result.put(Library.OPTION_FUNCTION_MAPPER, new Mapper());
      return result;
   }
}

class Mapper implements FunctionMapper{
   @Override
   public String getFunctionName(NativeLibrary library, Method method) {
      return GenieConnector.FUNCTION_PREFIX + method.getName();
   }
}

//class Mapper extends StdCallFunctionMapper{
//    public String getFunctionName(NativeLibrary library, Method method) {
//        System.out.println("" + GenieConnector.FUNCTION_PREFIX + super.getFunctionName(library, method));
//        return GenieConnector.FUNCTION_PREFIX + super.getFunctionName(library, method);
//    }
//}