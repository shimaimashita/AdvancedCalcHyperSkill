package calculator.util;

import calculator.exc.MyException;
import calculator.util.StringHandler;

import java.math.BigInteger;
import java.util.Map;
import java.util.TreeMap;

public class StringWorker {
    static final private Map<String, BigInteger> mp = new TreeMap<>();
    public void stringHandling(String inputString) throws MyException {
        // Assigning:
        if (inputString.contains("=")) {
            String[] arrayOfParts = inputString.split("=");
            if (arrayOfParts.length != 2) {
                throw new MyException("Invalid assignment");
            } else {
                assignHandling(arrayOfParts[0], arrayOfParts[1]);
            }
        } else {
            System.out.println(CalculationHandling(inputString));

        }
    }

    private void assignHandling(String leftPart, String rightPart) throws MyException {
        leftPart = leftPart.trim();
        if (leftPart.matches(".*[^a-zA-Z].*")) {
            throw new MyException("Invalid identifier");
        }
        try {
            BigInteger tempValue = CalculationHandling(rightPart);
            BigInteger m = mp.put(leftPart, tempValue);
        } catch (Exception e) {
            throw new MyException(e.getMessage(), e);
        }
    }

    private BigInteger CalculationHandling(String inputString) throws MyException {
        StringHandler stringHandler = new StringHandler(mp);

        return stringHandler.getCalculationResult(inputString);
    }
}