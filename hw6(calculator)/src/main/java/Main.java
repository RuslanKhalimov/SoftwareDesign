import token.Token;
import token.Tokenizer;
import visitor.CalcVisitor;
import visitor.ParserVisitor;
import visitor.PrintVisitor;

import java.io.*;
import java.text.ParseException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String input;
            Tokenizer tokenizer = new Tokenizer();
            PrintVisitor printVisitor = new PrintVisitor();
            CalcVisitor calcVisitor = new CalcVisitor();

            while (!(input = reader.readLine()).isEmpty()) {
                ParserVisitor parserVisitor = new ParserVisitor();

                List<Token> tokens = tokenizer.parse(input);
                System.out.println(printVisitor.print(tokens));

                List<Token> rpnTokens = parserVisitor.parse(tokens);
                System.out.println(printVisitor.print(rpnTokens));

                System.out.println(calcVisitor.calculate(rpnTokens));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

}
