package com.summer17.yko.kanjimashouapp;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.summer17.yko.kanjimashouapp.Utilities.BasicKanji;
import com.summer17.yko.kanjimashouapp.Utilities.JsonUtilities;
import com.summer17.yko.kanjimashouapp.Utilities.NetworkUtilities;

import java.io.IOException;
import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity
        implements SearchAdapter.SearchAdapterClickHandler , LoaderManager.LoaderCallbacks<String>{


    static int mLoaderId = 27;

    SearchAdapter mSearchAdapter;

    RecyclerView mRecycerView;

    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        //Get references of the different views of the activity
        mSearchAdapter = new SearchAdapter(this);
        mRecycerView = (RecyclerView) findViewById(R.id.rv_results);
        mProgressBar = (ProgressBar) findViewById(R.id.detailProgressBar);


        //Retrieve the JSON data from parent activity
        //Transform it into Arraylist of Basic Kanji
        Intent parentIntent = this.getIntent();
        String kanjiJson = parentIntent.getStringExtra(Intent.EXTRA_TEXT);
        ArrayList<BasicKanji> kanjiList = JsonUtilities.fromJsonToBasicKanji(kanjiJson);

        //Store it in data holder of the SearchAdapter
        mSearchAdapter.setKanjiList(kanjiList);

        //Set up Recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        mRecycerView.setLayoutManager(layoutManager);
        mRecycerView.setHasFixedSize(true);
        mRecycerView.setAdapter(mSearchAdapter);

        //Initialize Loader
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(mLoaderId, null, this);

    }

    @Override
    public void onListItemClicked(String clickedKanji) {
        //TODO : new ASyncTaskLoader for kanjidetails
        searchKanjiDetails(clickedKanji);
    }

    public void searchKanjiDetails(String query){
        mProgressBar.setVisibility(View.VISIBLE);
        LoaderManager loaderManager = getSupportLoaderManager();
        Bundle args = new Bundle();
        args.putString("DETAIL_QUERY", query);
        //loaderManager.initLoader(mLoaderId,args,this);
        Loader<String> githubSearchLoader = loaderManager.getLoader(mLoaderId);
        if (githubSearchLoader == null) {
            loaderManager.initLoader(mLoaderId, args, this);
        } else {
            loaderManager.restartLoader(mLoaderId, args, this);
        }
    }

    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<String>(this) {

            String mKanjiDetails;

            @Override
            public String loadInBackground() {
                String detailQuery = args.getString("DETAIL_QUERY");
                String builtUrl = NetworkUtilities.detailQueryBuildUrl(detailQuery);
                String queryResult = null;
                try{
                    queryResult = NetworkUtilities.detailQueryGetHttpResponse(builtUrl);
                }catch (IOException e){
                    //TODO : handle null result
                    Log.d("03","DETAIL_ACTIVITY : error");
                }
                return queryResult;
            }

            @Override
            protected void onStartLoading() {
                if (args == null) return;

                if(mKanjiDetails != null){
                    deliverResult(mKanjiDetails);
                }
                else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }
            @Override
            public void deliverResult(String data) {
                mKanjiDetails = data;
                super.deliverResult(data);
            }
        };
    }



    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

        mProgressBar.setVisibility(View.INVISIBLE);

        if(data == null){
            Log.d("04","RESULT_ACTIVITY : NO DATA");
            //Toast myToast = Toast.makeText(this, "An error occured, please check your connection and try again.", Toast.LENGTH_LONG);
            return;
        }
        Log.d("01","RESULT_ACTIVITY : LOADFINISHED");
        Intent newIntent = new Intent(this, KanjiDetailActivity.class);
        newIntent.putExtra(Intent.EXTRA_TEXT, data);
        startActivity(newIntent);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        //not needed
    }
}
