package ir.developer_boy.mstore.shipping;

public enum PayMentMethod {
    ONLINE_METHOD("online"), CASH_ON_DELIVERY("cash_on_delivery");

    private String value;

    PayMentMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
