package ir.developer_boy.mstore.model.providers;

import ir.developer_boy.mstore.model.api.ApiService;
import ir.developer_boy.mstore.model.api.RetrofitSingleTone;

public class ApiServiceProvider {

    private static ApiService apiService;


    public static ApiService getApiService() {

        if(apiService==null)
        {
            apiService=RetrofitSingleTone.getRetrofit().create(ApiService.class);
        }
        return apiService;
    }
}
