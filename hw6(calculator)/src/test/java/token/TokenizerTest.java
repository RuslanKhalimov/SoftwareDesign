package token;

import org.junit.Test;

import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;

public class TokenizerTest {
    private final Tokenizer tokenizer = new Tokenizer();

    @Test
    public void testNumber() throws ParseException {
        assertThat(tokenizer.parse("11213"))
                .containsExactly(
                        new NumberToken(11213)
                );
    }

    @Test
    public void testOperations() throws ParseException {
        assertThat(tokenizer.parse("1 + 2 * 3 - 4 / 5"))
                .containsExactly(
                        new NumberToken(1),
                        new OperationToken(TokenType.PLUS),
                        new NumberToken(2),
                        new OperationToken(TokenType.MUL),
                        new NumberToken(3),
                        new OperationToken(TokenType.MINUS),
                        new NumberToken(4),
                        new OperationToken(TokenType.DIV),
                        new NumberToken(5)
                        );
    }

    @Test
    public void testBraces() throws ParseException {
        assertThat(tokenizer.parse("(1 + 2)"))
                .containsExactly(
                        new BraceToken(TokenType.LEFT_BRACE),
                        new NumberToken(1),
                        new OperationToken(TokenType.PLUS),
                        new NumberToken(2),
                        new BraceToken(TokenType.RIGHT_BRACE)
                );
    }

    @Test(expected = ParseException.class)
    public void testIncorrectInput() throws ParseException {
        tokenizer.parse("2!");
    }

}
