package cx.jbzdak.diesIrae.genieConnector.enums;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
public enum DeviceType {
    MCA(0), SMP_CHANGER(4);

    private final short value;

    DeviceType(int value) {
        this.value = (short) value;
    }

    public short getValue() {
        return value;
    }
}
