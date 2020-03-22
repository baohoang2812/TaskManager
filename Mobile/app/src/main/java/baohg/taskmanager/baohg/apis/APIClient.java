package baohg.taskmanager.baohg.apis;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient{
    public static final String BASE_URL = "http://192.168.0.104:45455/";

    public static Retrofit retrofit = null;

    public static Retrofit getTaskManagerClient(){
        if(retrofit == null){
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
