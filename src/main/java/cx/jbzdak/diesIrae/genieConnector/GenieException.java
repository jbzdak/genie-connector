package cx.jbzdak.diesIrae.genieConnector;

import edu.umd.cs.findbugs.annotations.CheckForNull;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
public class GenieException extends RuntimeException{
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
      detailedErrorCode = LibraryConnector.getDetailedError(dsc);
      errorDescription = new ErrorDescription(detailedErrorCode);
      this.additionalInfo = new Object[0];
   }

   GenieException(int errorCode, DscPointer dsc, Object... additionalInfo) {
      super();
      this.errorCode = errorCode;
      detailedErrorCode = LibraryConnector.getDetailedError(dsc);
      errorDescription = new ErrorDescription(detailedErrorCode);
      this.additionalInfo =  additionalInfo;
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
      if(!StringUtils.isEmpty(super.getMessage())){
         message.append("wiadomość orygnialna '");
         message.append(super.getMessage());
         message.append("' ");
      }
      message.append("Kod błędu ogólnego '" );
      message.append(String.valueOf(errorCode));
      message.append(", ");
      message.append(errorDescription);
      if(!ArrayUtils.isEmpty(additionalInfo)){
         message.append("\nDodatkowe informacje: ");
         for(Object o : additionalInfo){
            message.append(o);
            message.append("\n");
         }
      }                      
      return message.toString();
   }
}
