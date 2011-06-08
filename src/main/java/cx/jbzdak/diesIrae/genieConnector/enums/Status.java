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

package cx.jbzdak.diesIrae.genieConnector.enums;

import java.util.EnumSet;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
@SuppressWarnings({"MagicNumber"})
public enum Status {
   BUSY(4), IDLE(2), DONE(16), UPDATE(32);

   private final int value;

   Status(int value) {
      this.value = value;
   }

   public int getValue() {
      return value;
   }

   public static EnumSet<Status> decode(int bitField) {
      EnumSet<Status> result = EnumSet.noneOf(Status.class);
      for (Status s : Status.values()) {
         if ((bitField & s.getValue()) != 0) {
            result.add(s);
         }
      }
      return result;

   }
}
