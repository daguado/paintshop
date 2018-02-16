package paintshop.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import paintshop.exceptions.InvalidInputException;
import paintshop.exceptions.OutputFileException;

/**
 * Class that models out Paint Shop containing a list of customer batch requests 
 * and methods to generate the batches the satisfy the customers (if there is a valid solution)
 * and to output the results to a file.
 * @author danielaguado
 *
 */
public class PaintShop {

	private static final String EXCEPTION_OUTPUT_FILE = "Error writing the output file %s, the exception was: ";
	private static final String REACHED_END_OF_FILE = "Expected reading an Integer, reached the end of the file";
	private static final String EXPECTED_INTEGER = "Expected reading an Integer, found: %s";
	private static final String INVALID_COLOUR_NUMBER = "Colour numbers must be between 1 and %s but found %s";
	private static final String INCORRECT_NUMBER_OF_CUSTOMERS = "Expected %s customers but found %s";
	private static final String AT_LEAST_ONE_CUSTOMER_IS_REQUIRED = "At least one customer is required in each test case";
	private static final String INVALID_NUMBER_OF_CUSTOMERS = "Invalid number of customers, expected %s but found %s";
	private static final String NUMBER_OF_COLOURS_MUST_BE_AT_LEAST_ONE = "The number of colours must be at least one";
	private static final String EXCEPTION_READING_INPUT_FILE = "Exception reading the input file %s";
	private static final String OUTPUT_FILENAME = "paintShopOutput-%s.txt";
	private static final String CASE_FORMAT_STRING = "Case #%s: %s";

	private List<PaintBatch> customerBatches;
	private List<String> batchSolutionFormatted;
	private String outputFileName;

	/**
	 * Constructor that receives the input file as a parameter and does the parsing
	 * @param inputFile The input file path
	 */
	public PaintShop(final String inputFile) {
		parseInputFile(inputFile);
		this.outputFileName = generateFileNameFromTimestamp();
	}

	/**
	 * Constructor that receives both the input file and the output file
	 * and parses the input file
	 * @param inputFile The input file path
	 * @param outputFile The output file path
	 */
	public PaintShop(final String inputFile, final String outputFile) {
		parseInputFile(inputFile);
		this.outputFileName = outputFile;
	}

	/**
	 * 	Generate paint batches for each customer request
	 *  and formats the result to the "Case #N: solution" format 
	 */
	public void generateBatches() {
		int caseNumber = 1;
		for (PaintBatch batch : customerBatches) {
			String formattedBatchResult = String.format(CASE_FORMAT_STRING, caseNumber, batch.mixColours());
			addBatchSolutionFormatted(formattedBatchResult);
			caseNumber++;
		}
	}

	/**
	 * Outputs the formatted solutions into the selected file name or a generated one
	 */
	public void printOutput() {

		try (PrintWriter writer = new PrintWriter(new FileWriter(outputFileName))) {
			for (String batch : batchSolutionFormatted) {
				System.out.println(batch);
				writer.println(batch);
			}
		} catch (Exception e) {
			throw new OutputFileException(EXCEPTION_OUTPUT_FILE, outputFileName, e.getMessage());
		}
	}

	/**
	 * Generates a file name based on the default file name 
	 * and current timestamp
	 * @return the file name
	 */
	private String generateFileNameFromTimestamp() {
		Instant instant = Instant.now();
		long timeStampMillis = instant.toEpochMilli();

		String outputFile = String.format(OUTPUT_FILENAME, timeStampMillis);
		return outputFile;
	}

	/**
	 * Reads the input file and maps it into the paint shop model objects
	 * @param inputFile The path to the input file
	 */
	private void parseInputFile(final String inputFile) {

		try (Scanner scanner = new Scanner(new File(inputFile))) {

			int numTestCases = readInt(scanner);
			parseTestCases(numTestCases, scanner);

		} catch (IOException e) {
			throw new InvalidInputException(EXCEPTION_READING_INPUT_FILE, e.getMessage());
		}
	}

	/**
	 * Parses the subsequent test cases section in the input file 
	 * into the corresponding model objects
	 * @param numCustomerBatchRequests The number of customer batch requests included in the file
	 * @param scanner The scanner for the input file
	 */
	private void parseTestCases(final int numCustomerBatchRequests, final Scanner scanner) {

		for (int i = 0; i < numCustomerBatchRequests; i++) {
			int numColors = readInt(scanner);
			validateNumColours(numColors);
			int numCustomers = readInt(scanner);

			PaintBatch testCase = new PaintBatch(numColors);
			parseCustomersForTestCase(numColors, numCustomers, testCase, scanner);

			addPaintBatch(testCase);
		}
	}

	/**
	 * Validates that the number of colours must is greater than zero 
	 * @param numColors The number of colours to validate
	 */
	private void validateNumColours(final int numColors) {
		if (numColors < 1) {
			throw new InvalidInputException(NUMBER_OF_COLOURS_MUST_BE_AT_LEAST_ONE);
		}
	}

	/**
	 * Parses the subsequent customers list in the input file and validates it
	 * @param numColours The number of colours in the batch
	 * @param numCustomers The number of customers in the batch
	 * @param batch The current batch that will contain the customers
	 * @param scanner The scanner for the input file
	 */
	private void parseCustomersForTestCase(final int numColours, final int numCustomers, final PaintBatch batch, final Scanner scanner) {
		for (int i = 0; i < numCustomers; i++) {
			if (scanner.hasNextLine()) {
				batch.addCustomer(scanner.nextLine());
			} else {
				throw new InvalidInputException(INVALID_NUMBER_OF_CUSTOMERS, String.valueOf(numCustomers),
						String.valueOf(i));
			}
		}
		validateCustomersForBatchRequest(numCustomers, batch);
		validateColoursAreInCorrectRange(numColours, batch);
	}

	/**
	 * Validates the customer list for the current batch request
	 * @param numCustomers The number of customers in the batch request
	 * @param batchRequest The batch request to validate
	 */
	private void validateCustomersForBatchRequest(final int numCustomers, final PaintBatch batchRequest) {
		if (batchRequest.getCustomers().isEmpty()) {
			throw new InvalidInputException(AT_LEAST_ONE_CUSTOMER_IS_REQUIRED);
		} else {
			if (numCustomers != batchRequest.getCustomers().size()) {
				throw new InvalidInputException(INCORRECT_NUMBER_OF_CUSTOMERS, String.valueOf(numCustomers),
						String.valueOf(batchRequest.getCustomers().size()));
			}
		}
	}

	/**
	 * Validates that every colour for a batch request is in the correct range 
	 * according to the provided number of colours
	 * @param numColours The number of colours in the batch request
	 * @param batchRequest The batch request that needs to be validated
	 */
	private void validateColoursAreInCorrectRange(final int numColours, final PaintBatch batchRequest) {
		Optional<Colour> invalidColour = batchRequest.getCustomers().stream().flatMap(customer -> customer.getColourPreferences().stream())
				.filter(colour -> colour.getNumber() > numColours).findAny();
		if (invalidColour.isPresent()) {
			throw new InvalidInputException(INVALID_COLOUR_NUMBER, String.valueOf(numColours),
					String.valueOf(invalidColour.get().getNumber()));
		}
	}

	/**
	 * Reads a single int from the input file, 
	 * throwing an InvalidInputException if an unexpected error occurs
	 * @param scanner The scanner for the input file
	 * @return The integer from the file
	 */
	private int readInt(final Scanner scanner) {

		if (scanner.hasNextLine()) {
			String nextLine = scanner.nextLine();
			if (StringUtils.isNumeric(nextLine)) {
				return Integer.parseInt(nextLine);
			} else {
				throw new InvalidInputException(EXPECTED_INTEGER, nextLine);
			}
		} else {
			throw new InvalidInputException(REACHED_END_OF_FILE);
		}
	}

	/**
	 * Adds a batch to the list
	 * @param batch the batch
	 */
	public void addPaintBatch(PaintBatch batch) {
		if (customerBatches == null) {
			customerBatches = new ArrayList<>();
		}
		customerBatches.add(batch);
	}

	/**
	 * Adds a formatted solution to the list
	 * @param solution the formatted solution
	 */
	public void addBatchSolutionFormatted(String solution) {
		if (batchSolutionFormatted == null) {
			batchSolutionFormatted = new ArrayList<>();
		}
		batchSolutionFormatted.add(solution);
	}

	public List<PaintBatch> getCustomerBatches() {
		return customerBatches;
	}

	public List<String> getBatchSolutionFormatted() {
		return batchSolutionFormatted;
	}

}