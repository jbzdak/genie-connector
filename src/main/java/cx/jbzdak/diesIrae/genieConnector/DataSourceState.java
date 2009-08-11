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

   EnumSet<DataSourceState> computeFromFlagInt(short flags){
      EnumSet<DataSourceState> result = EnumSet.noneOf(DataSourceState.class);
      for (DataSourceState dataSourceState : values()) {
         if((flags & dataSourceState.value) != 0){
            result.add(dataSourceState);
         }
      }
      return result;
    }
}
