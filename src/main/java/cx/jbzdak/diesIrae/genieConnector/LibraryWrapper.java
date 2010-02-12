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

import com.sun.jna.Structure;
import com.sun.jna.ptr.NativeLongByReference;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.ptr.ShortByReference;

import java.nio.IntBuffer;
import java.util.EnumSet;

import cx.ath.jbzdak.spectrometric.api.SpectrometricResult;
import cx.jbzdak.diesIrae.genieConnector.enums.*;
import cx.jbzdak.diesIrae.genieConnector.enums.param.Parameter;
import cx.jbzdak.diesIrae.genieConnector.structs.DSPreset;
import cx.jbzdak.diesIrae.genieConnector.structs.DSQuery;
import cx.jbzdak.diesIrae.genieConnector.structs.DSResult;

@SuppressWarnings({"StaticMethodOnlyUsedInOneClass"}) class LibraryWrapper {

   /**
    * Tylko po to żeby się delegaty generowały same ;) do wywanenia
    */
   private static final GenieLibrary GENIE_LIBRARY = GenieConnectorStaticData.GENIE_LIBRARY;

   /**
    * Maksymalna ilość kanałów które można przeczytać jednym wywołaniem SadGetSpectrometry
    */
   private static final int MAX_CHANNELS_READ_AT_ONCE = 4000;


   static void iUtlCreateFileDSC(PointerByReference hDSC) throws ConnectorException {
      checkError(GENIE_LIBRARY.iUtlCreateFileDSC(hDSC, 0, 0));
   }

   static void iUtlCreateFileDSC2(PointerByReference hDSC) throws ConnectorException {
      checkError(GENIE_LIBRARY.iUtlCreateFileDSC2(hDSC, 0, 0));
   }

   @SuppressWarnings({"MethodWithTooManyParameters"})
   static void openDatasource(DscPointer dscPointer, String name, SourceType type, EnumSet<OpenMode> openMode, boolean verify, String shellName) throws ConnectorException {
      checkError(GENIE_LIBRARY.SadOpenDataSource(dscPointer, name, type.value, OpenMode.getLogicalSum(openMode), (short) (verify ? 1 : 0), shellName));
   }

   static void closeDataSource(DscPointer dsc) throws ConnectorException {
      checkError(GENIE_LIBRARY.SadCloseDataSource(dsc));
   }

   static void close(DscPointer dsc) throws ConnectorException {
      checkError(GENIE_LIBRARY.SadDeleteDSC(dsc));
   }

   static long getDetailedError(DscPointer dsc) {
      NativeLongByReference details = new NativeLongByReference();
      ShortByReference s1 = new ShortByReference(), s2 = new ShortByReference();
      GenieConnectorStaticData.GENIE_LIBRARY.SadGetStatus(dsc, details, s1, s2);
      return details.getValue().longValue();
   }

   static void controlDSC(DscPointer dsc, DeviceType deviceType, OpCode opCode) throws ConnectorException {
      short errorCode = GENIE_LIBRARY.SadControlDSC(dsc, deviceType.getValue(), opCode.getValue());
      checkError(errorCode);
   }

   static void putSpectrum(DscPointer dsc, SpectrometricResult spectrometricResult) throws ConnectorException {
      int[] results = new int[spectrometricResult.getLastChannel() - spectrometricResult.getFirstChannel()];
      for (int ii = 0; ii < results.length; ii++) {
         results[ii] = spectrometricResult.get(spectrometricResult.getFirstChannel() + ii);
      }
      checkError(GENIE_LIBRARY.SadPutSpectrum(dsc, (short) spectrometricResult.getFirstChannel(), (short) spectrometricResult.getChannelNo(), results));
   }

   static <T> T getParam(DscPointer dsc, Parameter<T> param, short record, short entry) throws ConnectorException {
      return param.getType().readParam(GENIE_LIBRARY, dsc, param, record, entry);
   }

   static <T> void setParam(DscPointer dsc, Parameter<T> param, T value, short record, short entry) throws ConnectorException {
     param.getType().writeParam(GENIE_LIBRARY, value, dsc, param, record, entry);
   }

   static void flush(DscPointer dsc) throws ConnectorException {
      checkError(GENIE_LIBRARY.SadFlush(dsc));
   }

   public static IntBuffer getSpectralData(DscPointer dsc, short startChan, short endChan) throws ConnectorException {
      IntBuffer resultBuffer = IntBuffer.allocate(endChan - startChan);
      int currentStartIdx = startChan;
      while (currentStartIdx < endChan) {
         int[] result = new int[Math.min(endChan - currentStartIdx, MAX_CHANNELS_READ_AT_ONCE)];
         int channelsRead = Math.min(endChan - currentStartIdx, MAX_CHANNELS_READ_AT_ONCE);
         short errorCode = GENIE_LIBRARY.SadGetSpectrum(dsc, (short) currentStartIdx, (short) channelsRead, (short) 0, result);
         checkError(errorCode);
         currentStartIdx += channelsRead;
         resultBuffer.put(result, 0, channelsRead);
      }
      return resultBuffer;

   }

   public static EnumSet<Status> getStatus(DscPointer dscPointer) throws ConnectorException {
      DSQuery query = new DSQuery();
      query.setType(DSResult.class);
      short errorCode = GENIE_LIBRARY.SadQueryDataSource(dscPointer, (short) 1, query);
      checkError(errorCode);
      @SuppressWarnings({"CastToConcreteClass"})
      DSResult result = (DSResult) query.getTypedValue(DSResult.class);
      return Status.decode(result.getStatus());

   }

   public static void putStruct(DscPointer dscPointer, Structure structure) throws ConnectorException {
      putStruct(dscPointer, structure, (short) 1, (short) 1);
   }

   public static void putStruct(DscPointer dscPointer, Structure structure, short entry, short record) throws ConnectorException {
      checkError(GENIE_LIBRARY.SadPutStruct(dscPointer, StructureType.getStructureId(structure), record, entry, structure.getPointer(), (short) structure.size()));
   }

   public static void putStruct(DscPointer dscPointer, GenieStructure genieStructure) throws ConnectorException {
      checkError(GENIE_LIBRARY.SadPutStruct(dscPointer, genieStructure.getStructureId(), genieStructure.getRecord(), genieStructure.getEntry(), genieStructure.getStructure(), genieStructure.getSize()));
   }

   public static void setPreset(DscPointer dscPointer, DSPreset preset) throws ConnectorException {
      putStruct(dscPointer, preset);
   }


   static void checkError(short errorCode) throws ConnectorException {
      if (errorCode != 0) {
         throw new ConnectorException(errorCode);
      }
   }

   static void checkError(int errorCode) throws ConnectorException {
      if (errorCode != 0) {
         throw new ConnectorException(errorCode);
      }
   }
}
