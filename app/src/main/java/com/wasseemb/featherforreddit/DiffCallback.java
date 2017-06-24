package com.wasseemb.featherforreddit;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import com.wasseemb.featherforreddit.SubredditJSON.Child;
import com.wasseemb.featherforreddit.SubredditJSON.Subreddit;
import java.util.List;

/**
 * Created by Wasseem on 21/06/2017.
 */

public class DiffCallback extends DiffUtil.Callback {
  private List<Child> mOldList;
  private List<Child> mNewList;

  public DiffCallback(List<Child> oldList, List<Child> newList) {
    this.mOldList = oldList;
    this.mNewList = newList;
  }

  @Override
  public int getOldListSize() {
    return mOldList != null ? mOldList.size() : 0;
  }

  @Override
  public int getNewListSize() {
    return mNewList != null ? mNewList.size() : 0;
  }

  @Override
  public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
    return mNewList.get(newItemPosition).data.id.equals(mOldList.get(oldItemPosition).data.id);
  }

  @Override
  public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
    return mNewList.get(newItemPosition).data.id.equals(mOldList.get(oldItemPosition).data.id);
  }
  @Nullable
  @Override
  public Object getChangePayload(int oldItemPosition, int newItemPosition) {
    // Implement method if you're going to use ItemAnimator
    return super.getChangePayload(oldItemPosition, newItemPosition);
  }

}
