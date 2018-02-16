package paintshop.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import paintshop.exceptions.InvalidInputException;
import paintshop.exceptions.OutputFileException;

/**
 * @author danielaguado
 *
 */
public class TestPaintShop {

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Test
	public void testValidInput() {

		String filePath = getFilePathFromResourcesFolder("testCorrectInput.txt");

		PaintShop shop = new PaintShop(filePath);

		assertNotNull(shop.getCustomerBatches());
		assertEquals(2, shop.getCustomerBatches().size());

		PaintBatch testCase1 = shop.getCustomerBatches().get(0);
		assertEquals(5, testCase1.getNumColours());
		assertEquals(3, testCase1.getCustomers().size());

		Customer customer1 = testCase1.getCustomers().get(0);
		assertEquals(new Colour(1, 1), customer1.getColourPreferences().get(0));

		Customer customer2 = testCase1.getCustomers().get(1);
		assertEquals(new Colour(1, 0), customer2.getColourPreferences().get(0));
		assertEquals(new Colour(2, 0), customer2.getColourPreferences().get(1));

		Customer customer3 = testCase1.getCustomers().get(2);
		assertEquals(new Colour(5, 0), customer3.getColourPreferences().get(0));

		PaintBatch testCase2 = shop.getCustomerBatches().get(1);
		assertEquals(1, testCase2.getNumColours());
		assertEquals(2, testCase2.getCustomers().size());

		customer1 = testCase2.getCustomers().get(0);
		assertEquals(new Colour(1, 0), customer1.getColourPreferences().get(0));

		customer2 = testCase2.getCustomers().get(1);
		assertEquals(new Colour(1, 1), customer2.getColourPreferences().get(0));
	}

	@Test
	public void testNonExistingFile() {

		exception.expect(InvalidInputException.class);
		exception.expectMessage("Exception reading the input file nonexistingfile.txt (The system cannot find the file specified)");

		PaintShop shop = new PaintShop("nonexistingfile.txt");
	}
	
	@Test
	public void testInvalidInputOneLineFile() {

		String filePath = getFilePathFromResourcesFolder("testInvalidInputOneLine.txt");

		exception.expect(InvalidInputException.class);
		exception.expectMessage("Expected reading an Integer, reached the end of the file");

		PaintShop shop = new PaintShop(filePath);
	}

	@Test
	public void testInvalidInputNoCustomers() {

		String filePath = getFilePathFromResourcesFolder("testInvalidInputTwoLine2NoCustomers.txt");

		exception.expect(InvalidInputException.class);
		exception.expectMessage("Expected reading an Integer, reached the end of the file");

		PaintShop shop = new PaintShop(filePath);
	}

	@Test
	public void testInvalidInputNumberOfCasesDoesntMatch() {

		String filePath = getFilePathFromResourcesFolder("testInvalidInputNumberOfCasesDoesntMatch.txt");

		exception.expect(InvalidInputException.class);
		exception.expectMessage("Expected reading an Integer, reached the end of the file");

		PaintShop shop = new PaintShop(filePath);
	}

	@Test
	public void testInvalidInputNumberOfCustomersDoesntMatch() {

		String filePath = getFilePathFromResourcesFolder("testInvalidInputNumberOfCustomersDoesntMatch.txt");

		exception.expect(InvalidInputException.class);
		exception.expectMessage("Invalid number of customers, expected 3 but found 2");

		PaintShop shop = new PaintShop(filePath);
	}

	@Test
	public void testInvalidInputZeroColours() {

		String filePath = getFilePathFromResourcesFolder("testInvalidInputZeroColours.txt");

		exception.expect(InvalidInputException.class);
		exception.expectMessage("The number of colours must be at least one");

		PaintShop shop = new PaintShop(filePath);
	}

	@Test
	public void testInvalidInputColourOutOfRange() {

		String filePath = getFilePathFromResourcesFolder("testInvalidInputColourOutOfRange.txt");

		exception.expect(InvalidInputException.class);
		exception.expectMessage("Colour numbers must be between 1 and 3 but found 4");

		PaintShop shop = new PaintShop(filePath);
	}

	@Test
	public void testGenerateBatchesCorrectInput() {

		String filePath = getFilePathFromResourcesFolder("testCorrectInput.txt");

		PaintShop shop = new PaintShop(filePath);
		shop.generateBatches();

		assertEquals(2, shop.getCustomerBatches().size());
		assertEquals("Case #1: 1 0 0 0 0", shop.getBatchSolutionFormatted().get(0));
		assertEquals("Case #2: IMPOSSIBLE", shop.getBatchSolutionFormatted().get(1));
	}

	@Test
	public void testGenerateBatchesToFile() throws Exception {

		String filePath = getFilePathFromResourcesFolder("testCorrectInput.txt");

		PaintShop shop = new PaintShop(filePath, "testOutput.txt");
		shop.generateBatches();
		shop.printOutput();

		List<String> testOutputList = Files.readAllLines(Paths.get("testOutput.txt"));
		assertEquals(2, testOutputList.size());
		assertEquals("Case #1: 1 0 0 0 0", testOutputList.get(0));
		assertEquals("Case #2: IMPOSSIBLE", testOutputList.get(1));

		//Delete the file after the test
		Files.delete(Paths.get("testOutput.txt"));
	}

	@Test
	public void testInvalidOutputFile() throws Exception {

		String filePath = getFilePathFromResourcesFolder("testCorrectInput.txt");

		PaintShop shop = new PaintShop(filePath, "e:/testOutput.txt");
		shop.generateBatches();

		exception.expect(OutputFileException.class);
		exception.expectMessage("Error writing the output file e:/testOutput.txt, the exception was: ");

		shop.printOutput();
	}

	/**
	 * Utility method to read the absolute path in the resources folder by it's file name
	 * @param fileName
	 * @return
	 */
	private String getFilePathFromResourcesFolder(final String fileName) {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
		String filePath = file.getAbsolutePath();
		return filePath;
	}

}
