package ir.developer_boy.mstore.cart;

public class OnCartItemCountChanged {

    private static int Count;


    public OnCartItemCountChanged(int count) {
        Count = count;
    }

    public static int getCount() {
        return Count;
    }

    public static void setCount(int count) {
        Count = count;
    }
}
