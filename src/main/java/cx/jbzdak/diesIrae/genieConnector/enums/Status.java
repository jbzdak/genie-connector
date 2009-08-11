package cx.jbzdak.diesIrae.genieConnector.enums;

import java.util.EnumSet;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
@SuppressWarnings({"MagicNumber"})
public enum Status {
        BUSY(4), IDLE(2), DONE(16), UPDATE(32);

    private final  int value;

    Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static EnumSet<Status> decode(int bitField){
        EnumSet<Status> result= EnumSet.noneOf(Status.class);
        for(Status s : Status.values()){
            if((bitField & s.getValue()) != 0){
                result.add(s);
            }
        }
        return  result;

    }
}
