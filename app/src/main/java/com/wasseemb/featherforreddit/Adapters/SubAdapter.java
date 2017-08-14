package com.wasseemb.featherforreddit.Adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import com.wasseemb.featherforreddit.DetailActivity;
import com.wasseemb.featherforreddit.DiffCallback;
import com.wasseemb.featherforreddit.ImageViewActivity;
import com.wasseemb.featherforreddit.R;
import com.wasseemb.featherforreddit.RoundedImageView;
import com.wasseemb.featherforreddit.SubredditJSON.Child;
import com.wasseemb.featherforreddit.SubredditJSON.Preview;
import java.util.List;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Wasseem on 08/06/2017.
 */

public class SubAdapter extends RecyclerView.Adapter<SubAdapter.ViewHolder> {
  RecyclerView mRecyclerView;
  private List<Child> dataset;
  private Context context;
  public String id;
  public View viewToHide;


  // Provide a suitable constructor (depends on the kind of dataset)
  public SubAdapter(List<Child> myDataset, Context myContext) {
    context = myContext;
    dataset = myDataset;
  }

  // Create new views (invoked by the layout manager)
  @Override public SubAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    // create a new view

    View itemView =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.subreddit_itemvv, parent, false);



    // set the view's size, margins, paddings and layout parameters

    return new SubAdapter.ViewHolder(itemView);
  }



  @Override public void onAttachedToRecyclerView(RecyclerView recyclerView) {
    super.onAttachedToRecyclerView(recyclerView);
    mRecyclerView = recyclerView;
  }



  void run(String url,Callback callback) {
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
        .url(url)
        .build();

      client.newCall(request).enqueue(callback);
  }
  // Replace the contents of a view (invoked by the layout manager)
  @Override public void onBindViewHolder(final ViewHolder holder, int position) {
    resetVisibility(holder);
    //Instead of accessing it everytime
    final Child mChild = dataset.get(position);

    holder.mTitleTextView.setText(mChild.data.title);
    holder.mPointCount.setText(mChild.data.ups + " points");
    holder.mCommentCount.setText(mChild.data.numComments + " comments");
    Preview preview = mChild.data.preview;
    if (preview != null) {
      if (preview.images.get(0).variants.gif != null) {
        //                Log.d("Tag",preview.images.get(0).variants.gif.source.url);
        holder.mImageType.setImageResource(R.drawable.ic_gif_black_24dp);
        holder.mImageType.setColorFilter(ContextCompat.getColor(context, R.color.whiteFont));
        Picasso.with(context).load(thumbL(mChild.data.url)).into(holder.mImageView);

      } else {
        Uri uri = Uri.parse(mChild.data.preview.images.get(0).source.url);
        Picasso.with(context).load(uri).into(holder.mImageView);
        holder.mImageType.setImageResource(R.drawable.ic_play_arrow_black_24dp);
        holder.mImageType.setColorFilter(ContextCompat.getColor(context, R.color.whiteFont));
      }
      holder.mInfoTextView.setVisibility(View.GONE);
    } else {
      holder.mRelativeLayout.setVisibility(View.GONE);
      holder.mInfoTextView.setText(dataset.get(position).data.selftext);
    }
    if (position == dataset.size() - 1) {
      viewToHide = holder.mProgressBar;
      holder.mProgressBar.setVisibility(View.VISIBLE);
    }

    holder.mImageView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), ImageViewActivity.class);
        intent.setAction(mChild.data.preview.images.get(0).source.url);
        ActivityOptions options = ActivityOptions
            .makeSceneTransitionAnimation((Activity)v.getContext(), holder.mImageView, "robot_trans");
        // start the new activity
        v.getContext().startActivity(intent, options.toBundle());      }
    });

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent mIntent = new Intent(v.getContext(), DetailActivity.class);
        mIntent.setAction(mChild.data.preview.images.get(0).source.url);
        ActivityOptions options = ActivityOptions
            .makeSceneTransitionAnimation((Activity)v.getContext(), holder.cardView, "robot_trans");
        // start the new activity
        v.getContext().startActivity(mIntent, options.toBundle());
      }
    });


  }
  public void updateList(List<Child> newList) {
    List oldList = this.dataset;
    DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffCallback(oldList, newList));
    this.dataset = newList;
    result.dispatchUpdatesTo(this);
    viewToHide.setVisibility(View.GONE);
  }


  public void resetVisibility(ViewHolder holder) {

    //Issue with recycler view populating wrong
    holder.mImageView.setVisibility(View.VISIBLE);
    holder.mInfoTextView.setVisibility(View.VISIBLE);
    holder.mRelativeLayout.setVisibility(View.VISIBLE);
    holder.mImageType.setVisibility(View.VISIBLE);
    holder.mProgressBar.setVisibility(View.GONE);
  }


  private String thumbL(String text) {
    if (text.contains("redd.it")) return text;
    return text.replace(".gif", "h.gif");
  }



  //@Override public long getItemId(int position) {
  //  return position;
  //}

  // Return the size of your dataset (invoked by the layout manager)
  @Override public int getItemCount() {
    return dataset.size();
  }

  // Provide a reference to the views for each data item
  // Complex data items may need more than one view per item, and
  // you provide access to all the views for a data item in a view holder
  public static class ViewHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case

    @BindView(R.id.title_text) TextView mTitleTextView;
    @BindView(R.id.info_text) TextView mInfoTextView;
    @BindView(R.id.pointCount) TextView mPointCount;
    @BindView(R.id.commentCount) TextView mCommentCount;
    @BindView(R.id.info_image) RoundedImageView mImageView;
    @BindView(R.id.image_type) ImageView mImageType;
    @BindView(R.id.relativeLayout) RelativeLayout mRelativeLayout;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;
    @BindView(R.id.card_view) CardView cardView;

    public ViewHolder(View v) {
      super(v);
      ButterKnife.bind(this,v);

    }

  }



}
