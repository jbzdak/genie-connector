package cx.jbzdak.diesIrae.genieConnector.enums.paramType;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
class DatetimeParam extends ParameterType<Date>{
    DatetimeParam() {
        super("DATE", 'X', -1);
    }@Override
     public Date readArray(byte[] p) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte[] writeArray(Date date) {
        throw new UnsupportedOperationException();
    }
}
