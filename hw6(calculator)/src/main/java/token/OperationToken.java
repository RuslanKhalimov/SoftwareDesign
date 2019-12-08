package token;

import visitor.TokenVisitor;

import java.util.Objects;

public class OperationToken implements Token {
    private final TokenType operationType;

    public OperationToken(TokenType operationType) {
        this.operationType = operationType;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public TokenType getTokenType() {
        return operationType;
    }

    public int evaluate(int a, int b) {
        if (operationType.equals(TokenType.PLUS)) {
            return a + b;
        }
        if (operationType.equals(TokenType.MINUS)) {
            return a - b;
        }
        if (operationType.equals(TokenType.MUL)) {
            return a * b;
        }
        if (operationType.equals(TokenType.DIV)) {
            return a / b;
        }
        throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof OperationToken)) return false;
        OperationToken operationToken = (OperationToken) other;
        return Objects.equals(operationType, operationToken.operationType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationType);
    }

    @Override
    public String toString() {
        return operationType.toString();
    }

}
