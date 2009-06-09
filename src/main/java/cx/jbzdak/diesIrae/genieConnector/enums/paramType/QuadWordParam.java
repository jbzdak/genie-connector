package cx.jbzdak.diesIrae.genieConnector.enums.paramType;

import java.math.BigInteger;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
class QuadWordParam extends ParameterType<BigInteger>{

    QuadWordParam() {
        super("QUAD_WORD", 'Q', C_INT_LENGHT*4);
    }

    @Override
    public BigInteger readArray(byte[] p) {
         throw new UnsupportedOperationException();
    }

    @Override
    public byte[] writeArray(BigInteger bigInteger) {
         throw new UnsupportedOperationException();
    }
}
