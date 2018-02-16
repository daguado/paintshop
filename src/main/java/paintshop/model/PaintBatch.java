package paintshop.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that models a batch request, it contains the number of colours to be mixed for the batch, 
 * a list of customers and their list of preferences and the formatted result once the batch is mixed 
 * @author danielaguado
 *
 */
public class PaintBatch {

	private static final String NO_SOLUTION = "IMPOSSIBLE";

	private int numColours;
	private List<Customer> customers;
	private String batchFormatted;

	/**
	 * Constructor that receives the number of colours
	 * to be mixed for the batch as parameter
	 * @param numColours The number of colours
	 */
	public PaintBatch(final int numColours) {
		this.numColours = numColours;
	}

	/**
	 * Method that calculates the optimal colour combination
	 * if it's possible to satisfy all the customers
	 * @return The formatted optimal combination or IMPOSSIBLE if there isn't a solution
	 */
	public String mixColours() {
		Map<Integer, ColourType> tempSolution = new HashMap<>();
		Map<Integer, ColourType> solution = new HashMap<>();
		
		determineSolution(0, tempSolution, solution);

		if (solution.isEmpty()) {
			batchFormatted = NO_SOLUTION;
		} else {
			batchFormatted = formatOutput(solution, numColours);
		}
		return batchFormatted;
	}

	/**
	 * Recursive method that finds the best solution by using a backtracking algorithm
	 * @param currentCustomerIndex the customer being explored now
	 * @param tempSolution The solution being calculated at the current iteration
	 * @param bestSolution The optimal solution if there is any
	 * @return if a valid solution was found
	 */
	private boolean determineSolution(int currentCustomerIndex, Map<Integer, ColourType> tempSolution, Map<Integer, ColourType> bestSolution) {

		//Base case, we have reached the last customer
		if (currentCustomerIndex == customers.size()) {
			return true;
		} else {
			Customer cust = customers.get(currentCustomerIndex);

			//Current customer might be already satisfied with the current temp solution
			//and we just would need to jump to the next customer
			if (!isCustomerSatisfied(tempSolution, cust)) {

				//Try all customer's preferences
				for (Colour currentColour : cust.getColourPreferences()) {
					if (!tempSolution.containsKey(currentColour.getNumber())) {

						//Add the current colour to the temporary solution and jump to the next customer
						tempSolution.put(currentColour.getNumber(), currentColour.getType());
						boolean solutionFound = determineSolution(currentCustomerIndex + 1, tempSolution, bestSolution);

						//If we found a solution, determine if it's a more optimal solution or discard it
						if (solutionFound) {
							determineSolutionWithLessMatteColours(tempSolution, bestSolution);
						}
						//Remove the last colour we tried before moving to the next one
						tempSolution.remove(currentColour.getNumber());
					}
				}
				//After trying with all current customer's preferences, no one will give a satisfactory solution 
				if (!isCustomerSatisfied(tempSolution, cust)) {
					return false;
				}
			} else {
				//Customer is already satisfied with the current solution, jump to the next one
				boolean solutionFound = determineSolution(currentCustomerIndex + 1, tempSolution, bestSolution);

				//If we found a solution, determine if it's a more optimal solution or discard it
				if (solutionFound) {
					determineSolutionWithLessMatteColours(tempSolution, bestSolution);
				}
			}
		}
		return false;
	}

	/**
	 * Determines whether the solution passed as parameter satisfies the customer
	 * by checking if there is one of the customers preferences in the solution 
	 * @param solution the solution
	 * @param customer the customer
	 * @return true if the solution satisfies customer's preferences
	 */
	private boolean isCustomerSatisfied(Map<Integer, ColourType> solution, Customer customer) {

		for (Colour colour : customer.getColourPreferences()) {
			if ((solution.containsKey(colour.getNumber()) && solution.get(colour.getNumber()).equals(colour.getType()))) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Compares both solutions passed as parameters and updates bestSolution 
	 * with the one that has less MATTE colours
	 * @param tempSolution the temporary solution
	 * @param bestSolution the current best solution
	 */
	private void determineSolutionWithLessMatteColours(Map<Integer, ColourType> tempSolution, Map<Integer, ColourType> bestSolution) {
		if (bestSolution.isEmpty() || sumMatte(tempSolution) < sumMatte(bestSolution)) {
			Map<Integer, ColourType> newSolution = new HashMap<>();
			newSolution.putAll(tempSolution);
			bestSolution.clear();
			bestSolution.putAll(newSolution);
		}
	}

	/**
	 * Calculates the number of MATTE colours in the solution
	 * @param solution the solution
	 * @return The number of MATTE colours in the solution
	 */
	private int sumMatte(Map<Integer, ColourType> solution) {
		return solution.values().stream().mapToInt(ColourType::type).sum();
	}

	/**
	 * Formats the solution into the required output format,
	 * i.e.: 1 0 0 means the first colour will be prepared in MATTE, the second and third in GLOSSY
	 * @param solution the solution to format
	 * @param numColors the number of colours
	 * @return the formatted solution
	 */
	private String formatOutput(Map<Integer, ColourType> solution, int numColors) {

		StringBuilder result = new StringBuilder();

		for (int i = 1; i <= numColors; i++) {
			if (result.length() > 0) {
				result.append(" ");
			}

			if (solution.containsKey(i)) {
				result.append(solution.get(i).type());
			} else {
				result.append(ColourType.GLOSSY.type());
			}
		}
		return result.toString();
	}

	/**
	 * Creates a customer from their colour preferences string
	 * and adds it to the customers list
	 * @param customerPreference The customer preferences string 
	 * as read from the input file 
	 */
	public void addCustomer(final String customerPreference) {
		if (customers == null) {
			customers = new ArrayList<>();
		}
		customers.add(new Customer(customerPreference));
	}

	public int getNumColours() {
		return numColours;
	}

	public List<Customer> getCustomers() {
		return customers;
	}
}