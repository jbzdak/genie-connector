package cx.jbzdak.diesIrae.genieConnector;

import com.sun.jna.NativeLong;
import com.sun.jna.ptr.NativeLongByReference;
import cx.jbzdak.diesIrae.genieConnector.enums.param.Parameter;
import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * Created by IntelliJ IDEA.
 * User: jbzdak
 * Date: 2009-10-27
 * Time: 14:16:13
 */
public abstract class DefaultParameterType<T> extends ParameterType<T>{


   private final short byteLenght;

   protected DefaultParameterType(@NonNull String name, char identifierChar, int byteLenght) {
      super(name, identifierChar);
       if (byteLenght > Short.MAX_VALUE)
         throw new IllegalStateException();
      this.byteLenght = (short) byteLenght;
   }


   /**
    * Dla parametrów typu string trzeba podać nazwę... Domyślnie zwracamy #byteLenght
    *
    * @param paramName nazwa parametru dla której obliczamy długość
    * @return długośc w bajtach parametru
    */
   public short getByteLenght(String paramName) {
      return byteLenght;
   }

   public abstract T readArray(byte[] p);

   public abstract byte[] writeArray(T t);

   @Override
   public T readParam(GenieLibrary library, DscPointer dsc, Parameter param, short usRecord,  short usEntry) throws ConnectorException {
      byte[] result = new byte[getByteLenght(param.getName())];
      short errorCode;
      errorCode = library.SadGetParam(dsc, new NativeLong(param.getParamId()), usRecord, usEntry, result, getByteLenght(param.getName()));
      LibraryWrapper.checkError(errorCode);
      T objectResult = readArray(result);
      return objectResult;
   }

   @Override
   public void writeParam(GenieLibrary library, T value, DscPointer dscPointer, Parameter param, short usRecord,  short usEntry) throws ConnectorException {
      byte[] bytes = writeArray(value);
      short errorCode = library.SadPutParam(dscPointer, new NativeLong(param.getParamId()), usRecord, usEntry, bytes, getByteLenght(param.getName()));
      LibraryWrapper.checkError(errorCode);
   }
}
