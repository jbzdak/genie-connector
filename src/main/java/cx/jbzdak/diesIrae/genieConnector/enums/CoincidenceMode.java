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

/**
 * Created by IntelliJ IDEA.
 * User: jbzdak
 * Date: 2009-11-30
 * Time: 17:45:54
 */
public enum CoincidenceMode {
   /**
    * Coincidence is enabled
    */
   COINCIDENCE(1),
   /**
    * Coincidence is disabled,
    * and anticoincidence input is active
    */
   ANTI_COINCIDENCE(0);

   public static CoincidenceMode readLong(long value){
      for (CoincidenceMode mode : values()) {
         if(value==mode.value){
            return mode;
         }
      }
      throw new IllegalArgumentException();
   }

   private final long value;

   CoincidenceMode(long value) {
      this.value = value;
   }

   public long getValue() {
      return value;
   }

}
