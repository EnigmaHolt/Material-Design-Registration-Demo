package edu.usc.haowu.codetestforpheramor.api;

import android.content.Context;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import edu.usc.haowu.codetestforpheramor.model.BaseModel;
import edu.usc.haowu.codetestforpheramor.model.User;
import io.reactivex.Observable;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author Hao Wu
 * @time 2018/06/20
 * @since
 */
public class HttpService {

    private static final String URL = "https://external.dev.pheramor.com/";

    private static volatile OkHttpClient okHttpClient;

    private Context context;

    private static HttpService instance = null;

    public static HttpService getInstance(Context context){
        if(instance == null){
            synchronized (HttpService.class){
                if(instance == null){
                    instance = new HttpService(context);
                }
            }
        }

        return instance;
    }

    public HttpService(Context context) {
        this.context = context;
    }

    private static OkHttpClient getOkHttpClient(){
        if (okHttpClient == null){
            synchronized (HttpService.class){
                if(okHttpClient == null){
                    okHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(3, TimeUnit.SECONDS)
                            .readTimeout(3,TimeUnit.SECONDS)
                            .writeTimeout(3,TimeUnit.SECONDS)
                            .build();
                }
            }
        }
        return okHttpClient;
    }

    public static<T> T create(Class<T> classz){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                            .client(getOkHttpClient())
                            .baseUrl(URL)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();
        return retrofit.create(classz);
    }

    public interface ApiService{
        @Headers("Content-Type: application/json")
        @POST("/")
        Observable<Response<BaseModel>> registerNewUser(@Body User user);
    }

}
