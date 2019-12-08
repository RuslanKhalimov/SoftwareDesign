package visitor;

import token.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class ParserVisitor implements TokenVisitor {
    private final List<Token> rpnTokens = new ArrayList<>();
    private final Deque<Token> stack = new ArrayDeque<>();

    public List<Token> parse(List<Token> tokens) {
        tokens.forEach(token -> token.accept(this));

        while (!stack.isEmpty()) {
            rpnTokens.add(stack.pollLast());
        }

        return new ArrayList<>(rpnTokens);
    }

    @Override
    public void visit(NumberToken token) {
        rpnTokens.add(token);
    }

    @Override
    public void visit(BraceToken token) {
        if (token.getTokenType().equals(TokenType.LEFT_BRACE)) {
            stack.add(token);
        } else {
            if (stack.isEmpty()) {
                throw new IllegalStateException();
            }
            Token nextToken = stack.pollLast();
            while (!stack.isEmpty() && !nextToken.getTokenType().equals(TokenType.LEFT_BRACE)) {
                rpnTokens.add(nextToken);
                nextToken = stack.pollLast();
            }
        }
    }

    @Override
    public void visit(OperationToken token) {
        if (!stack.isEmpty()) {
            Token nextToken = stack.peekLast();
            while (!stack.isEmpty() && token.getTokenType().getPriority() <= nextToken.getTokenType().getPriority()) {
                rpnTokens.add(stack.pollLast());
                nextToken = stack.peekLast();
            }
        }
        stack.add(token);
    }

}
