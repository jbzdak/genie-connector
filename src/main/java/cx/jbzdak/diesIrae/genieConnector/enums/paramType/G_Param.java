/*
 * GenieConnector java library to connect with
 * Canberra Genie 2000 library
 * Copyright (C) 2009 Jacek Bzdak jbzdak@gmail.com
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

/**
 * Cokolwiek to jest . . .
 */
class G_Param extends ParameterType<Object>{
    G_Param() {
        super("GReal", 'G', C_REAL_LENGHT*4);
    }

    @Override
    public byte[] writeArray(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object readArray(byte[] p) {
         throw new UnsupportedOperationException();
    }
}
