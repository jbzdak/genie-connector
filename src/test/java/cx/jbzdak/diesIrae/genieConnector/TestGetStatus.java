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

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
public class TestGetStatus {

    SimpleConnector connector;

    private static Random random = new SecureRandom();
    @BeforeClass
    public static void beforeClass() throws Exception{
        Class.forName("cx.jbzdak.diesIrae.genieConnector.SimpleConnector");
    }

    @Before
    public void startup()throws Exception{
        connector = new GenieConnector();
    }

    @After
    public void after( ) throws Exception{
        connector.close();
    }

      @Test
    public void testGetStatus() throws Exception{
        Field f = SimpleConnector.class.getDeclaredField("dsc");
        f.setAccessible(true);
        DscPointer pointer =(DscPointer) f.get(connector);
        LibraryWrapper.getDetailedError(pointer);
    }
    
}
