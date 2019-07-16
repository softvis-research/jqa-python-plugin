package org.jqassistant.contrib.plugin.python.impl.scanner;

/**
 * This exception is thrown if something goes wrong while solving parsed types.
 * It is caught in
 * {@link org.jqassistant.contrib.plugin.python.impl.scanner.PythonSourceFileScannerPlugin}.
 * 
 * @author Kevin M. Shrestha
 *
 */
public class PythonSourceException extends RuntimeException {
    private static final long serialVersionUID = 1991891452730217845L;

    public PythonSourceException(String string) {
        super(string);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
