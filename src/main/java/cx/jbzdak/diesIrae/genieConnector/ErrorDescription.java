package cx.jbzdak.diesIrae.genieConnector;

import cx.jbzdak.diesIrae.genieConnector.enums.ErrorClass;
import cx.jbzdak.diesIrae.genieConnector.enums.ErrorLevel;
import org.apache.commons.collections.map.DefaultedMap;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
@SuppressWarnings({"MagicNumber"})
public class ErrorDescription {

    private static final Map<Integer, String> detailedMessages =
            Collections.synchronizedMap(DefaultedMap.decorate(new HashMap(),"Nie określono wiadomości szczegółowej"));

    static{
        detailedMessages.put(0xfffd, "Ogólny błąd połączenia z VDM");
        detailedMessages.put(0x8e2a, "Plik otwarty przez inny program i nie można się nim podzielić ;)");
    }

    private final Long originalErrorCode;

    private final ErrorClass errorClass;

    private final ErrorLevel errorLevel;

    private final Integer specificErrorCode;

    public ErrorDescription(Long originalErrorCode) {
        this.originalErrorCode = originalErrorCode;
        long specificCodeMask = (long)( Math.pow(2,16)) - 1L;
        //System.out.println(" "  + Long.toBinaryString(specificCodeMask));
        specificErrorCode = (int)(originalErrorCode & specificCodeMask);
        //.out.println(" "  + Long.toBinaryString(originalErrorCode));
        originalErrorCode >>= 16;
        //System.out.println(" "  + Long.toBinaryString(originalErrorCode));
        specificCodeMask = 7;
        errorClass = ErrorClass.getClass((byte)(originalErrorCode & specificCodeMask));
        //System.out.println(" "  + Long.toBinaryString(originalErrorCode));
        originalErrorCode >>= 4;
        specificCodeMask = 255;
        errorLevel = ErrorLevel.getLevel((byte)(originalErrorCode & specificCodeMask));
    }

    public Long getOriginalErrorCode() {
        return originalErrorCode;
    }

    public ErrorClass getErrorClass() {
        return errorClass;
    }

    public ErrorLevel getErrorLevel() {
        return errorLevel;
    }

    public Integer getSpecificErrorCode() {
        return specificErrorCode;
    }

    public String getDetailedMessage(){
        return detailedMessages.get(getSpecificErrorCode());
    }

    @Override
    public String toString() {
        return "ErrorDescription{" +
                "originalErrorCode=" + originalErrorCode +
                ", errorClass=" + errorClass +
                ", errorLevel=" + errorLevel +
                ", detailedMessage=" + getDetailedMessage() +
                ", specificErrorCode=" + Integer.toHexString(specificErrorCode) +
                '}';
    }
}
