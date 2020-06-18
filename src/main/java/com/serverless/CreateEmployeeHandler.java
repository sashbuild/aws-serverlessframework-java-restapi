package com.serverless;

import java.util.Collections;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateEmployeeHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse>
{

    private static final Logger LOG = LogManager.getLogger(CreateEmployeeHandler.class);

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context)
    {
        LOG.info("received CreateEmployeeHandler request: {}", input);
        try
        {
            JsonNode body = new ObjectMapper().readTree((String) input.get("body"));
            Employee employee = new Employee();
            employee.setName(body.get("name").asText());
            employee.setDepartment(body.get("department").asText());

            employee.create(employee);

            return ApiGatewayResponse.builder()
                    .setStatusCode(200)
                    .setObjectBody(employee)
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
