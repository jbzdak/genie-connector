package cx.jbzdak.diesIrae.genieConnector.structs;

public class DSResult {
    
     private short status;
     private short busyGroup;

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