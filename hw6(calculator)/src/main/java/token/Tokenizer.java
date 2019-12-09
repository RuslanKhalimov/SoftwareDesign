package token;

import state.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private String input;
    private int curIndex;
    private State state;

    public List<Token> parse(String input) throws ParseException {
        this.input = input.trim();
        curIndex = 0;
        state = new StartState();
        state.setNextState(this);
        List<Token> result = new ArrayList<>();

        while (!(state instanceof EndState)) {
            result.add(state.createToken(this));
            while (!isEndOfInput() && isWhiteSpace()) {
                nextCharacter();
            }
            state.setNextState(this);
        }

        return result;
    }

    public void setState(State state) {
        this.state = state;
    }

    public boolean isEndOfInput() {
        return curIndex >= input.length();
    }

    private boolean isWhiteSpace() {
        return Character.isWhitespace(getCurrentCharacter());
    }

    public boolean isNumber() {
        return Character.isDigit(getCurrentCharacter());
    }

    public boolean isLeftBrace() {
        return getCurrentCharacter() == '(';
    }

    public boolean isRightBrace() {
        return getCurrentCharacter() == ')';
    }

    public boolean isOperation() {
        String availableSymbols = "+-*/";
        return availableSymbols.indexOf(getCurrentCharacter()) >= 0;
    }

    public char getCurrentCharacter() {
        return input.charAt(curIndex);
    }

    public void nextCharacter() {
        curIndex++;
    }

    public int getCurIndex() {
        return curIndex;
    }

}
