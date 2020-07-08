package ir.developer_boy.mstore.main;

import java.util.List;

import io.reactivex.Single;
import ir.developer_boy.mstore.model.Product;
import ir.developer_boy.mstore.model.api.ApiService;
import ir.developer_boy.mstore.model.providers.ApiServiceProvider;

public class MainViewModel {
    private ApiService apiService= ApiServiceProvider.getApiService();


    public Single<List<Product>> latestProducts(){
        return apiService.getProducts(0);
    }
}
