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

    public IntBuffer getContents() {
        return contents.asReadOnlyBuffer();
    }

   public RegionOfInterest getRoi(int roiStartChannel, int roiEndChannel){
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
