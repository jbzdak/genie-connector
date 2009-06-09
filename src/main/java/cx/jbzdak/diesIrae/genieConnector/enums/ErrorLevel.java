package cx.jbzdak.diesIrae.genieConnector.enums;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
public enum ErrorLevel {
    HARDWARE_PROTOCOL_ERROR(1, "Hardware Protocol driver error"),
    VDM_DRIVER_ERROR(2, "VDM Driver error"),
    VDM_ERROR(3, "VDM error"),
    IPC_ERROR(4, "IPC error"),
    CLIENT_ERROR(5, "Client (SAD access routine) error"),
    APP_ERROR(6, "Application error");

    public static ErrorLevel getLevel(Byte flag){
        for(ErrorLevel clazz : values()){
            if(flag.equals(clazz.errorLevel)){
                return clazz;
            }
        }
        //throw new InvalidParameterException();
        return null;
    }


    private final byte errorLevel;
    private final String className;

    ErrorLevel(int errorLevel, String className) {
        this.errorLevel = (byte) errorLevel;
        this.className = className;
    }

    public byte getErrorLevel() {
        return errorLevel;
    }

    public String getClassName() {
        return className;
    }
}
