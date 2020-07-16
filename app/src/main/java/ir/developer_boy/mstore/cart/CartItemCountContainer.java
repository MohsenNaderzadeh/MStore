package ir.developer_boy.mstore.cart;

public class CartItemCountContainer {

    private static int count;

    public static void update(int count) {
        CartItemCountContainer.count = count;
    }

    public static int getCount() {
        return count;
    }
}
