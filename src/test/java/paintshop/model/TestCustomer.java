/*
 * Classname : TestCustomer.java
 *
 * Created on: 12 Feb 2018
 *
 * Copyright (c) 2000-2018 Realex Payments, Ltd.
 * Realex Payments, The Observatory, 7-11 Sir John Rogerson's Quay, Dublin 2, Ireland
 *  
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Realex Payments, Ltd. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Realex Payments.
 *
 */

package paintshop.model;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import paintshop.exceptions.InvalidInputException;

/**
 * @author danielaguado
 *
 */
public class TestCustomer {

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Test
	public void testValidCustomerOneColour() {

		String inputString = "1 1 0";

		Customer customer = new Customer(inputString);

		assertEquals(1, customer.getColourPreferences().size());
		assertEquals(new Colour(1, 0), customer.getColourPreferences().get(0));
	}

	@Test
	public void testAtLeastOneColourPerCustomer() {

		String inputString = "0";

		exception.expect(InvalidInputException.class);
		exception.expectMessage("The number of colors must be greater than zero for each customer");

		Customer customer = new Customer(inputString);
	}

	@Test
	public void testNumberOfColoursMustMatch() {

		String inputString = "2 1 0";

		exception.expect(InvalidInputException.class);
		exception.expectMessage("Expected 2 colours in customer preferences but found 1");

		Customer customer = new Customer(inputString);
	}

	@Test
	public void testInvalidColourType() {

		String inputString = "1 1 6";

		exception.expect(InvalidInputException.class);
		exception.expectMessage("Invalid color type, valid values are 0 and 1, received: 6");

		Customer customer = new Customer(inputString);
	}

	@Test
	public void testInvalidColourNumber() {

		String inputString = "1 0 0";

		exception.expect(InvalidInputException.class);
		exception.expectMessage("Colour number must be greater than 0");

		Customer customer = new Customer(inputString);
	}

	@Test
	public void testTwoColoursForOneCustomer() {

		String inputString = "2 2 1 5 0";

		Customer customer = new Customer(inputString);

		assertEquals(2, customer.getColourPreferences().size());
		assertEquals(new Colour(5, 0), customer.getColourPreferences().get(1));
		assertEquals(new Colour(2, 1), customer.getColourPreferences().get(0));
	}

}
