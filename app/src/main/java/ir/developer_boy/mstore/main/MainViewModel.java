package ir.developer_boy.mstore.main;

import java.util.List;

import io.reactivex.Single;
import ir.developer_boy.mstore.base.BaseViewModel;
import ir.developer_boy.mstore.model.Banner;
import ir.developer_boy.mstore.model.Product;
import ir.developer_boy.mstore.model.api.ApiService;
import ir.developer_boy.mstore.model.providers.ApiServiceProvider;

public class MainViewModel extends BaseViewModel {


    public Single<List<Product>> latestProducts(){
        return apiService.getProducts(Product.SORT_LATEST);
    }

    public Single<List<Product>> popularProducts(){
        return  apiService.getProducts(Product.SORT_POPULAR);
    }
    public Single<List<Product>> productsPriceHighToLow(){
        return  apiService.getProducts(Product.SORT_PRICE_HIGH_TO_LOW);
    }
    public Single<List<Product>> productsPriceLowToHigh(){
        return  apiService.getProducts(Product.SORT_PRICE_LOW_TO_HIGH);
    }
    public Single<List<Banner>> banners(){
        return  apiService.getBanners();
    }

}
