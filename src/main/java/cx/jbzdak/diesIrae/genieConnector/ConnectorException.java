package cx.jbzdak.diesIrae.genieConnector;

class ConnectorException extends Exception{
    int code;

    ConnectorException(short code) {
        this.code = code;
    }

    ConnectorException(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}