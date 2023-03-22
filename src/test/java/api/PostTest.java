package api;

import helpers.BaseTest;
import helpers.Params;
import helpers.RequestHelper;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.jupiter.api.*;
import pojo.BookInfo;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Тесты с методом POST")
public class PostTest extends BaseTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    RequestHelper requestHelper = new RequestHelper();

    @Test
    @Order(1)
    @DisplayName("Добавление новой книги")
    public void newBook() throws IOException {
        List<Params> params = List.of(
                new Params("Content-Type", "application/json", Params.ParamType.HEADER));

        BookInfo bookInfo = BookInfo.builder()
                .name("Теория всего")
                .build();
        String body = MAPPER.writeValueAsString(bookInfo);

        response = requestHelper.sendRequest(
                prop.getPropertyByKey("service"),
                "",
                "POST",
                params, body);

        assertThat(response.getStatusCode(), equalTo(201));
    }

    @Test
    @Order(2)
    @DisplayName("Добавление новой книги с аналогичными параметрами")
    public void repeatNewBook() throws IOException {
        List<Params> params = List.of(
                new Params("Content-Type", "application/json", Params.ParamType.HEADER));

        BookInfo bookInfo = BookInfo.builder()
                .name("Теория всего")
                .build();
        String body = MAPPER.writeValueAsString(bookInfo);

        response = requestHelper.sendRequest(
                prop.getPropertyByKey("service"),
                "",
                "POST",
                params, body);

        assertThat(response.getStatusCode(), equalTo(404));
    }

    @Test
    @DisplayName("Добавление новой книги со всеми заполненными полями")
    public void newBookWithAllBody() throws IOException {
        List<Params> params = List.of(
                new Params("Content-Type", "application/json", Params.ParamType.HEADER));

        BookInfo bookInfo = BookInfo.builder()
                .author("Иван Петров")
                .isElectronicBook(true)
                .name("Рандомная книга")
                .year(1999)
                .build();

        String body = MAPPER.writeValueAsString(bookInfo);

        response = requestHelper.sendRequest(
                prop.getPropertyByKey("service"),
                "",
                "POST",
                params, body);

        assertThat(response.getStatusCode(), equalTo(201));
    }

    @Test
    @DisplayName("Добавление книги без заполнения полей")
    public void newBookWithoutBody() throws IOException {
        List<Params> params = List.of(
                new Params("Content-Type", "application/json", Params.ParamType.HEADER));

        response = requestHelper.sendRequest(
                prop.getPropertyByKey("service"),
                "",
                "POST",
                params, "");

        assertThat(response.getStatusCode(), equalTo(400));
    }

    @Test
    @DisplayName("Добавление новой книги с английским названием")
    public void newBookEng() throws IOException {
        List<Params> params = List.of(
                new Params("Content-Type", "application/json", Params.ParamType.HEADER));

        BookInfo bookInfo = BookInfo.builder()
                .name("Random Book")
                .build();
        String body = MAPPER.writeValueAsString(bookInfo);

        response = requestHelper.sendRequest(
                prop.getPropertyByKey("service"),
                "",
                "POST",
                params, body);

        assertThat(response.getStatusCode(), equalTo(201));
    }

    @Test
    @DisplayName("Добавление новой книги с заполненным ID")
    public void newBookWithManualID() throws IOException {
        List<Params> params = List.of(
                new Params("Content-Type", "application/json", Params.ParamType.HEADER));

        BookInfo bookInfo = BookInfo.builder()
                .id(1999)
                .name("Random Book")
                .build();
        String body = MAPPER.writeValueAsString(bookInfo);

        response = requestHelper.sendRequest(
                prop.getPropertyByKey("service"),
                "",
                "POST",
                params, body);

        assertThat(response.getStatusCode(), equalTo(400));
    }

    @Test
    @DisplayName("Добавление новой книги с отрицательным годом выпуска")
    public void newBookWithNegativeYear() throws IOException {
        List<Params> params = List.of(
                new Params("Content-Type", "application/json", Params.ParamType.HEADER));

        BookInfo bookInfo = BookInfo.builder()
                .name("Random Book")
                .year(-1999)
                .build();
        String body = MAPPER.writeValueAsString(bookInfo);

        response = requestHelper.sendRequest(
                prop.getPropertyByKey("service"),
                "",
                "POST",
                params, body);

        assertThat(response.getStatusCode(), equalTo(400));
    }

    @Test
    @DisplayName("Добавление новой книги с пустым значением")
    public void newBookWithEmptyName() throws IOException {
        List<Params> params = List.of(
                new Params("Content-Type", "application/json", Params.ParamType.HEADER));

        BookInfo bookInfo = BookInfo.builder()
                .name("")
                .build();
        String body = MAPPER.writeValueAsString(bookInfo);

        response = requestHelper.sendRequest(
                prop.getPropertyByKey("service"),
                "",
                "POST",
                params, body);

        assertThat(response.getStatusCode(), equalTo(400));
    }
}
