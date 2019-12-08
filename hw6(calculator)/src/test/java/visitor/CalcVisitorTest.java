package visitor;

import org.junit.Test;
import token.BraceToken;
import token.NumberToken;
import token.OperationToken;
import token.TokenType;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class CalcVisitorTest {
    private final CalcVisitor calcVisitor = new CalcVisitor();

    @Test
    public void testNumber() {
        assertThat(calcVisitor.calculate(Collections.singletonList(new NumberToken(42)))).isEqualTo(42);
    }

    @Test
    public void testOperations() {
        assertThat(calcVisitor.calculate(Arrays.asList( // 5 + 4 - 3 * 2 / 1
                new NumberToken(5),
                new NumberToken(4),
                new OperationToken(TokenType.PLUS),
                new NumberToken(3),
                new NumberToken(2),
                new OperationToken(TokenType.MUL),
                new NumberToken(1),
                new OperationToken(TokenType.DIV),
                new OperationToken(TokenType.MINUS)
        ))).isEqualTo(3);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testBraces() {
        calcVisitor.calculate(Collections.singletonList(new BraceToken(TokenType.LEFT_BRACE)));
    }

    @Test(expected = IllegalStateException.class)
    public void testTooManyTokens() {
        calcVisitor.calculate(Arrays.asList(
                new NumberToken(1),
                new NumberToken(2)
        ));
    }

    @Test(expected = IllegalStateException.class)
    public void testNotEnoughTokens() {
        calcVisitor.calculate(Arrays.asList(
                new NumberToken(1),
                new OperationToken(TokenType.PLUS)
        ));
    }

}
