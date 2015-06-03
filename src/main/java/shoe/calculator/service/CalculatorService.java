package shoe.calculator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import shoe.calculator.RpnCalculator;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/calculator")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class CalculatorService {
    @Autowired
    private RpnCalculator calculator;

    @GET
    @Path("/evaluate/{expression}/")
    public Result evaluate(@PathParam("expression") String expression) {
        calculator.evaluate(expression);
        return new Result(calculator.currentValue().toString());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/evaluate/")
    public Result evaluateExpression(Request request) {
        calculator.evaluate(request.getExpression());
        return new Result(calculator.currentValue().toString());
    }
}
