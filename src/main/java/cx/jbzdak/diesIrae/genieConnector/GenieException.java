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

import edu.umd.cs.findbugs.annotations.CheckForNull;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
public class GenieException extends RuntimeException {
   @NonNull private final int errorCode;

   @CheckForNull private final Long detailedErrorCode;

   @CheckForNull private final ErrorDescription errorDescription;

   @NonNull private final Object[] additionalInfo;

   public GenieException(String message, int errorCode) {
      super(message);
      this.errorCode = errorCode;
      this.detailedErrorCode = null;
      this.errorDescription = null;
      this.additionalInfo = new Object[0];
   }

   GenieException(String message, int errorCode, DscPointer dsc) {
      super(message);
      this.errorCode = errorCode;
      detailedErrorCode = LibraryWrapper.getDetailedError(dsc);
      errorDescription = new ErrorDescription(detailedErrorCode);
      this.additionalInfo = new Object[0];
   }

   GenieException(int errorCode, DscPointer dsc, ConnectorException co, Object... additionalInfo) {
      super(co);
      this.errorCode = errorCode;
      detailedErrorCode = LibraryWrapper.getDetailedError(dsc);
      errorDescription = new ErrorDescription(detailedErrorCode);
      this.additionalInfo = additionalInfo;
   }

   public int getErrorCode() {
      return errorCode;
   }

   public Long getDetailedErrorCode() {
      return detailedErrorCode;
   }

   @Override
   public String getMessage() {
      StringBuilder message = new StringBuilder("Błąd Genie 2000 ");
      if (!StringUtils.isEmpty(super.getMessage())) {
         message.append("wiadomość orygnialna '");
         message.append(super.getMessage());
         message.append("' ");
      }
      message.append("Kod błędu ogólnego '");
      message.append(String.valueOf(errorCode));
      message.append(", ");
      message.append(errorDescription);
      if (!ArrayUtils.isEmpty(additionalInfo)) {
         message.append("\nDodatkowe informacje: ");
         for (Object o : additionalInfo) {
            message.append(o);
            message.append("\n");
         }
      }
      return message.toString();
   }
}
