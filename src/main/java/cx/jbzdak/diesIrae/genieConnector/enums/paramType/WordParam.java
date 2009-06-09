package cx.jbzdak.diesIrae.genieConnector.enums.paramType;

import java.nio.ByteBuffer;

class WordParam extends ParameterType<Integer>{

    WordParam() {
        super("WORD", 'W', C_INT_LENGHT*2);
    }

    @Override
    public Integer readArray(byte[] inputBuffer) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(inputBuffer);
        return byteBuffer.getInt();

    }

    @Override
    public byte[] writeArray(Integer integer) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.putInt(integer);
        return byteBuffer.array();
    }
}