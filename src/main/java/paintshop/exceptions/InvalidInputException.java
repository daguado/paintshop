package paintshop.exceptions;

/**
 * Class that represents any error when parsing the input file
 * @author danielaguado
 *
 */
public class InvalidInputException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String message;

	public InvalidInputException(final String message, final String... messageParams) {
		this.message = String.format(message, messageParams);
	}

	@Override
	public String getMessage() {
		return message;
	}
}