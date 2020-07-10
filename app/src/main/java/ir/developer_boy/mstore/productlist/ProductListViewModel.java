package ir.developer_boy.mstore.productlist;

import java.util.List;

import io.reactivex.Single;
import ir.developer_boy.mstore.base.BaseViewModel;
import ir.developer_boy.mstore.model.Product;
import ir.developer_boy.mstore.model.api.ApiService;
import ir.developer_boy.mstore.model.providers.ApiServiceProvider;

public class ProductListViewModel extends BaseViewModel {

    public Single<List<Product>> products(Integer sort)
    {
        return apiService.getProducts(sort);
    }



}
