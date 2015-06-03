package shoe.calculator.service;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Result")
public class Result {
    private String currentValue;

    public Result() {
        currentValue = "";
    }

    public Result(String result) {
        currentValue = result;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

}
