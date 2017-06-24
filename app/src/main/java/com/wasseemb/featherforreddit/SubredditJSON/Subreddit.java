package com.wasseemb.featherforreddit.SubredditJSON;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subreddit {

  @SerializedName("kind") @Expose public String kind;
  @SerializedName("data") @Expose public Data data;
}
