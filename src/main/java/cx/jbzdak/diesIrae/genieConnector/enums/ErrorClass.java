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
 * Hex 0: None, specific error value is sufficient.
 * Hex 1: Command class.
 * Hex 2: Hardware class.
 * Hex 3: Communications class.
 * Hex 4: Operating system class.
 * Hex 5: Environment variable class.
 * Hex 6: Data conversion class.
 * 668
 * Genie 2000 Error Codes
 * Hex 7: CAM class.
 * Hex 8: ‘C’ runtime library class.
 */
public enum ErrorClass {
   NONE(0, "None, specific error value is sufficient"),
   COMMAND_CLASS(1, "Command class"),
   HARDWARE_CLASS(2, "Hardware class"),
   COMM_CLASS(3, "Communications class"),
   OSS_CLASS(4, "Operating system class"),
   EVN_VAR_CLASS(5, "Environment variable class."),
   DATA_CONVERSION_CLASS(6, "Data conversion class"),
   CAM_CLASS(7, "CAM_CLASS"),
   C_RUNTIME_CLASS(8, "‘C’ runtime library class.");

   private final byte errorClass;

   private final String className;

   public static ErrorClass getClass(Byte flag) {
      for (ErrorClass clazz : values()) {
         if (flag.equals(clazz.errorClass)) {
            return clazz;
         }
      }
      //throw new InvalidParameterException();
      return null;
   }

   ErrorClass(int errorClass, String className) {
      this.errorClass = (byte) errorClass;
      this.className = className;
   }

   public byte getErrorClass() {
      return errorClass;
   }

   public String getClassName() {
      return className;
   }
}

