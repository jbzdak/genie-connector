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

import java.nio.IntBuffer;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
public class SpectrometricResult {

    private final short startChannel;
    private final short endChannel;
    private final IntBuffer contents;

    public static SpectrometricResult getEmptyResult(short startChannel, short endChannel){
       IntBuffer intBuffer = IntBuffer.allocate(endChannel);
       return new SpectrometricResult(startChannel, endChannel, intBuffer);
    }

    public SpectrometricResult(short startChannel, short endChannel, IntBuffer contents) {
        this.startChannel = startChannel;
        this.endChannel = endChannel;
        this.contents = contents;
    }

    public short getStartChannel() {
        return startChannel;
    }

    public short getEndChannel() {
        return endChannel;
    }

    public short getSize(){
      return (short)(endChannel - startChannel);  
    }

    IntBuffer getContents() {
        return contents.asReadOnlyBuffer();
    }

   public RegionOfInterest createRoi(int roiStartChannel, int roiEndChannel){
      return new RegionOfInterest((short)roiStartChannel, (short)roiEndChannel, this);      
   }

    public int getCountForChannel(int channelNum){
        return contents.get(channelNum - startChannel);    
    }

    @Override
    public String toString() {
        return "SpectrometricResult{" +
                "startChannel=" + startChannel +
                ", endChannel=" + endChannel +
                ", contents=" + contents +
                '}';
    }
}
