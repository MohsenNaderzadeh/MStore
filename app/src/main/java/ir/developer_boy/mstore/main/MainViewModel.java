package ir.developer_boy.mstore.main;

import ir.developer_boy.mstore.model.api.ApiService;
import ir.developer_boy.mstore.model.providers.ApiServiceProvider;

public class MainViewModel {
    private ApiService apiService= ApiServiceProvider.getApiService();

}
