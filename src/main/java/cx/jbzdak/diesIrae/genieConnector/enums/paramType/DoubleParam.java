package cx.jbzdak.diesIrae.genieConnector.enums.paramType;

import java.nio.ByteBuffer;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
class DoubleParam extends  ParameterType<Double>{
    DoubleParam() {
        super("DOUBLE", 'D', C_REAL_LENGHT*4);
    }

    @Override
    public Double readArray(byte[] p) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(p);
        return byteBuffer.getDouble();
    }

    @Override
    public byte[] writeArray(Double aDouble) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.putDouble(aDouble);
        return byteBuffer.array();      
    }
}
