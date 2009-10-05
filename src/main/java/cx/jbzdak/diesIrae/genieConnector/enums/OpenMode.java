/*
 * GenieConnector java library to connect with
 * Canberra Genie 2000 library
 * Copyright (C) 2009 Jacek Bzdak jbzdak@gmail.com
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

import java.util.EnumSet;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
public enum OpenMode {
    READ_ONLY(0),
    READ_WRITE(1),
    /**
     * Czyli że nikt inny nie strzela do tego źródła
     */

    EXCLUSIVE(2),
    /**
     * Aplikacja powinny używać AppWrite, a system SystemWrite
     */
    SYSTEM_WRITE(4),
     /**
     * Aplikacja powinny używać AppWrite, a system SystemWrite
     */
    APP_WRITE(8),
    /**
     * Omijanie VDM
     */
    DIRECT(128),
    CONTINUE(16),
    /**
     * Wymuszenie uzyskania kontroli jak urządzenie jest zajęte
     */
    TAKE_CONTROL(32),
    /**
     * Stać sie właściccielem??
     */
    TAKE_OVER(64),
    /**
     * Umożliwia współdzielenie źródła bez sprawdzania parametru shellId
     */
    SHARED_NO_CHECK(256),
    /**
     * Umożliwia współdzielenie źródła ze sprawdzaniem parametru shellId
     */
    SHARED_CHECK(512);

    private OpenMode(int value) {
        this.value = (short)value;
    }

    private final short value;

    public static short getLogicalSum(EnumSet<OpenMode> modes){
        short result = 0;
        for(OpenMode mode : modes){
            result |=mode.value;
        }
        return result;
    }
}
