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

import cx.jbzdak.diesIrae.genieConnector.enums.ErrorClass;
import cx.jbzdak.diesIrae.genieConnector.enums.ErrorLevel;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
public class ErrorDescriptionTest {

     ErrorDescription desc;

    @Before
    public void before(){
         desc = new ErrorDescription(0x278e2aL);
    }

    @Test
    public void testSpecificCode(){
        Assert.assertEquals(desc.getSpecificErrorCode().intValue(), 0x8e2a);
    }

    @Test
    public void testErrorLevel(){
        Assert.assertEquals(desc.getErrorLevel(), ErrorLevel.VDM_DRIVER_ERROR);
    }

    @Test
    public void testErrorClass(){
         Assert.assertEquals(desc.getErrorClass(), ErrorClass.CAM_CLASS);
    }

   public static void main(String[] args){
      System.out.println(new ErrorDescription(0x300013l));
   }

}
