package com.wasseemb.featherforreddit.SubredditJSON;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Gif {

  @SerializedName("source") @Expose public Source source;
  @SerializedName("resolutions") @Expose public List<Resolution> resolutions = null;
}
