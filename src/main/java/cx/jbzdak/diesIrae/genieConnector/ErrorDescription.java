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

import cx.jbzdak.diesIrae.genieConnector.enums.ErrorClass;
import cx.jbzdak.diesIrae.genieConnector.enums.ErrorLevel;
import org.apache.commons.collections.map.DefaultedMap;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
@SuppressWarnings({"MagicNumber"})
public class ErrorDescription {

   private static final Map<Integer, String> detailedMessages =
           Collections.synchronizedMap(DefaultedMap.decorate(new HashMap(), "Nie określono wiadomości szczegółowej"));

   static {
      detailedMessages.put(0xfffd, "Ogólny błąd połączenia z VDM");
      detailedMessages.put(0x8e2a, "Plik otwarty przez inny program i nie można się nim podzielić ;)");
      detailedMessages.put(0xfff7, "Wywołanie niepoprawnej operacji -- nie wiem na pewno, zgaduje -- ten błąd pojawia się jak" +
              " wywoła się start akwizycji danych na pliku z zapisanymi pomiarami");
      detailedMessages.put(0xfff4, "Network configuration file error");
      detailedMessages.put(0x22, "Busy but never opened. . . Just reset the god damn hardware!");
      detailedMessages.put(0x805a, "Parametr ten nie jest dostępny w źródle danych");
   }

   private final Long originalErrorCode;

   private final ErrorClass errorClass;

   private final ErrorLevel errorLevel;

   private final Integer specificErrorCode;

   public ErrorDescription(Long originalErrorCode) {
      this.originalErrorCode = originalErrorCode;
      long specificCodeMask = (long) (Math.pow(2, 16)) - 1L;
      //System.out.println(" "  + Long.toBinaryString(specificCodeMask));
      specificErrorCode = (int) (originalErrorCode & specificCodeMask);
      //.out.println(" "  + Long.toBinaryString(originalErrorCode));
      originalErrorCode >>= 16;
      //System.out.println(" "  + Long.toBinaryString(originalErrorCode));
      specificCodeMask = 7;
      errorClass = ErrorClass.getClass((byte) (originalErrorCode & specificCodeMask));
      //System.out.println(" "  + Long.toBinaryString(originalErrorCode));
      originalErrorCode >>= 4;
      specificCodeMask = 255;
      errorLevel = ErrorLevel.getLevel((byte) (originalErrorCode & specificCodeMask));
   }

   public Long getOriginalErrorCode() {
      return originalErrorCode;
   }

   public ErrorClass getErrorClass() {
      return errorClass;
   }

   public ErrorLevel getErrorLevel() {
      return errorLevel;
   }

   public Integer getSpecificErrorCode() {
      return specificErrorCode;
   }

   public String getDetailedMessage() {
      return detailedMessages.get(getSpecificErrorCode());
   }

   @Override
   public String toString() {
      return "ErrorDescription{" +
              "originalErrorCode=" + originalErrorCode +
              ", errorClass=" + errorClass +
              ", errorLevel=" + errorLevel +
              ", detailedMessage=" + getDetailedMessage() +
              ", specificErrorCode=" + Integer.toHexString(specificErrorCode) +
              '}';
   }
}
