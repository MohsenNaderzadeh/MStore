package ir.developer_boy.mstore.model.api;

import java.util.List;

import io.reactivex.Single;
import ir.developer_boy.mstore.model.Banner;
import ir.developer_boy.mstore.model.Product;
import ir.developer_boy.mstore.model.SuccessResponse;
import ir.developer_boy.mstore.model.Token;
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
}
