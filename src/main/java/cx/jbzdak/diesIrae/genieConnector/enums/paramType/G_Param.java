package cx.jbzdak.diesIrae.genieConnector.enums.paramType;

/**
 * Cokolwiek to jest . . .
 */
class G_Param extends ParameterType<Object>{
    G_Param() {
        super("GReal", 'G', C_REAL_LENGHT*4);
    }

    @Override
    public byte[] writeArray(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object readArray(byte[] p) {
         throw new UnsupportedOperationException();
    }
}
