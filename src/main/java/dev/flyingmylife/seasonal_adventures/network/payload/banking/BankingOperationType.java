package dev.flyingmylife.seasonal_adventures.network.payload.banking;

public enum BankingOperationType {
    WITHDRAW("withdraw"),
    REPLENISH("replenish");

    public final String id;
    BankingOperationType (String id) {
        this.id = id;
    }
}
