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

package cx.jbzdak.diesIrae.genieConnector;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import cx.jbzdak.diesIrae.genieConnector.enums.*;
import cx.jbzdak.diesIrae.genieConnector.enums.param.ParamAlias;
import cx.jbzdak.diesIrae.genieConnector.enums.param.Parameter;
import cx.jbzdak.diesIrae.struct.Preset;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.EnumSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
public class SimpleConnector {

   protected final DscPointer dsc;

   private ConnectorState connectorState = ConnectorState.NOT_OPENED;

   private FlushType flush = FlushType.MANUAL;

   private DeviceType deviceType = DeviceType.MCA;

   private short startChannel = 1;

   private short endChannel = 1;

   protected final PropertyChangeSupport support = new PropertyChangeSupport(this);

   public SimpleConnector() {
      PointerByReference dsc = new PointerByReference(Pointer.NULL);
      try {
         LibraryWrapper.iUtlCreateFileDSC2(dsc);
         CloseAllVDMsHook.registerConnector(this);
      } catch (ConnectorException e) {
         throw new GenieException("Error while opening VDM", e.getCode());
      }
      this.dsc = new DscPointer(dsc.getValue());
   }

   public void openFile(final File file, final EnumSet<OpenMode> mode) {
      assertMayOpen();
      doCall(new Call<Void>() {
         @Override Void doCall() throws ConnectorException {
            LibraryWrapper.openDatasource(dsc, file.getAbsolutePath(), SourceType.FILE, mode, false, "");
            setConnectorState(ConnectorState.OPEN);
            return null;
         }
      });
   }

   public void openSource(final String datasource, final EnumSet<OpenMode> mode, final SourceType type) {
      assertMayOpen();
      doCall(new Call<Void>() {
         @Override Void doCall() throws ConnectorException {
            LibraryWrapper.openDatasource(dsc, datasource, type, mode, true, "");
            setConnectorState(ConnectorState.OPEN);
            return null;
         }
      }, "datasource='" + datasource + "'", "mode='" + mode + "'");
   }

   public Set<Status> getStatus() {
      return doCall(new Call<Set<Status>>() {
         @Override Set<Status> doCall() throws ConnectorException {
            return LibraryWrapper.getStatus(dsc);
         }
      });
   }

   /**
    * Returns live time in seconds
    */
   public double getLiveTime(){
      return  getParam(ParamAlias.LIVE_TIME, 0, 0);
   }

   public CoincidenceMode getCoincidenceMode(){
      return CoincidenceMode.readLong(getParam(ParamAlias.COINCIDENCE_MODE, 1, 1));
   }

   public void setCoincidenceMode(CoincidenceMode coincidenceMode){
      setParam(ParamAlias.COINCIDENCE_MODE, coincidenceMode.getValue(), 1, 1);
   }

   /**
    * Check whether detector is currently acquiring data.
    * This is a helper method it is equivalent of
    * <code>getStatus().contains(Status.BUSY)</code>.
    *
    * @return true if detector is acquiring data
    */
   public boolean isAcquiring() {
      return getStatus().contains(Status.BUSY);
   }

   public <T> T getParam(final Parameter<T> parameter, final int record, final int entry) {
      assertOpened();
      return doCall(new Call<T>() {
         @Override
         public T doCall() throws ConnectorException {
            return LibraryWrapper.getParam(dsc, parameter, (short) record, (short) entry);
         }
      });
   }

   public <T> T getParam(final Parameter<T> parameter) {
      return getParam(parameter, 1, 1);
   }

   public <T> void setParam(final Parameter<T> parameter, final T value, final int record, final int entry) {
      assertOpened();
      doCall(new Call<Void>() {
         @Override
         public Void doCall() throws ConnectorException {
            LibraryWrapper.setParam(dsc, parameter, value, (short) record, (short) entry);
            if (flush == FlushType.AUTO_COMMIT) {
               flush();
            }
            return null;
         }
      });
   }

   public void controlDSC(final OpCode opCode) {
      assertOpened();
      doCall(new Call<Void>() {
         @Override Void doCall() throws ConnectorException {
            LibraryWrapper.controlDSC(dsc, deviceType, opCode);
            return null;
         }
      });
   }

   public <T> void setParam(final Parameter<T> parameter, final T value) {
      setParam(parameter, value, 1, 1);
   }

   public void flush() {
      doCall(new Call<Void>() {
         @Override Void doCall() throws ConnectorException {
            LibraryWrapper.flush(dsc);
            return null;
         }
      });
   }

   SpectrometricResult getSpectrometricData(final int start, final int end) {
      return doCall(new Call<SpectrometricResult>() {
         @Override SpectrometricResult doCall() throws ConnectorException {
            SpectrometricResult lastResult = new SpectrometricResult((short) start, (short) end, LibraryWrapper.getSpectralData(dsc, (short) start, (short) end));
            //setLastResult(lastResult);
            return lastResult;
         }
      }, "start= " + start, "end = " + end);
   }

   public void setSpectrometricData(final SpectrometricResult data) {
      doCall(new Call<Object>() {
         @Override Object doCall() throws ConnectorException {
            LibraryWrapper.putSpectrum(dsc, data);
            return null;
         }
      }, "data = " + data);
   }

   public void setStructure(final GenieStructure structure){
      doCall(new Call<Object>() {
         @Override Object doCall() throws ConnectorException {
            LibraryWrapper.putStruct(dsc, structure);
            return null;  //To change body of implemented methods use File | Settings | File Templates.
         }
      }, "structure="+ structure);
   }

   public SpectrometricResult getSpectrometricData() {
      return getSpectrometricData(startChannel, endChannel);
   }

   protected void closeNoCheck() {
      doCall(new Call<Void>() {
         @Override Void doCall() throws ConnectorException {
            if (getConnectorState() == ConnectorState.OPEN) {
               LibraryWrapper.closeDataSource(dsc);
            }
            LibraryWrapper.close(dsc);
            CloseAllVDMsHook.deregisterConnector(SimpleConnector.this);
            return null;
         }

         @Override void doFinally() {
            setConnectorState(ConnectorState.CLOSED);
         }
      });
   }

   public void close() {
      if (getConnectorState() == ConnectorState.CLOSED) {
         throw new IllegalStateException();
      }
      closeNoCheck();
   }


   public void setPreset(final Preset preset) {
      assertOpened();
      doCall(new Call<Object>() {
         @Override Object doCall() throws ConnectorException {
            LibraryWrapper.putStruct(dsc, preset);
            return null;  //To change body of implemented methods use File | Settings | File Templates.
         }
      });
   }

   public FlushType getFlush() {
      return flush;
   }

   public void setFlush(FlushType flush) {
      this.flush = flush;
   }

   public DeviceType getDeviceType() {
      return deviceType;
   }

   public void setDeviceType(DeviceType deviceType) {
      this.deviceType = deviceType;
   }

   public void setEndChannel(int endChannel) {
      int oldEndChannel = this.endChannel;
      this.endChannel = (short) endChannel;
      support.firePropertyChange("endChannel", oldEndChannel, this.endChannel);
   }

   public int getEndChannel() {
      return endChannel;
   }

   public void setStartChannel(int startChannel) {
      int oldStartChannel = this.startChannel;
      this.startChannel = (short) startChannel;
      support.firePropertyChange("startChannel", oldStartChannel, this.startChannel);
   }

   public int getStartChannel() {
      return startChannel;
   }

   protected void assertOpened() {
      if (ConnectorState.OPEN != connectorState) {
         throw new IllegalStateException("Can't call this method on closed or uninitialized Connector");
      }
   }

   protected void assertMayOpen() {
      if (getConnectorState() != ConnectorState.NOT_OPENED) {
         throw new IllegalStateException("Can't call this method on opened Connector");
      }
   }

   public ConnectorState getConnectorState() {
      return connectorState;
   }

   void setConnectorState(ConnectorState connectorState) {
      ConnectorState oldConnectorState = this.connectorState;
      this.connectorState = connectorState;
      support.firePropertyChange("connectorState", oldConnectorState, this.connectorState);
   }

   public void addPropertyChangeListener(PropertyChangeListener listener) {
      support.addPropertyChangeListener(listener);
   }

   public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
      support.addPropertyChangeListener(propertyName, listener);
   }

   public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
      support.removePropertyChangeListener(propertyName, listener);
   }

   public boolean hasListeners(String propertyName) {
      return support.hasListeners(propertyName);
   }

   public PropertyChangeListener[] getPropertyChangeListeners() {
      return support.getPropertyChangeListeners();
   }

   public void removePropertyChangeListener(PropertyChangeListener listener) {
      support.removePropertyChangeListener(listener);
   }

   public <T> T doCall(Call<T> call, Object... additionalInfo) throws GenieException {
      try {
         return call.doCall();
      } catch (ConnectorException e) {
         throw new GenieException(e.getCode(), dsc, e, additionalInfo);
      } finally {
         call.doFinally();
      }
   }

   private static abstract class Call<T> {
      abstract T doCall() throws ConnectorException;

      void doFinally() {
      }
   }
}
