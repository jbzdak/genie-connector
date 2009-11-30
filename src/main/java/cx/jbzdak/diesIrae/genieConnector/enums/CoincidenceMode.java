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
   COINCIDENCE(0),
   /**
    * Coincidence is disabled,
    * and anticoincidence input is active
    */
   ANTI_COINCIDENCE(1);

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
