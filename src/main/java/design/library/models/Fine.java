package design.library.models;

public class Fine {

    private Issuance issuance;
    private int amount;

    public Fine(Issuance issuance, int amount) {
        this.issuance = issuance;
        this.amount = amount;
    }

    public Issuance getIssuance() {
        return issuance;
    }

    public int getAmount() {
        return amount;
    }

}
