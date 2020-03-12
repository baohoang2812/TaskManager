package baohg.taskmanager.baohg.baohg.apis;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient{
    public static final String BASE_URL = "http://192.168.0.104:45457";

    public static Retrofit retrofit = null;

    public static Retrofit getTaskManagerClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
