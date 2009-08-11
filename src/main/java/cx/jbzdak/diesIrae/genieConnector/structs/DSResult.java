package cx.jbzdak.diesIrae.genieConnector.structs;

import com.sun.jna.Structure;

public class DSResult extends Structure{
    
     public short status;
     public short busyGroup;

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public short getBusyGroup() {
        return busyGroup;
    }

    public void setBusyGroup(short busyGroup) {
        this.busyGroup = busyGroup;
    }
}