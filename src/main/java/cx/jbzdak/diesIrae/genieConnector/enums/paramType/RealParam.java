package cx.jbzdak.diesIrae.genieConnector.enums.paramType;

import java.nio.ByteBuffer;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
class RealParam extends  ParameterType<Float>{
    RealParam() {
        super("FLOAT", 'F', C_REAL_LENGHT*2);
    }

    @Override
    public byte[] writeArray(Float aFloat) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.putFloat(aFloat);
        return byteBuffer.array();
    }

    @Override
    public Float readArray(byte[] p) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(p);
        return byteBuffer.getFloat();
    }
}
