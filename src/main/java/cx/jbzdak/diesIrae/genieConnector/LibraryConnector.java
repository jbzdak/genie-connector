package cx.jbzdak.diesIrae.genieConnector;

import com.sun.jna.NativeLong;
import com.sun.jna.Structure;
import com.sun.jna.ptr.NativeLongByReference;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.ptr.ShortByReference;
import cx.jbzdak.diesIrae.genieConnector.enums.*;
import cx.jbzdak.diesIrae.genieConnector.enums.param.Parameter;
import cx.jbzdak.diesIrae.genieConnector.structs.DSPreset;
import cx.jbzdak.diesIrae.genieConnector.structs.DSQuery;
import cx.jbzdak.diesIrae.genieConnector.structs.DSResult;

import java.nio.IntBuffer;
import java.util.EnumSet;

@SuppressWarnings({"StaticMethodOnlyUsedInOneClass"})
class LibraryConnector{

    /**
     * Tylko po to żeby się delegaty generowały same ;) do wywanenia
     */
    private static final GenieLibrary GENIE_LIBRARY = GenieMapperStaticMagic.GENIE_LIBRARY;

    /**
     * Maksymalna ilość kanałów które można przeczytać jednym wywołaniem SadGetSpectrometry
     * */
    private static final int MAX_CHANNELS_READ_AT_ONCE = 4000;


    static void iUtlCreateFileDSC(PointerByReference hDSC) throws ConnectorException {
        checkError(GENIE_LIBRARY.iUtlCreateFileDSC(hDSC,0,0));
    }

    static void iUtlCreateFileDSC2(PointerByReference hDSC) throws ConnectorException {
        checkError(GENIE_LIBRARY.iUtlCreateFileDSC2(hDSC,0,0));
    }

    @SuppressWarnings({"MethodWithTooManyParameters"})
    static void openDatasource(DscPointer dscPointer, String name, SourceType type, EnumSet<OpenMode> openMode, boolean verify, String shellName)throws ConnectorException {
         checkError(GENIE_LIBRARY.SadOpenDataSource(dscPointer, name, type.value,OpenMode.getLogicalSum(openMode),(short)(verify?1:0),shellName));
    }

    static void closeDataSource(DscPointer dsc) throws ConnectorException  {
        checkError(GENIE_LIBRARY.SadCloseDataSource(dsc));
    }

    static void close(DscPointer dsc) throws ConnectorException  {
        checkError(GENIE_LIBRARY.SadDeleteDSC(dsc));
    }

    static long getDetailedError(DscPointer dsc){
        NativeLongByReference details  = new NativeLongByReference();
        ShortByReference s1 = new ShortByReference(), s2 = new ShortByReference();
        GenieMapperStaticMagic.GENIE_LIBRARY.SadGetStatus(dsc, details, s1, s2);
        return details.getValue().longValue();
    }

    static void controlDSC(DscPointer dsc, DeviceType deviceType, OpCode opCode) throws ConnectorException {
        short errorCode = GENIE_LIBRARY.SadControlDSC(dsc, deviceType.getValue(), opCode.getValue());
        checkError(errorCode);
    }

     static <T> T getParam(DscPointer dsc, Parameter<T> param, short record, short entry) throws ConnectorException {
         byte[] result = new byte[param.getByteLenght()];
         short errorCode = GENIE_LIBRARY.SadGetParam(dsc, new NativeLong(param.getParamId()),record, entry,result,param.getByteLenght());
         checkError(errorCode);
         return param.getType().readArray(result);
     }

      static <T> void setParam(DscPointer dsc, Parameter<T> param, T value, short record, short entry) throws ConnectorException {
         byte[] bytes = param.getType().writeArray(value);          
         short errorCode = GENIE_LIBRARY.SadPutParam(dsc, new NativeLong(param.getParamId()),record, entry,bytes,param.getByteLenght());
         checkError(errorCode);
     }

    static void flush(DscPointer dsc) throws ConnectorException {
        checkError(GENIE_LIBRARY.SadFlush(dsc));
    }

    public static IntBuffer getSpectralData(DscPointer dsc, short startChan, short endChan) throws ConnectorException {

        IntBuffer resultBuffer = IntBuffer.allocate(endChan - startChan);
        int currentStartIdx = startChan;
        while(currentStartIdx<endChan){
            int[] result = new int[Math.min(endChan - currentStartIdx, MAX_CHANNELS_READ_AT_ONCE)];
            int channelsRead = Math.min(endChan - currentStartIdx, MAX_CHANNELS_READ_AT_ONCE);
            short errorCode = GENIE_LIBRARY.SadGetSpectrum(dsc, (short) currentStartIdx, (short) channelsRead, (short)0, result);
            checkError(errorCode);
            currentStartIdx += channelsRead;
            resultBuffer.put(result, 0, channelsRead);
        }
        return resultBuffer;

    }
//
//    private static IntBuffer getSpectralData(DscPointer dsc, short startChan, short channelNumber) throws ConnectorException{
//        if(channelNumber>=4000){
//            throw new IllegalArgumentException();
//        }
//        byte[] resultBytes = new byte[channelNumber * ParameterType.WORD.getByteLenght("")];
//        short errorCode = GENIE_LIBRARY.SadGetSpectrum(dsc, startChan, channelNumber, (short)0, resultBytes);
//        checkError(errorCode);
//
//
//        return IntBuffer.wrap(result);
//    }

//    /**
//     * Żeby było jasne - ja nie wiem dlaczgeo tak trzeba robić - fakt jest taki że SadGetSpectrum zwraca bajty w złej kolejności
//     * tj - jeśli liczba zliczeń w kanale wynosi 1 to dana komórka w tabeli zawierać będzie bajty w takiej kolejności:
//     * 1, 0, 0, 0. Żeby to skonwertować na inta trzeba to odwrócić. . .
//     * @param resultBytes
//     * @return
//     */
//    private static int[] getSpectrometricInts(byte[] resultBytes){
//        if(resultBytes.length%4!=0){
//            throw new IllegalArgumentException();
//        }
//        Native.
//        int channelNumber = resultBytes.length/4;
//        int[] result = new int[channelNumber];
//        byte[] integerBytes = new byte[4];
//        for(int ii = 0; ii <(channelNumber); ii++){
//            //System.out.print(resultBytes[ii]);
//             for(int jj=0; jj<4; jj++){
//                integerBytes[jj] = resultBytes[(4*ii)+3-jj];
//             }
//            result[ii] = ByteBuffer.wrap(integerBytes).getInt();
//        }
//        return result;
//    }

    public static EnumSet<Status> getStatus(DscPointer dscPointer)throws ConnectorException{
        DSQuery query = new DSQuery();
        query.setType(DSResult.class);
        short errorCode = GENIE_LIBRARY.SadQueryDataSource(dscPointer, (short)1, query);
        checkError(errorCode);
        @SuppressWarnings({"CastToConcreteClass"})
        DSResult result = (DSResult) query.getTypedValue(DSResult.class);
        return Status.decode(result.getStatus());

    }

   public static void putStruct(DscPointer dscPointer, Structure structure) throws ConnectorException{
      putStruct(dscPointer, structure, (short) 1, (short) 1);
   }

   public static void putStruct(DscPointer dscPointer, Structure structure, short entry, short record)throws ConnectorException{
      checkError(GENIE_LIBRARY.SadPutStruct(dscPointer, StructureType.getStructureId(structure), record, entry, structure.getPointer(), (short) structure.size()));
   }

   public static void setPreset(DscPointer dscPointer, DSPreset preset) throws ConnectorException{
      putStruct(dscPointer, preset);
   }


    private static void checkError(short errorCode)throws ConnectorException{
        if(errorCode!=0){
            throw new ConnectorException(errorCode);
        }
    }

     private static void checkError(int errorCode)throws ConnectorException{
        if(errorCode!=0){
            throw new ConnectorException(errorCode);
        }
    }




}
