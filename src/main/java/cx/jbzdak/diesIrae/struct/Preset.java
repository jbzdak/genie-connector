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

package cx.jbzdak.diesIrae.struct;

import com.sun.jna.Structure;
import cx.jbzdak.diesIrae.genieConnector.AbstractGenieStructure;
import cx.jbzdak.diesIrae.genieConnector.enums.PresetMode;
import cx.jbzdak.diesIrae.genieConnector.structs.DSPreset;
import cx.jbzdak.diesIrae.genieConnector.structs.DSPresetTime;

import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: jbzdak
 * Date: 2009-11-19
 * Time: 13:29:16
 */
public class Preset extends AbstractGenieStructure{

   DSPreset dsPreset = new DSPreset();

   private PresetMode presetMode = PresetMode.LIVE;

   private Long area;

   private Long time;

   private Long startChannel, stopChannel;

   private TimeUnit timeUnit;    

   public Preset() {
      super((short) 1,(short) 1,(short) 28);
   }


   @Override public Structure getStructure() {
      initPreset();
      return dsPreset;
   }

   private void initPreset() {
      dsPreset = new DSPreset();
      if(!EnumSet.of(PresetMode.LIVE, PresetMode.REAL).contains(presetMode)){
         throw new UnsupportedOperationException("Modes other than LIVE and REAL are unsupported for now.");
      }
      if(time==null || timeUnit == null){
         throw new IllegalStateException("preset time not set");
      }
      DSPresetTime time = new DSPresetTime();
      time.setTime(timeUnit.toMillis(this.time)/1000.0);
      dsPreset.setDsPresetTime(time);
      dsPreset.setFlPsetMode(presetMode);
   }

   @Override public short getSize() {
      return (short) 28;
   }

   public PresetMode getPresetMode() {
      return presetMode;
   }

   public void setPresetMode(PresetMode presetMode) {
      this.presetMode = presetMode;
   }

   public Long getArea() {
      return area;
   }

   public void setArea(Long area) {
      this.area = area;
   }

   public void setTime(long time, TimeUnit timeUnit){
      this.time = time;
      this.timeUnit = timeUnit;
   }

   public Long getStartChannel() {
      return startChannel;
   }

   public void setStartChannel(Long startChannel) {
      this.startChannel = startChannel;
   }

   public Long getStopChannel() {
      return stopChannel;
   }

   public void setStopChannel(Long stopChannel) {
      this.stopChannel = stopChannel;
   }

   public TimeUnit getTimeUnit() {
      return timeUnit;
   }
}
