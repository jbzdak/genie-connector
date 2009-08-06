package cx.jbzdak.diesIrae.genieConnector.enums;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
public enum OpCode {
    START_ACQUISITION(1), ABORT_ACQUISITION(2), BUMP_CHARGER(4), CLEAR_DATA(5);

    private final short value;

    OpCode(int value) {
        this.value = (short) value;
    }

    public short getValue() {
        return value;
        
    }
}
