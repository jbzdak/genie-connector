package cx.jbzdak.diesIrae.genieConnector.enums.param;

import cx.jbzdak.diesIrae.genieConnector.enums.paramType.ParameterType;
import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
class ParamImpl<T> implements Parameter<T> {

    @NonNull
    private final ParameterType<T> type;

    @NonNull
    private final long paramId;

    @NonNull
    private final String name;

    ParamImpl(char type, long paramId, String name) {
        this.type = (ParameterType<T>) ParameterType.getType(type);
        if (this.type == null) {
            throw new NullPointerException();
        }
        this.paramId = paramId;
        this.name = name;
    }

    @NonNull
    public short getByteLenght() {
        return type.getByteLenght(name);
    }

    @NonNull
    public ParameterType<T> getType() {
        return type;
    }

    @NonNull
    public long getParamId() {
        return paramId;
    }
    
    //All params (evil!)

}

