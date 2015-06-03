package shoe.calculator;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Stack;

@Component
public class RpnCalculator {
    Stack<BigDecimal> values;

    public RpnCalculator() {
        values = new Stack<BigDecimal>() {
            @Override
            public BigDecimal peek() {
                return empty() ? BigDecimal.ZERO : super.peek();
            }

            @Override
            public BigDecimal pop() {
                return empty() ? BigDecimal.ZERO : super.pop();
            }
        };
    }

    public void enter(String value) {
        values.push(new BigDecimal(value));
    }

    public void execute(String operator) {
        BigDecimal rhs = values.pop();
        BigDecimal lhs = values.pop();

        BigDecimal result = BigDecimal.ZERO;

        switch (operator) {
            case "+":
                result = lhs.add(rhs);
                break;
            case "-":
                result = lhs.subtract(rhs);
                break;
            case "*":
                result = lhs.multiply(rhs);
                break;
            case "/":
                result = lhs.divide(rhs);
                break;
            default:
               throw new IllegalArgumentException(String.format("Unknown operator: %s", operator));
        }
        values.push(result);
    }

    public BigDecimal currentValue() {
        return values.peek();
    }

    public void evaluate(String expression) {
        String[] parts = expression.split("\\s+");
        Arrays.stream(parts).forEach(p -> {
            if(p.matches("^[-+*/]$")) {
                execute(p);
            } else {
                enter(p);
            }
        });
    }

    public void clear() {
        values.clear();
    }
}
