package dao;

import http.MarketHttpClient;
import org.junit.*;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryAccountDaoTest {
    @ClassRule
    public static GenericContainer marketContainer = new FixedHostPortGenericContainer("market:1.0-SNAPSHOT")
            .withFixedExposedPort(8080, 8080)
            .withExposedPorts(8080);

    private AccountDao dao;

    private final static String TEST_COMPANY_NAME = "test_company";
    private final static int TEST_COMPANY_STOCKS_PRICE = 10;
    private final long TEST_USER_ID = 0;
    private final int TEST_USER_INITIAL_MONEY = 1000;

    @Before
    public void setUp() throws Exception {
        marketContainer.start();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/add_company?name=" + TEST_COMPANY_NAME + "&stocks_count=1000&stocks_price=" + TEST_COMPANY_STOCKS_PRICE))
                .GET()
                .build();

        HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        dao = new InMemoryAccountDao(new MarketHttpClient());
        dao.addUser(TEST_USER_ID);
        dao.addMoney(TEST_USER_ID, TEST_USER_INITIAL_MONEY);
    }

    @After
    public void tearDown() {
        marketContainer.stop();
    }

    @Test
    public void buyStocks() {
        assertThat(dao.buyStocks(TEST_USER_ID, TEST_COMPANY_NAME, 10).toBlocking().single())
                .isEqualTo("SUCCESS");
    }

    @Test
    public void sellStocks() {
        assertThat(dao.sellStocks(TEST_USER_ID, TEST_COMPANY_NAME, 10).toBlocking().single())
                .isEqualTo("SUCCESS");
    }

    @Test
    public void testNotExistingCompany() {
        assertThat(dao.buyStocks(TEST_USER_ID, "aaa", 10).toBlocking().single())
                .isEqualTo("Company with name 'aaa' doesn't exists");
    }

    @Test
    public void testNotEnoughStocks() {
        assertThat(dao.buyStocks(TEST_USER_ID, TEST_COMPANY_NAME, 2000).toBlocking().single())
                .isEqualTo("Not enough stocks in market");
    }

    @Test
    public void testNotEnoughMoney() {
        assertThat(dao.buyStocks(TEST_USER_ID, TEST_COMPANY_NAME, 150).toBlocking().single())
                .isEqualTo("Not enough money");
    }
}