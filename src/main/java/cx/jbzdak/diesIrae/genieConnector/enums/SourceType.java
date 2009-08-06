package cx.jbzdak.diesIrae.genieConnector.enums;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
public enum SourceType {
    FILE(1), DETECTOR(0x301);

    private SourceType(int value) {
        this.value = (short) value;
    }

    public final short value;

}
