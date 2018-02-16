package paintshop;

import paintshop.exceptions.InvalidInputException;
import paintshop.model.PaintShop;

/**
 * Main class for our Paint Shop.
 * A first parameter containing the input file is mandatory
 * The second parameter is optional and it can contain the file name used for the output file
 * Any other paramters will be ignored
 * @author danielaguado
 *
 */
public class PaintShopApp {

	private static final String INPUT_FILE_PATH_MUST_BE_PROVIDED_AS_AN_ARGUMENT = "Input file path must be provided as an argument";

	public static void main(final String[] args) {

		if (args == null || args.length < 1) {
			throw new InvalidInputException(INPUT_FILE_PATH_MUST_BE_PROVIDED_AS_AN_ARGUMENT);
		}

		PaintShop paintShop;

		//Only the input file was provided
		if (args.length == 1) {
			paintShop = new PaintShop(args[0]);
		} else {
			//Both input and output files were provided
			paintShop = new PaintShop(args[0], args[1]);
		}

		paintShop.generateBatches();
		paintShop.printOutput();
	}

}