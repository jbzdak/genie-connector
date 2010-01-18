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

import cx.jbzdak.diesIrae.genieConnector.enums.param.Parameter;
import edu.umd.cs.findbugs.annotations.NonNull;

import javax.annotation.concurrent.ThreadSafe;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
@ThreadSafe
public abstract class ParameterType<T> {

   public static final
   ParameterType<Byte> BYTE_PARAM = new ByteParam();

   public static final
   ParameterType<Integer> SHORT_PARAM = new WordParam();

   public static final
   ParameterType<Long> WORD = new LongWordParam();

   public static final
   ParameterType<BigInteger> QUAD_WORD = new QuadWordParam();

   public static final
   ParameterType<Float> REAL = new RealParam();

   public static final
   ParameterType<Double> DOUBLE = new DoubleParam();

   public static final
   ParameterType<Object> G_PARAM = new G_Param();

   public static final
   ParameterType<String> CHARACTER = new CharacterParam();

   public static final
   ParameterType<Date> DATETIME = new DatetimeParam();

   public static final
   ParameterType<?> UNKNOWN_TYPE = new ParameterType<Object>("UNKNOWN", '\0') {
      @Override
      public Object readParam(GenieLibrary library, DscPointer dscPointer, Parameter param, short usEntry, short usRecord) {
         throw new UnsupportedOperationException();
      }

      @Override
      public void writeParam(GenieLibrary library, Object value, DscPointer dscPointer, Parameter param, short usEntry, short usRecord) {
         throw new UnsupportedOperationException();
      }
   };

   private static final List<ParameterType<?>> PARAMS = Arrays.asList(BYTE_PARAM, SHORT_PARAM, WORD, QUAD_WORD, REAL,
           DOUBLE, G_PARAM, CHARACTER, DATETIME);

   private static final Map<Character, ParameterType<?>> PARAM_TYPE_MAPPER;

   static {
      Map<Character, ParameterType<?>> MAPPER = new HashMap<Character, ParameterType<?>>();
      for (ParameterType t : PARAMS) {
         MAPPER.put(t.identifierChar, t);
      }
      PARAM_TYPE_MAPPER = Collections.unmodifiableMap(MAPPER);
   }

   @NonNull
   public static ParameterType<?> getType(char type) {
      ParameterType result = PARAM_TYPE_MAPPER.get(type);
      if (result == null) {
         return UNKNOWN_TYPE;
      }
      return result;
   }

   /**
    * Długość typu int w kompilatorze dla któryego kompilujemy bibliotekę. . .
    * Albo też ta oczekiwana przez GENIE ;)
    */
   static final short C_INT_LENGHT = 4;

   /**
    * Długość typu REAL - czyli float ;) dla którego kompilujemy bibliotekę
    * Albo też długoć której oczekuje GENIE ;) dla wielkości REAL
    */
   static final short C_REAL_LENGHT = 4;

   ParameterType(String name, char identifierChar) {
      this.name = name;
      this.identifierChar = identifierChar;
     
   }

   public final String name;

   public final char identifierChar;

   public abstract T readParam(GenieLibrary library, DscPointer dscPointer, Parameter param, short usRecord,  short usEntry) throws ConnectorException;

   public abstract void writeParam(GenieLibrary library, T value, DscPointer dscPointer, Parameter param, short usRecord,  short usEntry) throws ConnectorException;

}


