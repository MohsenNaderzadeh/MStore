package ir.developer_boy.mstore.base;

import ir.developer_boy.mstore.model.api.ApiService;
import ir.developer_boy.mstore.model.providers.ApiServiceProvider;

public class BaseViewModel {
    protected ApiService apiService= ApiServiceProvider.getApiService();


}
