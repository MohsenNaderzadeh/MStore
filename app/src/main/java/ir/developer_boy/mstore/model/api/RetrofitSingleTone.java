package ir.developer_boy.mstore.model.api;

import ir.developer_boy.mstore.auth.TokenContainer;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleTone {
    private static Retrofit retrofit;




    public static Retrofit getRetrofit() {
        if(retrofit==null)
        {
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .addInterceptor(chain -> {
                        Request oldrequest = chain.request();
                        Request.Builder newRequest = oldrequest.newBuilder();
                        if (TokenContainer.getToken() != null) {
                            newRequest.addHeader("Authorization", "Bearer " + TokenContainer.getToken());
                        }
                        newRequest.addHeader("Accept", "application/json");
                        newRequest.method(oldrequest.method(), oldrequest.body());
                        return chain.proceed(newRequest.build());
                    })
                    .build();
            retrofit=new Retrofit.Builder()
                    .baseUrl("http://expertdevelopers.ir/api/v1/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
       return retrofit;
    }

    private RetrofitSingleTone(){

    }
}
