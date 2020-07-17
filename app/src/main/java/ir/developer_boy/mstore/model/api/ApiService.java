package ir.developer_boy.mstore.model.api;

import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.Single;
import ir.developer_boy.mstore.model.AddToCartResponse;
import ir.developer_boy.mstore.model.Banner;
import ir.developer_boy.mstore.model.CartItemsCount;
import ir.developer_boy.mstore.model.CartModel;
import ir.developer_boy.mstore.model.Comment;
import ir.developer_boy.mstore.model.OrderSubmitResponse;
import ir.developer_boy.mstore.model.Product;
import ir.developer_boy.mstore.model.SuccessResponse;
import ir.developer_boy.mstore.model.Token;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @GET("product/list")
    Single<List<Product>> getProducts(@Query("sort") Integer sort);

    @GET("banner/slider")
    Single<List<Banner>> getBanners();

    @FormUrlEncoded
    @POST("auth/token")
    Single<Token> getToken(@Field("grant_type") String grant_type,
                           @Field("client_id") Integer client_id,
                           @Field("client_secret") String client_secret,
                           @Field("username") String username,
                           @Field("password") String password);


    @FormUrlEncoded
    @POST("user/register")
    Single<SuccessResponse> registerUser(@Field("email") String username,
                                         @Field("password") String password);


    @GET("comment/list")
    Single<List<Comment>> getComments(@Query("product_id") int product_id);


    @POST("comment/add")
    Single<Comment> submitNewComment(@Body JsonObject body);

    @GET("cart/list")
    Single<CartModel> getCart();

    @POST("cart/add")
    Single<AddToCartResponse> addToCart(@Body JsonObject productid);

    @POST("cart/changeCount")
    Single<AddToCartResponse> changeCartItemCount(@Body JsonObject CartItemIdAndCount);

    @POST("cart/remove")
    Single<SuccessResponse> removeCartItem(@Body JsonObject cartItemId);


    @GET("cart/count")
    Single<CartItemsCount> getCartItemCount();


    @POST("order/submit")
    Single<OrderSubmitResponse> orderSubmit(@Body JsonObject OrderInfo);
}
