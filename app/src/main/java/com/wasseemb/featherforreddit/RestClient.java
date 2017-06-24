package com.wasseemb.featherforreddit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wasseemb.featherforreddit.SubredditJSON.Subreddit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Wasseem on 18/06/2017.
 */

public class RestClient {

  private static final String BASE_URL = "http://reddit.com/";
  private static RestClient instance = new RestClient();
  private ApiService apiService;

  public RestClient() {
    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    Retrofit.Builder builder = new Retrofit.Builder()
        //.client(GetClient())
        .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson));
    Retrofit retrofit = builder.build();
    apiService = retrofit.create(ApiService.class);
  }

  public static RestClient getInstance() {
    return instance;
  }

  public static OkHttpClient GetClient() {
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    return httpClient.addInterceptor(logging).build();
  }

  public ApiService getApiService() {
    return apiService;
  }

  public Call<Subreddit> OpenFrontPage() {
    return apiService.OpenFrontPage();
  }

  public Call<Subreddit> OpenFrontPage(int limit, String after) {
    return apiService.OpenFrontPage(limit, after);
  }

  public Call<Subreddit> OpenNewSub(String sub) {
    return apiService.OpenNewSub(sub);
  }

  public Call<Subreddit> OpenNewSub(String sub, int limit, String after) {
    return apiService.OpenNewSub(sub, limit, after);
  }
}