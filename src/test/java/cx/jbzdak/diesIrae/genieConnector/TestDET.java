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

import cx.jbzdak.diesIrae.genieConnector.enums.OpenMode;
import cx.jbzdak.diesIrae.genieConnector.enums.SourceType;
import org.junit.Before;
import org.junit.Test;

import java.util.EnumSet;

/**
 * Created by IntelliJ IDEA.
 * User: jbzdak
 * Date: 2009-07-15
 * Time: 13:55:48
 * To change this template use File | Settings | File Templates.
 */
public class TestDET {

    GenieConnector connector;

    @Before
    public void before(){
        connector = new GenieConnector();
        connector.openSource("DET_1", EnumSet.of(OpenMode.READ_WRITE, OpenMode.SYSTEM_WRITE, OpenMode.EXCLUSIVE), SourceType.DETECTOR);
    }

    @Test
    public void test1(){
        System.out.println(""+connector.getSpectrometricData(1,100));
    }
}
