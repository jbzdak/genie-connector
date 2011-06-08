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

package cx.jbzdak.diesIrae.genieConnector;

import org.slf4j.Logger;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
class CloseAllVDMsHook extends Thread {

   private static final CloseAllVDMsHook HOOK;

   static {
      HOOK = new CloseAllVDMsHook();
      Runtime.getRuntime().addShutdownHook(HOOK);
   }

   public static void registerConnector(SimpleConnector connector) {
      HOOK.openConnectors.add(connector);
   }

   public static void deregisterConnector(SimpleConnector connector) {
      HOOK.openConnectors.remove(connector);
   }

   private final Collection<SimpleConnector> openConnectors = new ConcurrentLinkedQueue<SimpleConnector>();

   private static final Logger LOGGER = Utils.getLogger();

   @Override
   public void run() {
      for (SimpleConnector connector : openConnectors) {
         try {
            connector.closeNoCheck();
         } catch (Throwable e) {
            LOGGER.warn("", e);
         }
      }
   }
}
