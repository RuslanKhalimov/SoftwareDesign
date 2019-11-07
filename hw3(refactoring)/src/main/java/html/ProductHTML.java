package html;

import product.Product;

import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

public class ProductHTML {

    public static void printProductsHTML(List<Product> products, PrintWriter printer) {
        printer.println("<html><body>");
        for (Product product : products) {
            printer.println(product.toHTML());
        }
        printer.println("</body></html>");
    }

    public static void printProductHTML(Optional<Product> product, String header, PrintWriter printer) {
        printer.println("<html><body>");
        printer.println("<h1>" + header + "</h1>");
        product.ifPresent(p -> printer.println(p.toHTML()));
        printer.println("</body></html>");
    }

    public static void printInfoHTML(Object info, String header, PrintWriter printer) {
        printer.println("<html><body>");
        printer.println(header);
        printer.println(info);
        printer.println("</body></html>");
    }

}
