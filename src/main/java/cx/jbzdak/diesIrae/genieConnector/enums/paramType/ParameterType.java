package cx.jbzdak.diesIrae.genieConnector.enums.paramType;

import edu.umd.cs.findbugs.annotations.NonNull;

import java.math.BigInteger;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak jbzdak@gmail.com
 */
public abstract class ParameterType<T> {

    public static final
        ParameterType<Byte> BYTE_PARAM = new ByteParam();

    public static final
        ParameterType<Integer> SHORT_PARAM = new WordParam();

    public static final
        ParameterType<Long> WORD = new LongWordParam();

    public static final
        ParameterType<BigInteger> QUAD_WORD = new QuadWordParam();

    public static final
        ParameterType<Float> REAL = new RealParam();

    public static final
        ParameterType<Double> DOUBLE = new DoubleParam();

    public static final
        ParameterType<Object> G_PARAM = new G_Param();

    public static final
        ParameterType<String> CHARACTER = new CharacterParam();

    public static final
        ParameterType<Date> DATETIME = new DatetimeParam();

    public static final
        ParameterType<?> UNKNOWN_TYPE = new ParameterType<Object>("UNKNOWN", '\0', (short)-1){
        @Override
        public Object readArray(byte[] p) {
           throw new UnsupportedOperationException();
        }

        @Override
        public byte[] writeArray(Object o) {
            throw new UnsupportedOperationException();
        }
    };

    private static final List<ParameterType<?>> PARAMS = Arrays.asList(BYTE_PARAM, SHORT_PARAM, WORD, QUAD_WORD, REAL,
                        DOUBLE, G_PARAM, CHARACTER, DATETIME);

    private static final Map<Character, ParameterType<?>> PARAM_TYPE_MAPPER;

    static{
        Map<Character, ParameterType<?>> MAPPER = new HashMap<Character, ParameterType<?>>();
        for(ParameterType t : PARAMS){
            MAPPER.put(t.identifierChar, t);
        }
        PARAM_TYPE_MAPPER = Collections.unmodifiableMap(MAPPER);
    }

    @NonNull
    public static ParameterType<?> getType(char type){
        ParameterType result = PARAM_TYPE_MAPPER.get(type);
        if(result==null){
            return UNKNOWN_TYPE;
        }
        return result;
    }

    /**
     * Długość typu int w kompilatorze dla któryego kompilujemy bibliotekę. . .
     * Albo też ta oczekiwana przez GENIE ;)
     */
    static final short C_INT_LENGHT = 2;

    /**
     * Długość typu REAL - czyli float ;) dla którego kompilujemy bibliotekę
     * Albo też długoć której oczekuje GENIE ;) dla wielkości REAL
     */
    static final short C_REAL_LENGHT = 4;
    
    protected ParameterType(String name, char identifierChar, int byteLenght) {
        this.name = name;
        this.identifierChar = identifierChar;
        if(byteLenght > Short.MAX_VALUE)
            throw new IllegalStateException();
        this.byteLenght = (short) byteLenght;
    }

    public final String name;

    public final char identifierChar;

    private final short byteLenght;

    /**
     * Dla parametrów typu string trzeba podać nazwę... Domyślnie zwracamy #byteLenght
     * @param paramName nazwa parametru dla której obliczamy długość
     * @return długośc w bajtach parametru
     */
    public short getByteLenght(String paramName) {
        return byteLenght;
    }

    public abstract T readArray(byte[] p);

    public abstract byte[] writeArray(T t);
}


