package shoe.calculator;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/beans.xml"})
public class RpnCalculatorTest {
    @Autowired
    private RpnCalculator calculator;

    @After
    public void clearAll() {
        calculator.clear();
    }

    @Test
    public void addingTwoNumbers() {
        calculator.enter("5");
        calculator.enter("2");
        calculator.execute("+");
        assertThat(calculator.currentValue(), is(new BigDecimal(7)));
    }

    @Test
    public void subtractingTwoNumbers() {
        calculator.enter("5");
        calculator.enter("2");
        calculator.execute("-");
        assertThat(calculator.currentValue(), is(new BigDecimal(3)));
    }

    @Test
    public void addNoNumbers() {
        calculator.execute("+");
        assertThat(calculator.currentValue(), is(BigDecimal.ZERO));
    }

    @Test
    public void multiplyingTwoNumbers() {
        calculator.enter("5");
        calculator.enter("2");
        calculator.execute("*");
        assertThat(calculator.currentValue(), is(new BigDecimal(10)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void useUnknownOperator() {
        calculator.execute("bogus_operator");
    }

    @Test
    public void divideTwoNumbers() {
        calculator.enter("5");
        calculator.enter("2");
        calculator.execute("/");
        assertThat(calculator.currentValue(), is(new BigDecimal("2.5")));
    }

    @Test
    public void executeExpression()  {
        calculator.evaluate("5 7 *");
        assertThat(calculator.currentValue(), is(new BigDecimal(35)));
    }
}
