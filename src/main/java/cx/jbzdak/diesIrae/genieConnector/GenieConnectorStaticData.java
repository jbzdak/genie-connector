package cx.jbzdak.diesIrae.genieConnector;

import com.sun.jna.Native;
import com.sun.jna.Library;

import java.util.Map;
import java.util.HashMap;

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
class GenieConnectorStaticData {

   /**
    * Nazwa dll-a dll musi być w java.lib.path
    */
   static final String DLL_FILENAME = "cxAthJbzdakGenieConnector";

   /**
    * Prefix jaki dodajemy do magicznych funkcji z DLL-a
    */
   static final String FUNCTION_PREFIX = "DLL_WRAPPER_";

   static final GenieLibrary GENIE_LIBRARY;
   static{
      GENIE_LIBRARY = (GenieLibrary)
              Native.loadLibrary(DLL_FILENAME, GenieLibrary.class, createLibOptions());
      Native.setProtected(true);
      GENIE_LIBRARY.vG2KEnv();
   }

   private static Map<String,Object> createLibOptions(){
      Map<String, Object> result = new HashMap<String, Object>();
      result.put(Library.OPTION_FUNCTION_MAPPER, new Mapper());
      return result;
   }
}
