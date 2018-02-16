package paintshop.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import paintshop.exceptions.InvalidInputException;

/**
 * Class that models a customer in our Paint Shop, it contains the customer's colour preferences
 * @author danielaguado
 *
 */
public class Customer {

	private static final String INCORRECT_NUM_CUST_PREFERENCES = "Expected %s colours in customer preferences but found %s";
	private static final String COLOUR_NUMBER_MUST_BE_GREATER_THAN_0 = "Colour number must be greater than 0";
	private static final String NUMBER_OF_COLORS_MUST_BE_GREATER_THAN_ZERO = "The number of colors must be greater than zero for each customer";

	private List<Colour> colourPreferences;

	/**
	 * Constructor that creates a customer 
	 * based on the preferences line from the input file
	 * @param preferences the preferences string in the format 1 1 0
	 */
	public Customer(final String preferences) {
		this.colourPreferences = parseColourPreferences(preferences);
	}

	/**
	 * Parses the preferences String passed as parameter into a list of Colour preferences
	 * @param preferences the preferences String
	 * @return the list of Colours
	 */
	private List<Colour> parseColourPreferences(final String preferences) {

		List<Colour> colourPrefs = new ArrayList<Colour>();

		//Map the line into an array of ints by splitting by the spaces
		int[] customerLineInt = Arrays.stream(preferences.split("\\s")).map(String::trim).mapToInt(Integer::parseInt).toArray();
		int numPreferences = customerLineInt[0];

		if (numPreferences == 0) {
			throw new InvalidInputException(NUMBER_OF_COLORS_MUST_BE_GREATER_THAN_ZERO);
		}
		
		for (int i = 1; i < customerLineInt.length; i += 2) {
			int colour = customerLineInt[i];
			if (colour < 1) {
				throw new InvalidInputException(COLOUR_NUMBER_MUST_BE_GREATER_THAN_0);
			}
			int type = customerLineInt[i + 1];

			colourPrefs.add(new Colour(colour, type));
		}

		if (colourPrefs.size() != numPreferences) {
			throw new InvalidInputException(INCORRECT_NUM_CUST_PREFERENCES, String.valueOf(numPreferences), String.valueOf(colourPrefs.size()));
		}

		return colourPrefs;
	}

	public List<Colour> getColourPreferences() {
		return colourPreferences;
	}

}