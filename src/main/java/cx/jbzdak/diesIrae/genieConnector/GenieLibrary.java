package cx.jbzdak.diesIrae.genieConnector;

import com.sun.jna.Library;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.NativeLongByReference;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.ptr.ShortByReference;
import cx.jbzdak.diesIrae.genieConnector.structs.DSQuery;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
@SuppressWarnings({"MethodWithTooManyParameters", "UnusedReturnValue"})
interface GenieLibrary extends Library{

    public void vG2KEnv();

    /** 
     * @param hDSC Inicjalizowany wskaźnik na źródło danych
     * @param zero Parametr który musi być zerem
     * @param zero2 Kolejny parametr który musi być zerem
     * @return zero jak OK inaczej że źle
     */
    @SuppressWarnings({"SameParameterValue"})
    public int iUtlCreateFileDSC(PointerByReference hDSC, int zero, int zero2);

       /**
     * @param hDSC Inicjalizowany wskaźnik na źródło danych
     * @param zero Parametr który musi być zerem
     * @param zero2 Kolejny parametr który musi być zerem
     * @return zero jak OK inaczej że źle
     */
    @SuppressWarnings({"SameParameterValue"})
    public int iUtlCreateFileDSC2(PointerByReference hDSC, int zero, int zero2);

    /**
     *
     * @param dscPointer dsc którego status sobie czytamy
     * @param result wynik działania
     * @param dummy1 coś co musi tam być, ale się nie wykorzystuje
     * @param dummy2 patrz dummy1
     * @return kurwa! nie wiem co zwraca, error kod sprawdzenia błędu??
     */
    public short SadGetStatus (DscPointer dscPointer, NativeLongByReference result, ShortByReference dummy1, ShortByReference dummy2);


    /**
     * 
     * @param dsc dsc dla ktorego otwieramy dataSource
     * @param sourceName nazwa źródła (moze być kwalifikowana nazwa pliku (bez spacji w środku??)
     * @param type typ
     * @param acces tryb dostępu
     * @param verify ???
     * @param shellId dla nas ""
     * @return kod błędu
     */
    public short SadOpenDataSource(DscPointer dsc, String sourceName, short type, short acces, short verify, String shellId);

    /**
     * Zwraca parametr CAMA
     * @param hDSC źródło
     * @param ulParam kod parametry
     * @param usRecord rekord??
     * @param usEntry entry??
     * @param result tutaj zapiszemy wynik
     * @param usExpect rozmiar <code>result</code> w bajtach
     * @return kod błedu.
     */
    public short SadGetParam(DscPointer hDSC, NativeLong ulParam, short usRecord,   short usEntry, byte[] result, short usExpect );


    public short  SadPutParam(DscPointer hDSC, NativeLong ulParam, short usRecord,   short usEntry, byte[] result, short usExpect );

    /**
     * Pobiera spektralne dane
     * @param hDSC żródło danych
     * @param usStart
     * @param usCount
     * @param fFloat
     * @param pvData
     * @return
     */
    public short SadGetSpectrum(DscPointer hDSC, short usStart, short usCount, short fFloat, byte pvData );

    /**
     * Zamyka żródło danych otwarte przes DSC
     * @param dsc dsc
     * @return kod błędu
     */
    public short SadCloseDataSource(DscPointer dsc);

    /**
     * Dealokuje ten kawałek biblioteki :)
     * @param dsc dsc
     * @return kod błędu
     */
    public short SadDeleteDSC(DscPointer dsc);

    public short SadPutStruct(DscPointer sdc, short structType, short record, short entry, Pointer ptr, short structSize);

    /**
     * Powoduje wyplucie danych do pliku/urządzenia
     * @param dsc dsc
     * @return kod błędu
     */
    public short SadFlush(DscPointer dsc);

    public short SadControlDSC(DscPointer dsc, short usDevice, short usOpCode);

    public short SadGetSpectrum(DscPointer dsc, short start, short count, short useFloats, int[] result);

    public short SadSetSpectrum(DscPointer dsc, short start, short count, byte[] input);

    public short SadQueryDataSource(DscPointer dscPointer, short opCode, DSQuery result);
}
