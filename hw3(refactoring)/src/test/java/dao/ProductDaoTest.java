package dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import product.Product;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static dao.DaoUtils.cleanTables;
import static dao.DaoUtils.createTables;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductDaoTest {

    private static List<Product> PRODUCTS_EXAMPLE;

    private ProductDao productDao;

    @Before
    public void setUp() throws SQLException {
        PRODUCTS_EXAMPLE = Arrays.asList(
                new Product("product1", 100),
                new Product("product2", 200),
                new Product("product3", 300)
        );
        productDao = new ProductDao();
        createTables();
    }

    @After
    public void cleanUp() throws Exception {
        cleanTables();
    }

    private void insertProducts(List<Product> products) throws SQLException {
        for (Product product : products) {
            productDao.insert(product);
        }
    }

    @Test
    public void testGetFromEmptyDB() throws SQLException {
        assertThat(productDao.getProducts()).isEmpty();
    }

    @Test
    public void testFindFromEmptyDB() throws SQLException {
        assertThat(productDao.findMinPriceProduct()).isEmpty();
    }

    @Test
    public void testInsert() throws SQLException {
        insertProducts(PRODUCTS_EXAMPLE);
        assertThat(productDao.getProducts()).containsExactlyInAnyOrder(PRODUCTS_EXAMPLE.toArray(new Product[3]));
    }

    @Test
    public void testFindMaxPriceProduct() throws SQLException {
        insertProducts(PRODUCTS_EXAMPLE);
        Optional<Product> product = productDao.findMaxPriceProduct();
        assertThat(product).isPresent();
        assertThat(product.get()).isEqualTo(new Product("product3", 300));
    }

    @Test
    public void testFindMinPriceProduct() throws SQLException {
        insertProducts(PRODUCTS_EXAMPLE);
        Optional<Product> product = productDao.findMinPriceProduct();
        assertThat(product).isPresent();
        assertThat(product.get()).isEqualTo(new Product("product1", 100));
    }

    @Test
    public void testGetPricesSum() throws SQLException {
        insertProducts(PRODUCTS_EXAMPLE);
        assertThat(productDao.getPricesSum()).isEqualTo(600);
    }

    @Test
    public void testGetProductsCount() throws SQLException {
        insertProducts(PRODUCTS_EXAMPLE);
        assertThat(productDao.getProductsCount()).isEqualTo(3);
    }

}
