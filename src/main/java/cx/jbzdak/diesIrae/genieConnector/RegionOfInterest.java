package cx.jbzdak.diesIrae.genieConnector;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
public class RegionOfInterest {

    private final short startChannel;
    private final short endChannel;
    private final SpectrometricResult internal;

    public RegionOfInterest(short startChannel, short endChannel, SpectrometricResult internal) {
        this.startChannel = startChannel;
        this.endChannel = endChannel;
        this.internal = internal;
    }

    public short getStartChannel() {
        return startChannel;
    }

    public short getEndChannel() {
        return endChannel;
    }

    public SpectrometricResult getInternal() {
        return internal;
    }

    public int getCountForChannel(int channelNum) {
        if(channelNum<startChannel || channelNum>endChannel){
            throw new IllegalArgumentException("Pobrano punkty spoza ROI za pośrednictwem ROI");
        }
        return internal.getCountForChannel(channelNum);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("startChannel", startChannel).
                append("endChannel", endChannel).
                append("internal", internal).
                toString();
    }
}
