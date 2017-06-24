package com.wasseemb.featherforreddit.SubredditJSON;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Data {

  @SerializedName("modhash") @Expose public String modhash;
  @SerializedName("children") @Expose public List<Child> children = null;
  @SerializedName("after") @Expose public String after;
  @SerializedName("before") @Expose public Object before;
}
