package cx.jbzdak.diesIrae.genieConnector;

import edu.umd.cs.findbugs.annotations.CheckForNull;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.apache.commons.lang.StringUtils;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
public class GenieException extends RuntimeException{
   @NonNull private final int errorCode;
   @CheckForNull private final Long detailedErrorCode;
   @CheckForNull private final ErrorDescription errorDescription;

   public GenieException(String message, int errorCode) {
      super(message);
      this.errorCode = errorCode;
      this.detailedErrorCode = null;
      this.errorDescription = null;
   }

   GenieException(String message, int errorCode, DscPointer dsc) {
      super(message);
      this.errorCode = errorCode;
      detailedErrorCode = LibraryConnector.getDetailedError(dsc);
      errorDescription = new ErrorDescription(detailedErrorCode);

   }

   GenieException(int errorCode, DscPointer dsc) {
      super();
      this.errorCode = errorCode;
      detailedErrorCode = LibraryConnector.getDetailedError(dsc);
      errorDescription = new ErrorDescription(detailedErrorCode);
   }

   public int getErrorCode() {
      return errorCode;
   }

   public Long getDetailedErrorCode() {
      return detailedErrorCode;
   }

   @Override
   public String getMessage() {
      return "Błąd Genie 2000, " + (StringUtils.isEmpty(super.getMessage())?"":"wiadomość orygnialna '" + super.getMessage() + "'")
              + "Kod błędu ogólnego '" + errorCode + "', " + (errorDescription!=null?errorDescription:"");
   }
}
