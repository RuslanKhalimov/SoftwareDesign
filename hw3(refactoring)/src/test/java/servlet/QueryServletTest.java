package servlet;

import dao.ProductDao;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import product.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class QueryServletTest {

    @Mock
    private ProductDao productDao;

    @Mock
    private HttpServletRequest servletRequest;

    @Mock
    private HttpServletResponse servletResponse;

    private AbstractProductServlet servlet;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        servlet = new QueryServlet(productDao);
    }

    @Test
    public void testMax() throws IOException, SQLException {
        when(servletRequest.getParameter("command"))
                .thenReturn("max");

        when(productDao.findMaxPriceProduct())
                .thenReturn(Optional.of(new Product("iphone6", 300)));

        StringWriter stringWriter = new StringWriter();
        PrintWriter printer = new PrintWriter(stringWriter);
        when(servletResponse.getWriter())
                .thenReturn(printer);

        servlet.doGet(servletRequest, servletResponse);

        printer.flush();
        assertThat(stringWriter.toString()).isEqualToIgnoringNewLines(
                "<html><body>" +
                "<h1>Product with max price: </h1>" +
                "iphone6\t300</br>" +
                "</body></html>"
        );
    }

    @Test
    public void testMin() throws IOException, SQLException {
        when(servletRequest.getParameter("command"))
                .thenReturn("min");

        when(productDao.findMinPriceProduct())
                .thenReturn(Optional.of(new Product("iphone6", 300)));

        StringWriter stringWriter = new StringWriter();
        PrintWriter printer = new PrintWriter(stringWriter);
        when(servletResponse.getWriter())
                .thenReturn(printer);

        servlet.doGet(servletRequest, servletResponse);

        printer.flush();
        assertThat(stringWriter.toString()).isEqualToIgnoringNewLines(
                "<html><body>" +
                "<h1>Product with min price: </h1>" +
                "iphone6\t300</br>" +
                "</body></html>"
        );
    }

    @Test
    public void testSum() throws IOException, SQLException {
        when(servletRequest.getParameter("command"))
                .thenReturn("sum");

        when(productDao.getPricesSum())
                .thenReturn(1000L);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printer = new PrintWriter(stringWriter);
        when(servletResponse.getWriter())
                .thenReturn(printer);

        servlet.doGet(servletRequest, servletResponse);

        printer.flush();
        assertThat(stringWriter.toString()).isEqualToIgnoringNewLines(
                "<html><body>" +
                "Summary price: " +
                "1000" +
                "</body></html>"
        );
    }

    @Test
    public void testCount() throws IOException, SQLException {
        when(servletRequest.getParameter("command"))
                .thenReturn("count");

        when(productDao.getProductsCount())
                .thenReturn(2);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printer = new PrintWriter(stringWriter);
        when(servletResponse.getWriter())
                .thenReturn(printer);

        servlet.doGet(servletRequest, servletResponse);

        printer.flush();
        assertThat(stringWriter.toString()).isEqualToIgnoringNewLines(
                "<html><body>" +
                "Number of products: " +
                "2" +
                "</body></html>"
        );
    }

}
