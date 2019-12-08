package token;

import state.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private String input;
    private int curIndex;

    public List<Token> parse(String input) throws ParseException {
        this.input = input;
        curIndex = 0;
        State curState;
        List<Token> result = new ArrayList<>();

        while (!isEndOfInput()) {
            if (isWhiteSpace()) {
                nextCharacter();
                continue;
            }
            if (isBrace()) {
                curState = new BraceState(this);
            } else if (isNumber()) {
                curState = new NumberState(this);
            } else if (isOperationOrBrace()) {
                curState = new OperationState(this);
            } else {
                throw new ParseException("Unexpected symbol '" + getCurrentCharacter() + "'", curIndex);
            }

            result.add(curState.createToken());
        }

        return result;
    }

    public boolean isEndOfInput() {
        return curIndex >= input.length();
    }

    private boolean isWhiteSpace() {
        return Character.isWhitespace(getCurrentCharacter());
    }

    private boolean isBrace() {
        String availableSymbols = "()";
        return availableSymbols.indexOf(getCurrentCharacter()) >= 0;
    }

    private boolean isNumber() {
        return Character.isDigit(getCurrentCharacter());
    }

    private boolean isOperationOrBrace() {
        String availableSymbols = "+-*/";
        return availableSymbols.indexOf(getCurrentCharacter()) >= 0;
    }

    public char getCurrentCharacter() {
        return input.charAt(curIndex);
    }

    public void nextCharacter() {
        curIndex++;
    }

}
