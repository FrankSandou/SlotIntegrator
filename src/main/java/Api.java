import com.sun.source.tree.AssertTree;

import io.cucumber.java.ru.И;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomStringUtils.*;

import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static io.restassured.RestAssured.given;
import static java.util.Collections.sort;
import static org.junit.Assert.assertTrue;

public class Api {
    public Map<String, Object> storage = new HashMap<>();
    public RequestSpecification request = given().relaxedHTTPSValidation();
    public Response response;

    @И("^печать в консоль \"(.*)\"")
    public void печатьЗначение(String value) throws Throwable {
        System.out.println(value);

    }

    @И("^запросить данные игрока \"(.*)\"")
    public void запросить_данные_игрока(String email) throws Throwable {

        String context = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "\\src\\test\\player.json")));
        context = context.replace("{email}", email);

        response =


                request.header(
                                "Authorization",
                                "Bearer " + storage.get("accessToken")
                        ).header("Content-Type",
                                "application/json")
                        .header("accept",
                                "application/json")
                        .body(context)
                        .when()
                        .post("https://testslotegrator.com/api/automationTask/getOne")
                        .then()
                        .extract()
                        .response();
        Integer Status = response.getStatusCode();
        //        assertTrue ("Не соответствует статус ответа -200",
//                (response.getStatusCode()==200));
        System.out.println(response.getBody().jsonPath().prettyPrint());

    }

    public static String getRandomLengthString(Integer count) {

        String generatedString = RandomStringUtils.randomAlphabetic(count);
        return generatedString;
    }

    @И("сбросить реквест")
    public void сбросРеквеста() throws Throwable {
        request = null;
        request = given().relaxedHTTPSValidation();

    }

    @И("запросить список всех пользователей и убедиться что он пустой")
    public void запросить_список_всех_пользователей_и_убедиться_что_он_пустой() throws Throwable {
        response =


                request.header(
                                "Authorization",
                                "Bearer " + storage.get("accessToken")
                        ).header("Content-Type",
                                "application/json")
                        .header("accept",
                                "application/json")

                        .when()
                        .get("https://testslotegrator.com/api/automationTask/getAll")
                        .then()
                        .extract()
                        .response();
        Integer Status = response.getStatusCode();
        System.out.println(response.getBody().jsonPath().prettyPrint());
        assertTrue("Cписок не пуст",
                ((response.getBody().jsonPath().equals(null))));

    }

    @И("запросить данные всех пользователей и отсортировать их по имени")
    public void запросить_данные_всех_пользователей_и_отсортировать_их_по_имени() throws Throwable {
        response =


                request.header(
                                "Authorization",
                                "Bearer " + storage.get("accessToken")
                        ).header("Content-Type",
                                "application/json")
                        .header("accept",
                                "application/json")

                        .when()
                        .get("https://testslotegrator.com/api/automationTask/getall")
                        .then()
                        .extract()
                        .response();
        Integer Status = response.getStatusCode();

        response.getBody().jsonPath().get();

        ;
        Collections.sort(response.getBody().jsonPath().get(), new LinkedHashMapComparator("email"));

        System.out.println(response.getBody().jsonPath().prettyPrint());


    }

    class LinkedHashMapComparator implements Comparator<LinkedHashMap<String, String>> {
        private final String field;

        public LinkedHashMapComparator(String field) {
            this.field = field;
        }

        @Override
        public int compare(LinkedHashMap<String, String> map1, LinkedHashMap<String, String> map2) {
            String value1 = map1.get(field);
            String value2 = map2.get(field);

            return value1.compareTo(value2);
        }

    }

    @И("^создать \"(.*)\" игроков")
    public void игроки_с_параметрами(String val) throws Throwable {
        for (Integer i = 1; i <= Integer.valueOf(val); i++) {
            игрок_с_параметрами();
            сбросРеквеста();
        }
    }

    @И("создать игрока")
    public void игрок_с_параметрами() throws Throwable {
        String password = getRandomLengthString(4);
        String value = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "\\src\\test\\player.json")));
        value = value.replace("{currency_code}", getRandomLengthString(4))
                .replace("{email}", getRandomLengthString(4))
                .replace("{name}", getRandomLengthString(4))
                .replace("{password_change}", password)
                .replace("{password_repeat}", password)
                .replace("{surname}", getRandomLengthString(4))
                .replace("{username}", getRandomLengthString(4));

        response =


                request.header(
                                "Authorization",
                                "Bearer " + storage.get("accessToken")
                        ).header("Content-Type",
                                "application/json")
                        .header("accept",
                                "application/json")
                        .body(value)
                        .when()
                        .post("https://testslotegrator.com/api/automationTask/create")
                        .then()
                        .extract()
                        .response();
        Integer Status = response.getStatusCode();
        assertTrue("Не соответствует статус ответа -201",
                (response.getStatusCode() == 201));
        System.out.println(response.getBody().jsonPath().prettyPrint());

//        assertTrue ("Не соответствует размер полей в ответе",
//                ((LinkedHashMap)response.getBody().jsonPath().get()).size()==6);
    }


    @И("^и создали игрока с  параметрами:$")
    public void игрок_с_параметрами(List<List<String>> table) throws Throwable {
        RestAssured.useRelaxedHTTPSValidation();
        if (table.size() < 1) {
            throw new Exception("Таблица в cucumber не заполнена");
        }

        for (int i = 0; i < table.size(); i++) {
            request.header(table.get(i).get(0), table.get(i).get(1));
        }

    }

    @И("^проверить токен пользователя")
    public void проверить_токен_пользователя() throws Throwable {
        response =
                given()

                        .header("Content-Type",
                                "application/json")
                        .header("accept",
                                "*/*")


                        .body(new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "\\src\\test\\token.json"))))
                        .when()
                        .post("https://testslotegrator.com/api/tester/login")
                        .then()
                        .extract()
                        .response();
        Integer Status = response.getStatusCode();
        System.out.println(Status);
//        assertTrue ("Не соответствует статус ответа -200",
//                (response.getStatusCode()==200);

        //        assertTrue ("Не соответствует размер полей в ответе",
//                ((LinkedHashMap)response.getBody().jsonPath().get()).size()==4);
//        assertTrue ("Не соответствует размер полей в ответе",
//                ((LinkedHashMap)response.getBody().jsonPath().get()).containsKey("accessToken"));

        if (response.getBody().jsonPath().get("accessToken") != null) {
            storage.put("accessToken", response.getBody().jsonPath().get("accessToken").toString());
        }
    }
}
