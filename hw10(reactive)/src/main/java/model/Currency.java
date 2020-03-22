package model;

public enum Currency {
    USD,
    EUR,
    RUB;

    private double getMultiplier() {
        switch (this) {
            case USD:
                return 1;
            case EUR:
                return 0.94;
            case RUB:
                return 78.04;
            default:
                return 1;
        }
    }

    public double getMultiplier(Currency otherCurrency) {
        return otherCurrency.getMultiplier() / getMultiplier();
    }
}
