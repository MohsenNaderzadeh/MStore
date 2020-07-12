package ir.developer_boy.mstore.auth;

public class TokenContainer {


    private static String token;

    public static void updateToken(String token) {
        TokenContainer.token = token;
    }

    public static String getToken() {
        return token;
    }
}
