package api;

import helpers.BaseTest;
import helpers.Params;
import helpers.RequestHelper;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.BookInfo;
import pojo.ErrorResponse;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DisplayName("Тесты с методом PUT")
public class PutTest extends BaseTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    RequestHelper requestHelper = new RequestHelper();

    @Test
    @DisplayName("Обновление информации о книге")
    public void updateBook() throws IOException {
        List<Params> params = List.of(
                new Params("Content-Type", "application/json", Params.ParamType.HEADER));

        BookInfo bookInfo = BookInfo.builder()
                .author("Новый автор")
                .isElectronicBook(true)
                .name("Новое имя книги")
                .year(1800)
                .build();
        String body = MAPPER.writeValueAsString(bookInfo);

        response = requestHelper.sendRequest(
                prop.getPropertyByKey("service"),
                "/7",
                "PUT",
                params, body);

        assertThat(response.getStatusCode(), equalTo(200));
    }

    @Test
    @DisplayName("Обновление информации о книге, не указав автора")
    public void updateBookWithoutAuthor() throws IOException {
        List<Params> params = List.of(
                new Params("Content-Type", "application/json", Params.ParamType.HEADER));

        BookInfo bookInfo = BookInfo.builder()
                .isElectronicBook(true)
                .name("Новое имя книги")
                .year(1800)
                .build();
        String body = MAPPER.writeValueAsString(bookInfo);

        response = requestHelper.sendRequest(
                prop.getPropertyByKey("service"),
                "/7",
                "PUT",
                params, body);

        assertThat(response.getStatusCode(), equalTo(400));
        String json = response.getBody().asString();
        ErrorResponse errorResponse = MAPPER.readValue(json, ErrorResponse.class);
        assertThat(errorResponse.getError(), equalTo(TestData.ERROR_MESSAGE_400_AUTHOR));
    }

    @Test
    @DisplayName("Обновление информации о книге, не указав название")
    public void updateBookWithoutName() throws IOException {
        List<Params> params = List.of(
                new Params("Content-Type", "application/json", Params.ParamType.HEADER));

        BookInfo bookInfo = BookInfo.builder()
                .author("Новый автор")
                .isElectronicBook(true)
                .year(1800)
                .build();
        String body = MAPPER.writeValueAsString(bookInfo);

        response = requestHelper.sendRequest(
                prop.getPropertyByKey("service"),
                "/7",
                "PUT",
                params, body);

        assertThat(response.getStatusCode(), equalTo(400));
        String json = response.getBody().asString();
        ErrorResponse errorResponse = MAPPER.readValue(json, ErrorResponse.class);
        assertThat(errorResponse.getError(), equalTo(TestData.ERROR_MESSAGE_400_NAME));
    }

    @Test
    @DisplayName("Обновление информации о книге, не указав год")
    public void updateBookWithoutYear() throws IOException {
        List<Params> params = List.of(
                new Params("Content-Type", "application/json", Params.ParamType.HEADER));

        BookInfo bookInfo = BookInfo.builder()
                .author("Новый автор")
                .isElectronicBook(true)
                .name("Новое имя книги")
                .build();
        String body = MAPPER.writeValueAsString(bookInfo);

        response = requestHelper.sendRequest(
                prop.getPropertyByKey("service"),
                "/7",
                "PUT",
                params, body);

        assertThat(response.getStatusCode(), equalTo(400));
        String json = response.getBody().asString();
        ErrorResponse errorResponse = MAPPER.readValue(json, ErrorResponse.class);
        assertThat(errorResponse.getError(), equalTo(TestData.ERROR_MESSAGE_400_YEAR));
    }

    @Test
    @DisplayName("Обновление информации о книге, не указав тип книги")
    public void updateBookWithoutType() throws IOException {
        List<Params> params = List.of(
                new Params("Content-Type", "application/json", Params.ParamType.HEADER));

        BookInfo bookInfo = BookInfo.builder()
                .author("Новый автор")
                .name("Новое имя книги")
                .year(1800)
                .build();
        String body = MAPPER.writeValueAsString(bookInfo);

        response = requestHelper.sendRequest(
                prop.getPropertyByKey("service"),
                "/7",
                "PUT",
                params, body);

        assertThat(response.getStatusCode(), equalTo(400));
        String json = response.getBody().asString();
        ErrorResponse errorResponse = MAPPER.readValue(json, ErrorResponse.class);
        assertThat(errorResponse.getError(), equalTo(TestData.ERROR_MESSAGE_400_ELECTRONIC_BOOK));
    }

    @Test
    @DisplayName("Обновление информации о книге с несуществующим ID")
    public void updateBookWithWrongID() throws IOException {
        List<Params> params = List.of(
                new Params("Content-Type", "application/json", Params.ParamType.HEADER));

        BookInfo bookInfo = BookInfo.builder()
                .author("Новый автор")
                .isElectronicBook(true)
                .name("Новое имя книги")
                .year(1800)
                .build();
        String body = MAPPER.writeValueAsString(bookInfo);

        response = requestHelper.sendRequest(
                prop.getPropertyByKey("service"),
                "/1999",
                "PUT",
                params, body);

        assertThat(response.getStatusCode(), equalTo(404));
        String json = response.getBody().asString();
        ErrorResponse errorResponse = MAPPER.readValue(json, ErrorResponse.class);
        assertThat(errorResponse.getError(), containsString("not found"));
    }
}
