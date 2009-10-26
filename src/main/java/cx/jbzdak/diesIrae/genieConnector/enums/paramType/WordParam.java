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

package cx.jbzdak.diesIrae.genieConnector.enums.paramType;

import java.nio.ByteBuffer;

class WordParam extends ParameterType<Integer> {

   WordParam() {
      super("WORD", 'W', C_INT_LENGHT * 2);
   }

   @Override
   public Integer readArray(byte[] inputBuffer) {
      ByteBuffer byteBuffer = ByteBuffer.wrap(inputBuffer);
      return byteBuffer.getInt();

   }

   @Override
   public byte[] writeArray(Integer integer) {
      ByteBuffer byteBuffer = ByteBuffer.allocate(4);
      byteBuffer.putInt(integer);
      return byteBuffer.array();
   }
}