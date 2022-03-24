package calculator.util;

import calculator.exc.MyException;

import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHandler implements StringHandlerInterface {

    private Map<String, Integer> mp;

    public StringHandler() {
        this.mp = null;
    }

    public StringHandler(Map<String, Integer> mp) {
        this.mp = mp;
    }

    @Override
    public int getCalculationResult(String inputString) throws MyException {
        if (checkParentheses(inputString)) {
            return getPostFixCalculation(toPostFixView(cleanString(putVariable(inputString))));
        } else {
            throw new MyException("Invalid expression");
        }
    }



    public void setMap(Map<String, Integer> mp) {
        this.mp = mp;
    }

    @Override
    public String cleanString(String inputString) throws MyException {
        if (inputString.matches(".*[*/^]{2,}.*")) {
            throw new MyException("Invalid expression");
        }
        inputString = "0 +" + inputString;
        inputString = inputString.replaceAll("(^\\s*)|(\\s*$)", "");
        inputString = inputString.replaceAll("-\\s*-", "+");
        inputString = inputString.replaceAll("\\+{2,}", "+");
        inputString = inputString.replaceAll("-\\s*\\+|\\+\\s*-", "-");
        inputString = inputString.replaceAll("\\(", " ( ");
        inputString = inputString.replaceAll("\\)", " ) ");
        inputString = inputString.replaceAll("\\+", " + ");
        inputString = inputString.replaceAll("\\*", " * ");
        inputString = inputString.replaceAll("-", " - ");
        inputString = inputString.replaceAll("/", " / ");
        inputString = inputString.replaceAll("\\^", " ^ ");
        inputString = inputString.replaceAll("\\s{2,}", (" "));
        return inputString;
    }

    @Override
    public String putVariable(String inputString) throws MyException {
        Pattern pattern = Pattern.compile("\\b([a-zA-Z]+)\\b");
        Matcher matcher = pattern.matcher(inputString);

        while (matcher.find()) {
            Integer result = mp.get(matcher.group(1));

            if (result == null) {
                throw new MyException("Unknown variable");
            }
            inputString = inputString.replaceAll(matcher.group(1), result.toString());
        }
        if (inputString.matches(".*[a-zA-Z]+.*")) {
            throw new MyException("Invalid assignment");
        } else {
            return inputString;
        }
    }

    static private int Prec(char ch)
    {
        switch (ch)
        {
            case '+':
            case '-':
                return 1;

            case '*':
            case '/':
                return 2;

            case '^':
                return 3;
        }
        return -1;
    }

    @Override
    public String toPostFixView(String inputString) throws MyException {
        // initializing empty String for result
        String result = new String(" ");

        // initializing empty stack
        Stack<Character> stack = new Stack<>();

        String[] array = inputString.split("\\s");

        for (int i = 0; i<array.length; ++i)
        {
            String c = array[i];

            // If the scanned character is an
            // operand, add it to output.
            if (c.matches("\\d+"))
                result += c + " ";

                // If the scanned character is an '(',
                // push it to the stack.
            else if (c.equals("("))
                stack.push('(');

                //  If the scanned character is an ')',
                // pop and output from the stack
                // until an '(' is encountered.
            else if (c.equals(")"))
            {
                while (!stack.isEmpty() &&
                        stack.peek() != '(')
                    result += stack.pop() + " ";

                stack.pop();
            }
            else // an operator is encountered
            {
                while (!stack.isEmpty() && Prec(c.charAt(0))
                        <= Prec(stack.peek())){

                    result += stack.pop() + " ";
                }
                stack.push(c.charAt(0));
            }

        }

        // pop all the operators from the stack
        while (!stack.isEmpty()){
            if(stack.peek() == '(')
                throw new MyException("Invalid Expression");
            result += stack.pop() + " ";
        }
        return result.trim();
    }

    @Override
    public int getPostFixCalculation(String inputString) {
        LinkedList<Integer> stack = new LinkedList<>();
        String[] operands = inputString.split("\\s");
        for (int i = 0; i < operands.length; i++) {
            if (operands[i].matches("[0-9]+")) {
                stack.push(Integer.parseInt(operands[i]));
            } else {
                int second = stack.pop();
                int first = stack.pop();
                switch (operands[i]) {
                    case "+":
                        stack.push(first + second);
                        break;
                    case "-":
                    case "–":
                        stack.push(first - second);
                        break;
                    case "*":
                        stack.push(first * second);
                        break;
                    case "/":
                        stack.push(first / second);
                        break;
                    case "^":
                        stack.push((int) Math.pow(first, second));
                }
            }
        }
        return stack.pop();
    }

    private boolean checkParentheses(String inputString) {
        LinkedList<Character> stack = new LinkedList<>();
        for (Character ch : inputString.toCharArray()) {
            if (ch.equals('(')) {
                stack.push('(');
            } else if (ch.equals(')')) {
                if (stack.peek() == null || !(stack.peek().equals('('))) {
                    return false;
                } else {
                    stack.pop();
                }
            }
        }
        if (stack.size() != 0) return false;
        else return true;
    }

}
