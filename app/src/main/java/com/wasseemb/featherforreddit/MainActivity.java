package com.wasseemb.featherforreddit;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.wasseemb.featherforreddit.Adapters.SubAdapter;
import com.wasseemb.featherforreddit.SubredditJSON.Child;
import com.wasseemb.featherforreddit.SubredditJSON.Subreddit;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Callback<Subreddit> {
  List<Child> dataset = new ArrayList<>();;
  private SubAdapter adapter;
  private RecyclerView.LayoutManager layoutManager;
  private String afterResponse;

  private EndlessRecyclerOnScrollListener endlessScrollListener;
  private String subreddit = "";
  int x =1;




  @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
  @BindView(R.id.recyclerView) RecyclerView recyclerView;
  @BindView(R.id.toolbar) Toolbar toolbar;
  RestClient restClient = new RestClient();






  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    setSupportActionBar(toolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    Utils.onActivityCreateSetTheme(this);



    toolbar.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        //recyclerView.smoothScrollToPosition(0);
        recyclerView.scrollToPosition(0);
        //if(x==0) {
        //  Utils.changeToTheme(MainActivity.this, x);
        //  x = 1;
        //}
        //else{
        //  Utils.changeToTheme(MainActivity.this, x);
        //  x=0;
        //}

      }
    });
    toolbar.setOnLongClickListener(new View.OnLongClickListener() {
      @Override public boolean onLongClick(View v) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(MainActivity.this);
        View mView = layoutInflaterAndroid.inflate(R.layout.subreddit_material_box, null);
        AlertDialog.Builder alertDialogBuilderUserInput =
            new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilderUserInput.setView(mView);

        final EditText userInputDialogEditText =
            (EditText) mView.findViewById(R.id.userInputDialog);
        alertDialogBuilderUserInput.setCancelable(true)
            .setPositiveButton("Open", new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialogBox, int id) {
                // ToDo get user input here
                subreddit = userInputDialogEditText.getText().toString();
                loadFirstTime(subreddit);
              }
            });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
        return false;
      }
    });

    // use this setting to improve performance if you know that changes
    // in content do not change the layout size of the RecyclerView
    recyclerView.setHasFixedSize(true);

    // use a linear layout manager
    layoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(layoutManager);


    loadFrontPage();


    endlessScrollListener =
        new EndlessRecyclerOnScrollListener((LinearLayoutManager) layoutManager) {
          @Override public void onLoadMore() {
            if (subreddit == "") {
              loadMoreFrontPage();
            } else {
              loadMoreItems(subreddit);
            }
          }
        };
    recyclerView.addOnScrollListener(endlessScrollListener);
    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {
        //dataset.clear();
        //adapter.notifyDataSetChanged();
        if (subreddit == "") {
          loadFrontPage();
        } else {
          loadFirstTime(subreddit);
        }
        swipeRefreshLayout.setRefreshing(false);
      }
    });
  }

  public void loadFirstTime(String subreddit) {
    if (dataset != null) dataset.clear();
    restClient.OpenNewSub(subreddit).enqueue(new Callback<Subreddit>() {
      @Override public void onResponse(Call<Subreddit> call, Response<Subreddit> response) {
        dataset.addAll(response.body().data.children);
        //((SubAdapter)adapter).setDataset(dataset);
        afterResponse = response.body().data.after;
        adapter.notifyDataSetChanged();
        //adapter.updateList(dataset);

      }

      @Override public void onFailure(Call<Subreddit> call, Throwable t) {

      }
    });
   // adapter.notifyDataSetChanged();

  }

  private void loadMoreItems(String subreddit) {
    restClient.OpenNewSub(subreddit, 25, afterResponse).enqueue(this);
  }

  private void loadMoreFrontPage() {
    // HERE YOU LOAD the next batch of items
    restClient.OpenFrontPage(25, afterResponse).enqueue(this);
  }

  @Override public void onResponse(Call<Subreddit> call, Response<Subreddit> response) {

    dataset.addAll(response.body().data.children);
    //((SubAdapter)adapter).setDataset(dataset);
    afterResponse = response.body().data.after;
    //adapter.notifyDataSetChanged();
    adapter.updateList(dataset);

  }

  @Override public void onFailure(Call<Subreddit> call, Throwable throwable) {
    Log.d("Tag", throwable.getMessage());
  }

  public void loadFrontPage() {
    restClient.OpenFrontPage().enqueue(new Callback<Subreddit>() {
      @Override public void onResponse(Call<Subreddit> call, Response<Subreddit> response) {
        dataset.addAll(response.body().data.children);
        //((SubAdapter)adapter).setDataset(dataset);

        afterResponse = response.body().data.after;
        //adapter.notifyDataSetChanged();
        //adapter.updateList(dataset);
        adapter = new SubAdapter(dataset,getApplicationContext());
        recyclerView.setAdapter(adapter);

      }

      @Override public void onFailure(Call<Subreddit> call, Throwable throwable) {
        Log.d("Tag", throwable.getMessage());
      }
    });
  }

}

