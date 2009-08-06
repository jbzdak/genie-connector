package cx.jbzdak.diesIrae.genieConnector.enums.param;

import cx.jbzdak.diesIrae.genieConnector.enums.paramType.ParameterType;
import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
public class ParamAlias<T> implements Parameter<T>{

    public static final ParamAlias<String> SAMPLE_IDENTIFIER = new ParamAlias<String>(Parameters.T_SIDENT);

    public static final ParamAlias<Float> SAMPLE_QUANTITY = new ParamAlias<Float>(Parameters.F_SQUANT);

    public static final ParamAlias<Long> CHANNEL_NUMBER = new ParamAlias<Long>(Parameters.L_CHANNELS);

    private final ParamImpl internal;

    ParamAlias(ParamImpl internal) {
        this.internal = internal;
    }

    @NonNull
    public short getByteLenght() {
        return internal.getByteLenght();
    }

    @NonNull
    public ParameterType<T> getType() {
        return internal.getType();
    }

    @NonNull
    public long getParamId() {
        return internal.getParamId();
    }
}
