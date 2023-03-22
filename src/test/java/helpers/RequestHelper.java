package helpers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import static helpers.CustomApiListener.withCustomTemplates;

public class RequestHelper {
    public static ConfigProp prop = new ConfigProp();
    Logger logger = LoggerFactory.getLogger(RequestHelper.class);

    public Response sendRequest(String service, String ID, String requestMethod,
                                List<Params> params, Object body) throws IOException {

        String url = prop.getPropertyByKey("host") + service + ID;

        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.body(body);
        requestSpecification.filter(withCustomTemplates());
        requestSpecification.log().all();

        for (Params param : params) {
            switch (param.getType()) {
                case HEADER:
                    requestSpecification.header(param.getKey(), param.getValue());
                    break;
                case PARAM:
                    requestSpecification.param(param.getKey(), param.getValue());
                    break;
                case BODY:
                    requestSpecification.body(body);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid ParamType: " + param.getType());
            }
        }

        Response response = requestSpecification.request(requestMethod, url);
        logger.info("Response body: {}", response.getBody().asPrettyString());

        return response;
    }
}
