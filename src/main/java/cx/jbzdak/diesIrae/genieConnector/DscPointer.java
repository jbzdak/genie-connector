package cx.jbzdak.diesIrae.genieConnector;

import com.sun.jna.Pointer;
import com.sun.jna.PointerType;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
public class DscPointer extends PointerType{

    public DscPointer() {
        super();
    }

    public DscPointer(Pointer p) {
        super(p);
    }

}
