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
import com.sun.jna.ptr.DoubleByReference;
import cx.jbzdak.diesIrae.genieConnector.enums.param.Parameter;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
class DoubleParam extends ParameterType<Double> {
   DoubleParam() {
      super("DOUBLE", 'D');
   }

   @Override
   public Double readParam(GenieLibrary library, DscPointer dscPointer, Parameter param, short usRecord, short usEntry) throws ConnectorException {
      DoubleByReference resultReference = new DoubleByReference();
      int error = library.SadGetParam(dscPointer, new NativeLong(param.getParamId()), usRecord, usEntry, resultReference, (short)8);
      LibraryWrapper.checkError(error);
      return  resultReference.getValue();
   }

   @Override
   public void writeParam(GenieLibrary library, Double value, DscPointer dscPointer, Parameter param, short usRecord, short usEntry) throws ConnectorException {
      DoubleByReference ref = new DoubleByReference();
      ref.setValue(value);
      int error = library.SadPutParam(dscPointer, new NativeLong(param.getParamId()), usRecord, usEntry, ref, (short)8);
      LibraryWrapper.checkError(error);
   }
}
