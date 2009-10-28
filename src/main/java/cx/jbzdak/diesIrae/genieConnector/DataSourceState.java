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

import java.util.EnumSet;

/**
 * Created by IntelliJ IDEA.
 * User: jbzdak
 * Date: 2009-08-07
 * Time: 12:18:11
 */
public enum DataSourceState {
   DONE(16), BUSY(4), IDLE(2), UPDATE(32);

   private final int value;

   DataSourceState(int value) {
      this.value = value;
   }

   EnumSet<DataSourceState> computeFromFlagInt(short flags) {
      EnumSet<DataSourceState> result = EnumSet.noneOf(DataSourceState.class);
      for (DataSourceState dataSourceState : values()) {
         if ((flags & dataSourceState.value) != 0) {
            result.add(dataSourceState);
         }
      }
      return result;
   }
}
