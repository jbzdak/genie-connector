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

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Structure;

import java.util.HashMap;
import java.util.Map;

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

   static {
      GENIE_LIBRARY = (GenieLibrary)
              Native.loadLibrary(DLL_FILENAME, GenieLibrary.class, createLibOptions());
      Native.setProtected(true);
      GENIE_LIBRARY.vG2KEnv();
   }

   private static Map<String, Object> createLibOptions() {
      Map<String, Object> result = new HashMap<String, Object>();
      result.put(Library.OPTION_FUNCTION_MAPPER, new GenieFunctionMapper());
      result.put(Library.OPTION_STRUCTURE_ALIGNMENT, Structure.ALIGN_NONE);
      return result;
   }

}



