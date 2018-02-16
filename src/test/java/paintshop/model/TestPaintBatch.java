package paintshop.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author danielaguado
 *
 */
public class TestPaintBatch {

	@Test
	public void testCase1() {

		PaintBatch paintBatch = new PaintBatch(5);
		paintBatch.addCustomer("1 1 1");
		paintBatch.addCustomer("2 1 0 2 0");
		paintBatch.addCustomer("1 5 0");

		String result = paintBatch.mixColours();

		assertEquals("1 0 0 0 0", result);
	}

	@Test
	public void testCase2() {

		PaintBatch paintBatch = new PaintBatch(5);
		paintBatch.addCustomer("1 1 1");
		paintBatch.addCustomer("2 1 0 2 0");
		paintBatch.addCustomer("2 5 1 4 0");

		String result = paintBatch.mixColours();

		assertEquals("1 0 0 0 0", result);
	}

	@Test
	public void testCase3() {

		PaintBatch paintBatch = new PaintBatch(5);
		paintBatch.addCustomer("1 1 1");
		paintBatch.addCustomer("2 1 0 2 0");
		paintBatch.addCustomer("2 5 1 4 1");

		String result = paintBatch.mixColours();

		assertEquals("1 0 0 0 1", result);
	}

	@Test
	public void testCaseImpossibleSolution() {

		PaintBatch paintBatch = new PaintBatch(2);
		paintBatch.addCustomer("1 1 1");
		paintBatch.addCustomer("1 1 0");

		String result = paintBatch.mixColours();

		assertEquals("IMPOSSIBLE", result);
	}

	@Test
	public void testCase4() {

		PaintBatch paintBatch = new PaintBatch(2);
		paintBatch.addCustomer("1 1 1");
		paintBatch.addCustomer("2 1 1 2 1");

		String result = paintBatch.mixColours();

		assertEquals("1 0", result);
	}

	@Test
	public void testCase5() {

		PaintBatch paintBatch = new PaintBatch(3);
		paintBatch.addCustomer("1 1 1");
		paintBatch.addCustomer("2 1 1 2 1");
		paintBatch.addCustomer("2 3 1 2 0");

		String result = paintBatch.mixColours();

		assertEquals("1 0 0", result);
	}

	@Test
	public void testCase6() {

		PaintBatch paintBatch = new PaintBatch(3);
		paintBatch.addCustomer("2 1 0 3 0");
		paintBatch.addCustomer("2 1 1 2 1");
		paintBatch.addCustomer("2 3 1 2 0");

		String result = paintBatch.mixColours();

		assertEquals("1 0 0", result);
	}

	@Test
	public void testCase7() {

		PaintBatch paintBatch = new PaintBatch(3);
		paintBatch.addCustomer("2 3 0 1 0");
		paintBatch.addCustomer("2 2 1 1 1");
		paintBatch.addCustomer("2 3 1 2 0");

		String result = paintBatch.mixColours();

		assertEquals("1 0 0", result);
	}

	@Test
	public void testCase8() {

		PaintBatch paintBatch = new PaintBatch(5);
		paintBatch.addCustomer("1 1 1");
		paintBatch.addCustomer("2 1 0 2 0");
		paintBatch.addCustomer("1 5 0");

		String result = paintBatch.mixColours();

		assertEquals("1 0 0 0 0", result);
	}

}
