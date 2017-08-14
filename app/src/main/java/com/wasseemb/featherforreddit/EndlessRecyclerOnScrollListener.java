package com.wasseemb.featherforreddit;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Wasseem on 16/06/2017.
 */

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
  private boolean loading = true;
  private int previousTotal = 0;
  private LinearLayoutManager mLinearLayoutManager;

  public EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
    mLinearLayoutManager = linearLayoutManager;
  }

  // Concrete classes should implement the Loading of more data entries
  public abstract void onLoadMore();

  @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
    super.onScrollStateChanged(recyclerView, newState);
  }

  @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
    super.onScrolled(recyclerView, dx, dy);

    if (dy <= 0) return;
    int visibleItemCount = recyclerView.getChildCount();
    int totalItemCount = mLinearLayoutManager.getItemCount();
    int firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

    if (loading) {
      if (totalItemCount > previousTotal) {
        loading = false;
        previousTotal = totalItemCount;
      }
    }
    if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem)) {
      // End has been reached
      // Do something
      onLoadMore();
      loading = true;
    }
  }
}