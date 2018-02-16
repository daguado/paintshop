package paintshop.model;

/**
 * Class that models a Colour and its finish
 * @author danielaguado
 *
 */
public class Colour {

	private int number;
	private ColourType type;

	/**
	 * Constructor that receives the colour number
	 * and the colour finish as Integers
	 * @param number
	 * @param type
	 */
	public Colour(int number, int type) {
		this.number = number;
		this.type = ColourType.getColourType(type);
	}

	public int getNumber() {
		return number;
	}

	public ColourType getType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + number;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Colour))
			return false;
		Colour other = (Colour) obj;
		if (number != other.number)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

}