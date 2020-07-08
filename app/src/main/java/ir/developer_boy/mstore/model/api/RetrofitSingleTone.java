package ir.developer_boy.mstore.model.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleTone {
    private static Retrofit retrofit;




    public static Retrofit getRetrofit() {
        if(retrofit==null)
        {
            retrofit=new Retrofit.Builder()
                    .baseUrl("http://expertdevelopers.ir/api/v1/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
       return retrofit;
    }
}
