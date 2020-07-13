package ir.developer_boy.mstore.exception;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.HttpException;

public class ExceptionMessageFactory {
    private ExceptionMessageFactory() {
    }
    public static String getMessage(Throwable throwable){
        if(throwable instanceof HttpException)
        {
            switch (((HttpException) throwable).code())
            {
                case 401:
                case 403:
                    EventBus.getDefault().post(new UnAthuorized());
                    break;
                case 400:
                case 422:
                    Gson gson=new Gson();
                    try {
                        MsHttpException msHttpException=gson.fromJson(((HttpException) throwable).response().errorBody().string(),MsHttpException.class);
                        return msHttpException.getMessage();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    return "اختلال در دریافت اطلاعات";


            }
        }
        return "خطای نامشخص";
    }
}
