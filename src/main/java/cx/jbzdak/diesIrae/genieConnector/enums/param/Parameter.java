package cx.jbzdak.diesIrae.genieConnector.enums.param;

import cx.jbzdak.diesIrae.genieConnector.enums.paramType.ParameterType;
import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
public interface Parameter<T> {
    @NonNull
    short getByteLenght();

    @NonNull
    ParameterType<T> getType();

    @NonNull
    long getParamId();
}
