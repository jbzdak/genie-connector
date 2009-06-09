package cx.jbzdak.diesIrae.genieConnector.enums.paramType;

import java.nio.ByteBuffer;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
class LongWordParam extends ParameterType<Long>{

    LongWordParam() {
        super("LONG_WORD", 'L', C_INT_LENGHT*4);
    }

    @Override
    public Long readArray(byte[] inputBuffer) {
        ByteBuffer byteBuffer  = ByteBuffer.wrap(inputBuffer);
        return byteBuffer.getLong();
    }

    @Override
    public byte[] writeArray(Long l) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.putLong(l);            
        return byteBuffer.array();
    }
}
