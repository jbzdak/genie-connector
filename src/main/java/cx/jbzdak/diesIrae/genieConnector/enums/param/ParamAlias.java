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

package cx.jbzdak.diesIrae.genieConnector.enums.param;

import cx.jbzdak.diesIrae.genieConnector.ParameterType;
import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
public class
        ParamAlias<T> implements Parameter<T> {

   public static final ParamAlias<String> SAMPLE_IDENTIFIER = new ParamAlias<String>(Parameters.T_SIDENT);

   public static final ParamAlias<Float> SAMPLE_QUANTITY = new ParamAlias<Float>(Parameters.F_SQUANT);

   public static final ParamAlias<Long> CHANNEL_NUMBER = new ParamAlias<Long>(Parameters.L_CHANNELS);

   /**
    * Steruje koincydencją. Jeśli ma wartość 0 to koincydencja jest wyłączona, jeśli 1 włączona.
    */
   public static final ParamAlias<Long> COINCIDENCE_MODE = new ParamAlias<Long>(Parameters.L_ADCFANTIC);

   public static final ParamAlias<Double> LIVE_TIME = new ParamAlias<Double>(Parameters.X_ELIVE);

   /**
    * Maximum number of gain passes
    */
   public static final ParamAlias<Long> MAX_GAIN_PASS= new ParamAlias<Long>(Parameters.L_MAXGAINPASS);

   private final ParamImpl internal;

   ParamAlias(ParamImpl internal) {
      this.internal = internal;
   }

   @NonNull
   public ParameterType<T> getType() {
      return internal.getType();
   }

   public String getName() {
      return internal.getName();
   }

   @NonNull
   public long getParamId() {
      return internal.getParamId();
   }
}
