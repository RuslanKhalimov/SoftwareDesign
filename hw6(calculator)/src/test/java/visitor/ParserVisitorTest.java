package visitor;

import org.junit.Before;
import org.junit.Test;
import token.BraceToken;
import token.NumberToken;
import token.OperationToken;
import token.TokenType;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class ParserVisitorTest {
    private ParserVisitor parserVisitor;

    @Before
    public void setUp() {
        parserVisitor = new ParserVisitor();
    }

    @Test
    public void testNumber() {
        assertThat(parserVisitor.parse(Collections.singletonList(new NumberToken(1))))
                .containsExactly(new NumberToken(1));
    }

    @Test
    public void testOperations() {
        assertThat(parserVisitor.parse(Arrays.asList(
                new NumberToken(1),
                new OperationToken(TokenType.PLUS),
                new NumberToken(2)
        ))).containsExactly(
                new NumberToken(1),
                new NumberToken(2),
                new OperationToken(TokenType.PLUS)
        );
    }

    @Test
    public void testOperationsPriority() {
        assertThat(parserVisitor.parse(Arrays.asList(
                new NumberToken(1),
                new OperationToken(TokenType.PLUS),
                new NumberToken(2),
                new OperationToken(TokenType.MUL),
                new NumberToken(3)
        ))).containsExactly(
                new NumberToken(1),
                new NumberToken(2),
                new NumberToken(3),
                new OperationToken(TokenType.MUL),
                new OperationToken(TokenType.PLUS)
        );
    }

    @Test
    public void testBraces() {
        assertThat(parserVisitor.parse(Arrays.asList(
                new BraceToken(TokenType.LEFT_BRACE),
                new NumberToken(1),
                new OperationToken(TokenType.PLUS),
                new NumberToken(2),
                new BraceToken(TokenType.RIGHT_BRACE),
                new OperationToken(TokenType.MUL),
                new NumberToken(3)
        ))).containsExactly(
                new NumberToken(1),
                new NumberToken(2),
                new OperationToken(TokenType.PLUS),
                new NumberToken(3),
                new OperationToken(TokenType.MUL)
        );
    }

}
