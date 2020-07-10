package ir.developer_boy.mstore.model.api;

import java.util.List;

import io.reactivex.Single;
import ir.developer_boy.mstore.model.Banner;
import ir.developer_boy.mstore.model.Product;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("product/list")
    Single<List<Product>> getProducts(@Query("sort") Integer sort);

    @GET("banner/slider")
    Single<List<Banner>> getBanners();
}
