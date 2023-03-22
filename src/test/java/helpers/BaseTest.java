package helpers;

import io.restassured.response.Response;

public class BaseTest extends RequestHelper {
    public static Response response;

    public static class TestData {
        public static final String ERROR_MESSAGE_400_AUTHOR = "Author must be String type (Unicode)";
        public static final String ERROR_MESSAGE_400_NAME = "Name must be String type (Unicode)";
        public static final String ERROR_MESSAGE_400_YEAR = "Year must be Integer type";
        public static final String ERROR_MESSAGE_400_ELECTRONIC_BOOK = "isElectronicBook must be boolean type";
    }
}