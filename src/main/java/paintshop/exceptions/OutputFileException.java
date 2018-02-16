package paintshop.exceptions;

/**
 * Class that represents an Exception when creating the output file
 * @author danielaguado
 *
 */
public class OutputFileException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String message;

	public OutputFileException(final String message, final String... messageParams) {
		this.message = String.format(message, messageParams);
	}

	@Override
	public String getMessage() {
		return message;
	}
}