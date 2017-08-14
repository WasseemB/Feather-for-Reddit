package com.wasseemb.featherforreddit;

/**
 * Created by Wasseem on 14/08/2017.
 */

import com.wasseemb.featherforreddit.SubredditJSON.Subreddit;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Wasseem on 07/06/2017.
 */

public interface ApiService {
  @GET("r/{subreddit}/.json") Call<Subreddit> OpenNewSub(@Path("subreddit") String subreddit);

  @GET("r/{subreddit}/.json") Call<Subreddit> OpenNewSub(@Path("subreddit") String subreddit,
      @Query("limit") int limit, @Query("after") String after);

  @GET(".json") Call<Subreddit> OpenFrontPage();

  @GET(".json") Call<Subreddit> OpenFrontPage(@Query("limit") int limit,
      @Query("after") String after);
}