package chat.hackathon.gupshup.io.groupmessaging;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ApiClientImpl {
    private static ApiClient instance;
    public static ApiClient getApiClient() {

        if (instance != null) {
            return instance;
        }

        String URL = "www.gupshup.io/dummyUrl";

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        retrofitBuilder.baseUrl(URL);
        Retrofit rxRetrofit2 = retrofitBuilder.build();
        return instance = rxRetrofit2.create(ApiClient.class);
    }
}
