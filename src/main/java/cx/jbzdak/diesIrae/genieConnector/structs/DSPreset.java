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

package cx.jbzdak.diesIrae.genieConnector.structs;

import com.sun.jna.NativeLong;
import com.sun.jna.Structure;
import cx.jbzdak.diesIrae.genieConnector.enums.PresetMode;

import java.io.Serializable;

/**
 * Controls readout preset.
 * <p/>
 * Created by IntelliJ IDEA.
 * User: jbzdak
 * Date: 2009-08-05
 * Time: 14:58:49
 * To change this template use File | Settings | File Templates.
 */
public class DSPreset extends Structure implements Serializable {
   /**
    * Preset mode. See {@link cx.jbzdak.diesIrae.genieConnector.enums.PresetMode} and
    * {@link #setFlPsetMode(cx.jbzdak.diesIrae.genieConnector.enums.PresetMode)} for
    * possible values.
    */
   public NativeLong flPsetMode;

   /**
    * Controls preset time.
    */
   public DSPresetTime dsPresetTime;

   /**
    * Have no idea what it does. . .
    */
   public double dPsetComp;

   /**
    * First channel that will be read
    */
   public NativeLong ulStartCh= new NativeLong(0);

   /**
    * Last channel that will be read
    */
   public NativeLong ulStopCh= new NativeLong(0);

   public DSPreset() {
      setAlignType(ALIGN_NONE);
   }

   public void setFlPsetMode(PresetMode flPsetMode) {
      updatePresetTime(flPsetMode);
      setFlPsetMode(new NativeLong(flPsetMode.getPresetValue()));
   }

   private void updatePresetTime(PresetMode flPsetMode) {
      if (flPsetMode.getPsPresetTimeField() == null) {
         throw new IllegalArgumentException("Cant set DSPresetTime value because this " +
                 "PresetMode is not properly configured.");
      }
      if (dsPresetTime != null) {
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
      if (flPsetMode != null && flPsetMode.longValue() != 0) {
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
