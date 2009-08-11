package cx.jbzdak.diesIrae.genieConnector.enums;

import com.sun.jna.NativeLong;

/**
 * Controls preset setting method. See description of fields. Used by
 * {@link cx.jbzdak.diesIrae.genieConnector.structs.DSPreset}.
 *
 * Created by IntelliJ IDEA.
 * User: jbzdak
 * Date: 2009-08-06
 * Time: 12:52:54
 */
public enum PresetMode {
   /**
      Readout live time.
    */
   LIVE(1L, Double.class),
   /**
    * Readout real time
    */
   REAL(2L, Double.class),
   /**
    * Nmber of sweeps
    */
   SWEEPS(10L, NativeLong.class),
   /**
    * Total collected events
    */
   TOTAL(4L, null),
   /**
    * No fuking idea: preset counts
    */
   LEVEL(8L, null),
   /**
    * NFI: preset area
    */
   AREA(0x20L, null);

   public static PresetMode getPresetFromValue(long presetValue){
      for(PresetMode presetMode : values()){
         if(presetMode.getPresetValue() == presetValue){
            return presetMode;
         }
      }
      throw new IllegalArgumentException("Unknown presetValue '" + presetValue + "'");
   }

   private final long presetValue;

   /**
    * Class of a field that should be used from union {@link cx.jbzdak.diesIrae.genieConnector.structs.DSPresetTime}
    * when using this preset.
    *
    * Null means 'I have no idea, and dont need it :)'. s
    */
   private final Class psPresetTimeField;

   PresetMode(long presetValue, Class psPresetTimeField) {
      this.presetValue = presetValue;
      this.psPresetTimeField = psPresetTimeField;
   }

   public Class getPsPresetTimeField() {
      return psPresetTimeField;
   }

   public long getPresetValue() {
      return presetValue;
   }

   
}
