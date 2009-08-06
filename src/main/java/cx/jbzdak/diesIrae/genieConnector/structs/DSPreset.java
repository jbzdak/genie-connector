package cx.jbzdak.diesIrae.genieConnector.structs;

import com.sun.jna.NativeLong;
import com.sun.jna.Structure;
import cx.jbzdak.diesIrae.genieConnector.enums.PresetMode;

import java.io.Serializable;

/**
 * Controls readout preset.
 *
 * Created by IntelliJ IDEA.
 * User: jbzdak
 * Date: 2009-08-05
 * Time: 14:58:49
 * To change this template use File | Settings | File Templates.
 */
public class DSPreset extends Structure implements Serializable{
   /**
    * Preset mode. See {@link cx.jbzdak.diesIrae.genieConnector.enums.PresetMode} and
    * {@link #setFlPsetMode(cx.jbzdak.diesIrae.genieConnector.enums.PresetMode)} for
    * possible values.
    */
   private NativeLong flPsetMode;

   /**
    * Controls preset time.
    */
   private DSPresetTime dsPresetTime;

   /**
    * Have no idea what it does. . .
    */
   private double dPsetComp;

   /**
    * First channel that will be read
    */
   private NativeLong ulStartCh;

   /**
    * Last channel that will be read
    */
   private NativeLong ulStopCh;

   public void setFlPsetMode(PresetMode flPsetMode){
      updatePresetTime(flPsetMode);
      setFlPsetMode(new NativeLong(flPsetMode.getPresetValue()));
   }

   private void updatePresetTime(PresetMode flPsetMode){
       if(flPsetMode.getPsPresetTimeField() == null){
         throw new IllegalArgumentException("Cant set DSPresetTime value because this " +
                 "PresetMode is not properly configured.");
      }
      if(dsPresetTime!=null){
         dsPresetTime.setType(flPsetMode.getPsPresetTimeField());
      }
   }

   public NativeLong getFlPsetMode() {
      return flPsetMode;
   }

   void setFlPsetMode(NativeLong flPsetMode) {
      this.flPsetMode = flPsetMode;
   }

   public DSPresetTime getDsPresetTime() {
      return dsPresetTime;
   }

   public void setDsPresetTime(DSPresetTime dsPresetTime) {
      this.dsPresetTime = dsPresetTime;
      if(flPsetMode!=null){
         updatePresetTime(PresetMode.getPresetFromValue(flPsetMode.longValue()));
      }
   }

   public double getDPsetComp() {
      return dPsetComp;
   }

   public void setDPsetComp(double dPsetComp) {
      this.dPsetComp = dPsetComp;
   }

   public NativeLong getUlStartCh() {
      return ulStartCh;
   }

   public void setUlStartCh(NativeLong ulStartCh) {
      this.ulStartCh = ulStartCh;
   }

   public NativeLong getUlStopCh() {
      return ulStopCh;
   }

   public void setUlStopCh(NativeLong ulStopCh) {
      this.ulStopCh = ulStopCh;
   }
}
