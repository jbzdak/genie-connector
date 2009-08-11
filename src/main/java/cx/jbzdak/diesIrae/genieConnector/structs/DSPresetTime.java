package cx.jbzdak.diesIrae.genieConnector.structs;

import com.sun.jna.NativeLong;
import com.sun.jna.Union;

import java.io.Serializable;

/**
 * Union that configures readout time.
 *
 * Value that is read from this union by C code depends on value stored in
 * {@link cx.jbzdak.diesIrae.genieConnector.structs.DSPreset#flPsetMode}.
 *
 *  
 * Created by IntelliJ IDEA.
 * User: jbzdak
 * Date: 2009-08-05
 * Time: 14:58:57
 *
 */
public class DSPresetTime extends Union implements Serializable{

   public NativeLong sweep;

   public Double time;

   public Double getTime() {
      return time;
   }

   public void setTime(Double time) {
      this.time = time;
   }

   public NativeLong getSweep() {
      return sweep;
   }

   public void setSweep(NativeLong sweep) {
      this.sweep = sweep;
   }
}

