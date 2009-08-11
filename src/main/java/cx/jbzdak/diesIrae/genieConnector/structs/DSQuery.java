package cx.jbzdak.diesIrae.genieConnector.structs;

import com.sun.jna.Union;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
public class DSQuery extends Union{

    public DSResult stDS;

    public long flDirty;

    public long   ulVfyRC;

    public long   ulChannels;

    public long getFlDirty() {
        return flDirty;
    }

    public void setFlDirty(long flDirty) {
        this.flDirty = flDirty;
    }

    public long getUlVfyRC() {
        return ulVfyRC;
    }

    public void setUlVfyRC(long ulVfyRC) {
        this.ulVfyRC = ulVfyRC;
    }

    public long getUlChannels() {
        return ulChannels;
    }

    public void setUlChannels(long ulChannels) {
        this.ulChannels = ulChannels;
    }

    public DSResult getStDS() {
        return stDS;
    }

    public void setStDS(DSResult stDS) {
        this.stDS = stDS;
    }
}
