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
import cx.jbzdak.diesIrae.genieConnector.enums.param.ParamAlias;
import org.junit.*;

import java.io.File;
import java.security.SecureRandom;
import java.util.EnumSet;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
@SuppressWarnings({"MagicNumber"})
public class GenieConnectorTest {
    GenieConnector connector;

    private static Random random = new SecureRandom();
    @BeforeClass
    public static void beforeClass() throws Exception{
        Class.forName("cx.jbzdak.diesIrae.genieConnector.GenieConnector");
    }

    @Before
    public void startup()throws Exception{
        connector = new GenieConnector();
        connector.openFile(new File("C:\\GENIE2K\\CAMFILES\\NBSSTD.CNF"), EnumSet.of(OpenMode.READ_WRITE, OpenMode.SYSTEM_WRITE, OpenMode.EXCLUSIVE));
    }

    @After
    public void after( ) throws Exception{
        connector.close();
    }

    /**
     * Rekonstrukcja Example2.c 1
     */
    @Test
    public void test(){
        Assert.assertEquals(connector.getParam(ParamAlias.SAMPLE_IDENTIFIER, 0,0), "NBS Std.");
    }

    @Test
    public void test2(){
        float randomFloat = random.nextFloat();
        connector.setParam(ParamAlias.SAMPLE_QUANTITY, randomFloat);
        Assert.assertEquals(connector.getParam(ParamAlias.SAMPLE_QUANTITY), randomFloat, 0.0001);
        connector.setParam(ParamAlias.SAMPLE_QUANTITY, 0.0f);
    }



}
