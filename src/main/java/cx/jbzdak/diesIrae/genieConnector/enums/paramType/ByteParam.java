package cx.jbzdak.diesIrae.genieConnector.enums.paramType;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
class ByteParam extends ParameterType<Byte>{

    ByteParam() {
        super("BYTE", 'B', 1);
    }

    @Override
    public Byte readArray(byte[] inputBuffer) {
        return inputBuffer[0];
    }

    @Override
    public byte[] writeArray(Byte aByte) {
        return new byte[]{aByte};
    }
}
