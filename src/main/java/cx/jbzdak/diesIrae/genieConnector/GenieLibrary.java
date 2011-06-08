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

import com.sun.jna.Library;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.DoubleByReference;
import com.sun.jna.ptr.NativeLongByReference;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.ptr.ShortByReference;
import cx.jbzdak.diesIrae.genieConnector.structs.DSQuery;

@SuppressWarnings({"MethodWithTooManyParameters", "UnusedReturnValue"})

interface GenieLibrary extends Library {
   /**
    * Inicjalizuje środowisko z danych z rejestru
    */
   public void vG2KEnv();

   /**
    * Inicjalizuje hDsc
    *
    * @param hDSC  Inicjalizowany wskaźnik na źródło danych
    * @param zero  Parametr który musi być zerem
    * @param zero2 Kolejny parametr który musi być zerem
    * @return zero jak OK inaczej że źle
    */
   @SuppressWarnings({"SameParameterValue"})
   public int iUtlCreateFileDSC(PointerByReference hDSC, int zero, int zero2);

   /**
    * Inicjalizuje hDsc
    *
    * @param hDSC  Inicjalizowany wskaźnik na źródło danych
    * @param zero  Parametr który musi być zerem
    * @param zero2 Kolejny parametr który musi być zerem
    * @return zero jak OK inaczej że źle
    */
   @SuppressWarnings({"SameParameterValue"})
   public int iUtlCreateFileDSC2(PointerByReference hDSC, int zero, int zero2);

   /**
    * @param dscPointer dsc którego status sobie czytamy
    * @param result     wynik działania
    * @param dummy1     coś co musi tam być, ale się nie wykorzystuje
    * @param dummy2     patrz dummy1
    * @return nie wiem co zwraca --- error kod sprawdzenia błędu?? Dokumentacja po stronie C jest niepełna w tym zkresie
    */
   public short SadGetStatus(DscPointer dscPointer, NativeLongByReference result, ShortByReference dummy1, ShortByReference dummy2);

   /**
    * Otwiera źródło danych
    *
    * @param dsc        dsc dla ktorego otwieramy dataSource
    * @param sourceName nazwa źródła (moze być kwalifikowana nazwa pliku (bez spacji w środku??)), albo nazwa detektora.
    * @param type       typ
    * @param acces      tryb dostępu
    * @param verify     to nie wiem.
    * @param shellId    dla nas ""
    * @return kod błędu
    */
   public short SadOpenDataSource(DscPointer dsc, String sourceName, short type, short acces, short verify, String shellId);

   /**
    * Zwraca wartość parametru
    *
    * @param hDSC     źródło
    * @param ulParam  kod parametru
    * @param usRecord rekord
    * @param usEntry  entry
    * @param result   tutaj zapiszemy wynik
    * @param usExpect rozmiar <code>result</code> w bajtach
    * @return kod błedu.
    */
   public short SadGetParam(DscPointer hDSC, NativeLong ulParam, short usRecord, short usEntry, byte[] result, short usExpect);

   /**
     * Zwraca wartość parametru
     *
     * @param hDSC     źródło
     * @param ulParam  kod parametru
     * @param usRecord rekord
     * @param usEntry  entry
     * @param result   tutaj zapiszemy wynik
     * @param usExpect rozmiar <code>result</code> w bajtach
     * @return kod błedu.
     */
    public short SadGetParam(DscPointer hDSC, NativeLong ulParam, short usRecord, short usEntry, NativeLongByReference result, short usExpect);

    public short SadGetParam(DscPointer hDSC, NativeLong ulParam, short usRecord, short usEntry, DoubleByReference result, short usExpect);

   /**
    * Ustawia parametr CAMA
    *
    * @param hDSC     źródło
    * @param ulParam  kod parametru
    * @param usRecord rekord
    * @param usEntry  entru
    * @param result   dane do zapisania
    * @param usExpect rozmiar danych w bajtach
    * @return kod błędu
    */
   public short SadPutParam(DscPointer hDSC, NativeLong ulParam, short usRecord, short usEntry, byte[] result, short usExpect);

   public short SadPutParam(DscPointer hDSC, NativeLong ulParam, short usRecord, short usEntry, NativeLongByReference result, short usExpect);

   public short SadPutParam(DscPointer hDSC, NativeLong ulParam, short usRecord, short usEntry, DoubleByReference result, short usExpect);

   /**
    * Zamyka żródło danych otwarte przes DSC
    *
    * @param dsc dsc
    * @return kod błędu
    */
   public short SadCloseDataSource(DscPointer dsc);

   /**
    * Dealokuje ten kawałek biblioteki :)
    *
    * @param dsc dsc
    * @return kod błędu
    */
   public short SadDeleteDSC(DscPointer dsc);

   /**
    * @param sdc
    * @param structType
    * @param record
    * @param entry
    * @param ptr
    * @param structSize
    * @return
    */
   public short SadPutStruct(DscPointer sdc, short structType, short record, short entry, Pointer ptr, short structSize);

     /**
    * @param sdc
    * @param structType
    * @param record
    * @param entry
    * @param ptr
    * @param structSize
    * @return
    */
   public short SadPutStruct(DscPointer sdc, short structType, short record, short entry, Structure str, short structSize);

   /**
    * Powoduje wyplucie danych do pliku/urządzenia
    *
    * @param dsc dsc
    * @return kod błędu
    */
   public short SadFlush(DscPointer dsc);

   /**
    * Ustawia stru
    *
    * @param dsc
    * @param usDevice
    * @param usOpCode
    * @return
    */
   public short SadControlDSC(DscPointer dsc, short usDevice, short usOpCode);


   /**
    * Pobiera spektralne dane
    *
    * @param dsc       żródło danych
    * @param start     pierwszy kanał
    * @param count     ilość kanałów do zapisania --- max 4000 na raz przy jednym wywołaniu
    * @param useFloats 1 jeśli wybnik ma być zwrocony jako dana zmienniprzecinkowa
    * @param result    wynik działania
    * @return kod błędu
    */
   public short SadGetSpectrum(DscPointer dsc, short start, short count, short useFloats, int[] result);

   /**
    * Pobiera spektralne dane
    *
    * @param dsc   żródło danych
    * @param start pierwszy kanał
    * @param count ilość kanałów do zapisania --- max 4000 na raz przy jednym wywołaniu
    * @param input dane
    * @return kod błędu
    */
   public short SadPutSpectrum(DscPointer dsc, short start, short count, int[] input);

   /**
    * Zwraca stan źródła danych
    *
    * @param dscPointer żródło danych
    * @param opCode     typ danych do zwrócenia
    * @param result     wyniyk
    * @return kod błędu
    */
   public short SadQueryDataSource(DscPointer dscPointer, short opCode, DSQuery result);
}
