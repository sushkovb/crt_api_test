package api;

import helpers.BaseTest;
import helpers.Params;
import helpers.RequestHelper;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.ErrorResponse;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Тесты с методом GET")
public class GetTest extends BaseTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    RequestHelper requestHelper = new RequestHelper();

    @Test
    @DisplayName("Получение списка книг")
    public void allBooks() throws IOException {
        List<Params> params = List.of(
                new Params("Content-Type", "application/json", Params.ParamType.HEADER));

        response = requestHelper.sendRequest(
                prop.getPropertyByKey("service"),
                "",
                "GET",
                params, "");

        assertThat(response.getStatusCode(), equalTo(200));
    }

    @Test
    @DisplayName("Получение книги по ID")
    public void bookID() throws IOException {
        List<Params> params = List.of(
                new Params("Content-Type", "application/json", Params.ParamType.HEADER));

        response = requestHelper.sendRequest(
                prop.getPropertyByKey("service"),
                "/1",
                "GET",
                params, "");

        assertThat(response.getStatusCode(), equalTo(200));
    }

    @Test
    @DisplayName("Запрос книги с несуществующим ID")
    public void noExistID() throws IOException {
        List<Params> params = List.of(
                new Params("Content-Type", "application/json", Params.ParamType.HEADER));

        response = requestHelper.sendRequest(
                prop.getPropertyByKey("service"),
                "/1555",
                "GET",
                params, "");

        assertThat(response.getStatusCode(), equalTo(404));
        String json = response.getBody().asString();
        ErrorResponse errorResponse = MAPPER.readValue(json, ErrorResponse.class);
        assertThat(errorResponse.getError(), containsString("not found"));
    }

    @Test
    @DisplayName("Запрос ID книги с буквенным значением")
    public void wrongID() throws IOException {
        List<Params> params = List.of(
                new Params("Content-Type", "application/json", Params.ParamType.HEADER));

        response = requestHelper.sendRequest(
                prop.getPropertyByKey("service"),
                "/==",
                "GET",
                params, "");

        assertThat(response.getStatusCode(), equalTo(404));
    }
}
