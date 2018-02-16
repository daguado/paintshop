package paintshop.model;

import paintshop.exceptions.InvalidInputException;

/**
 * Enum to represent the two finishes a Colour can be prepared
 * Glossy = 0 and Matte = 1
 * @author danielaguado
 *
 */
public enum ColourType {

	GLOSSY(0),
	MATTE(1);

	public static final String INVALID_COLOR_TYPE = "Invalid color type, valid values are 0 and 1, received: %s";

	private int type;

	ColourType(final int type) {
		this.type = type;
	}

	public int type() {
		return type;
	}

	public static ColourType getColourType(int type) {
		switch (type) {
		case 0:
			return GLOSSY;
		case 1:
			return MATTE;
		default:
			throw new InvalidInputException(INVALID_COLOR_TYPE, String.valueOf(type));
		}
	}

}
