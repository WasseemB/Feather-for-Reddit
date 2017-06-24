package com.wasseemb.featherforreddit.SubredditJSON;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Image {

  @SerializedName("source") @Expose public Source source;
  @SerializedName("resolutions") @Expose public List<Resolution> resolutions = null;
  @SerializedName("variants") @Expose public Variants variants;
  @SerializedName("id") @Expose public String id;
}
