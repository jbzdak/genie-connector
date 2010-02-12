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

package cx.jbzdak.diesIrae.genieConnector.enums;

import com.sun.jna.NativeLong;

/**
 * Controls preset setting method. See description of fields. Used by
 * {@link cx.jbzdak.diesIrae.genieConnector.structs.DSPreset}.
 * <p/>
 * Created by IntelliJ IDEA.
 * User: jbzdak
 * Date: 2009-08-06
 * Time: 12:52:54
 */
public enum PresetMode {
   /**
    * Readout live time.
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

   public static PresetMode getPresetFromValue(long presetValue) {
      for (PresetMode presetMode : values()) {
         if (presetMode.getPresetValue() == presetValue) {
            return presetMode;
         }
      }
      throw new IllegalArgumentException("Unknown presetValue '" + presetValue + "'");
   }

   private final long presetValue;

   /**
    * Class of a field that should be used from union {@link cx.jbzdak.diesIrae.genieConnector.structs.DSPresetTime}
    * when using this preset.
    * <p/>
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
