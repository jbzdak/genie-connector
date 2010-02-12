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
 * User: Jacek Bzdak jbzdak@gmail.com
 */
public enum ErrorLevel {
   HARDWARE_PROTOCOL_ERROR(1, "Hardware Protocol driver error"),
   VDM_DRIVER_ERROR(2, "VDM Driver error"),
   VDM_ERROR(3, "VDM error"),
   IPC_ERROR(4, "IPC error"),
   CLIENT_ERROR(5, "Client (SAD access routine) error"),
   APP_ERROR(6, "Application error");

   public static ErrorLevel getLevel(Byte flag) {
      for (ErrorLevel clazz : values()) {
         if (flag.equals(clazz.errorLevel)) {
            return clazz;
         }
      }
      //throw new InvalidParameterException();
      return null;
   }


   private final byte errorLevel;

   private final String className;

   ErrorLevel(int errorLevel, String className) {
      this.errorLevel = (byte) errorLevel;
      this.className = className;
   }

   public byte getErrorLevel() {
      return errorLevel;
   }

   public String getClassName() {
      return className;
   }
}
