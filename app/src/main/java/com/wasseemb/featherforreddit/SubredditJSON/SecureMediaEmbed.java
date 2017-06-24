package com.wasseemb.featherforreddit.SubredditJSON;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SecureMediaEmbed {

  @SerializedName("content") @Expose public String content;
  @SerializedName("width") @Expose public Integer width;
  @SerializedName("scrolling") @Expose public Boolean scrolling;
  @SerializedName("height") @Expose public Integer height;
}
