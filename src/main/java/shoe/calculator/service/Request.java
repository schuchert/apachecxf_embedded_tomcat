package shoe.calculator.service;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Result")
public class Request {
    private String expression;

    public Request() {
        expression = "";
    }

    public Request(String result) {
        expression = result;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
