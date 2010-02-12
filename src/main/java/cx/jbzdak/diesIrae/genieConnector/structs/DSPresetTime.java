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

package cx.jbzdak.diesIrae.genieConnector.structs;

import com.sun.jna.NativeLong;
import com.sun.jna.Union;

import java.io.Serializable;

/**
 * Union that configures readout time.
 * <p/>
 * Value that is read from this union by C code depends on value stored in
 * {@link cx.jbzdak.diesIrae.genieConnector.structs.DSPreset#flPsetMode}.
 * <p/>
 * <p/>
 * Created by IntelliJ IDEA.
 * User: jbzdak
 * Date: 2009-08-05
 * Time: 14:58:57
 */
public class DSPresetTime extends Union implements Serializable {
   
   public Double time;

   public NativeLong sweep;

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

