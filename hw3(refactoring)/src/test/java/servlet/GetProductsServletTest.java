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
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class GetProductsServletTest {

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
        servlet = new GetProductsServlet(productDao);
    }

    @Test
    public void testGetProductServlet() throws IOException, SQLException {
        when(productDao.getProducts())
                .thenReturn(Arrays.asList(
                        new Product("iphone6", 300),
                        new Product("iphoneX", 800)
                ));

        StringWriter stringWriter = new StringWriter();
        PrintWriter printer = new PrintWriter(stringWriter);
        when(servletResponse.getWriter())
                .thenReturn(printer);

        servlet.doGet(servletRequest, servletResponse);

        printer.flush();

        assertThat(stringWriter.toString()).isEqualToIgnoringNewLines(
                "<html><body>" +
                "iphone6\t300</br>" +
                "iphoneX\t800</br>" +
                "</body></html>"
        );
    }

}
