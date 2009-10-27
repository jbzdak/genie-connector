package cx.jbzdak.diesIrae.genieConnector;

import com.sun.jna.FunctionMapper;
import com.sun.jna.NativeLibrary;

import java.lang.reflect.Method;

class GenieFunctionMapper implements FunctionMapper {
   @Override
   public String getFunctionName(NativeLibrary library, Method method) {
      return GenieConnectorStaticData.FUNCTION_PREFIX + method.getName();
   }
}