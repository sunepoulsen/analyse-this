package dk.sunepoulsen.analysethis.vcs.api;

public class VCSException extends Exception {
    /**
     * Constructs an exception with a single message.
     *
     * @param message The message.
     */
    public VCSException( String message ) {
        super( message );
    }

    /**
     * Constructs an exception from a formated message and a number of arguments.
     * <p>
     *     This constructor is simply a shortcut for
     *     <code>CliException( String.format( format, args ) )</code>
     * </p>
     *
     * @param format A format string.
     * @param args   Arguments to the formatted string.
     */
    public VCSException( String format, Object... args ) {
        this( String.format( format, args ) );
    }

    /**
     * Constructs an exception with a message and a caused throwable.
     *
     * @param message Message.
     * @param cause   Cause instance.
     */
    public VCSException( String message, Throwable cause ) {
        super( message, cause );
    }
}
