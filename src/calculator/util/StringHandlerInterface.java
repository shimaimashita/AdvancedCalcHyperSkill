package calculator.util;

import calculator.exc.MyException;

import java.math.BigInteger;

public interface StringHandlerInterface {
    /**
     * Public function that converts string and performs calculations
     * @param inputString a string of operators and operands
     * @return calculation result
     */
    BigInteger getCalculationResult (String inputString) throws MyException;

    /**
     * Removes unnecessary whitespaces and plus signs,
     * Changes "+-" and "-+" to "-"
     * Changes "--" to "+"
     * @param inputString - an uncleaned string
     * @return a cleaned string
     */
    String cleanString(String inputString) throws MyException;

    /**
     * Changes variables to their values
     * @param inputString - a string with variables ("a", "aba", etc...)
     * @param mp
     * @return str - a string with values instead of variables
     */
    String putVariable(String inputString) throws MyException;

    /**
     * Changes string of operands and operators in infix notation
     * to postfix notation
     * @param inputString
     * @return
     */
    String toPostFixView(String inputString) throws MyException;

    /**
     * Performs calculations on string of operands and operators
     * in postfix notation
     * @param inputString
     * @return result of calculations
     */
    BigInteger getPostFixCalculation(String inputString);

}
