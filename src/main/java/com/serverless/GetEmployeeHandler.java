package com.serverless;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetEmployeeHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse>
{

    private static final Logger LOG = LogManager.getLogger(GetEmployeeHandler.class);

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context)
    {
        LOG.info("received GetEmployeeHandler request: {}", input);
        try
        {

            List<Employee> employeeList = new Employee().retrieveAll();

            return ApiGatewayResponse.builder()
                    .setStatusCode(200)
                    .setObjectBody(employeeList)
                    .setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless"))
                    .build();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Response responseBody = new Response("Vada Pochey", input);
            return ApiGatewayResponse.builder()
                    .setStatusCode(500)
                    .setObjectBody(responseBody)
                    .setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless"))
                    .build();
        }
    }
}
