package com.summer17.yko.kanjimashouapp;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.summer17.yko.kanjimashouapp.Utilities.BasicKanji;
import com.summer17.yko.kanjimashouapp.Utilities.NetworkUtilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity
    implements LoaderManager.LoaderCallbacks<String>{

    EditText mSearchBoxEditText;

    Button mSearchButton;

    TextView mNoResultMessage;

    ProgressBar mProgressBar;

    static final int loaderId = 22;

    boolean mErrorStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get references to the various UI elements
        mSearchBoxEditText = (EditText) findViewById(R.id.et_searchBox);
        mSearchButton = (Button) findViewById(R.id.searchButton);
        mNoResultMessage = (TextView) findViewById(R.id.tv_noResultMessage);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        LoaderManager loaderManager = getSupportLoaderManager();

        //Initialize loader
        loaderManager.initLoader(loaderId, null, this);
    }

    /**
     * Start/Restart AsyncTaskLoader to search the query
     * @param view the Button that was clicked
     */
    public void makeBasicSearchQuery(View view){
        String searchQuery = mSearchBoxEditText.getText().toString();
        Bundle args = new Bundle();
        args.putString("SEARCH_QUERY", searchQuery);

        LoaderManager loaderManager = getSupportLoaderManager();

        if(loaderManager.getLoader(loaderId) != null){
            loaderManager.restartLoader(loaderId, args, this);
        } else {
            loaderManager.initLoader(loaderId, args, this);
        }

    }

    /**
     * Creates new Loader
     * @param id of the loader being created
     * @param args the data passed to it
     * @return AsyncTaskLoader
     */
    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {

        return new AsyncTaskLoader<String>(this) {

            String mKanjiList = null;

            /**
             * Loads data from KanjiAlive API in the background
             * @return an array of Kanji as a JSONString
             */
            @Override
            public String loadInBackground() {

                String searchQuery = args.getString("SEARCH_QUERY");
                String builtUrl = NetworkUtilities.basicSearchBuildUrl(searchQuery);
                String kanjiList = null;
                try{
                    kanjiList = NetworkUtilities.basicSearchGetHttpResponse(builtUrl);
                } catch (IOException e){
                    mErrorStatus = true;
                }
                return kanjiList;

            }

            /**
             * Start retrieving data from the API
             * If the data was already loaded previously, deliver the result
             */
            @Override
            protected void onStartLoading() {
                if (args == null) return;

                if(mKanjiList != null){
                    deliverResult(mKanjiList);
                }
                else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }

            @Override
            public void deliverResult(String data) {
                mKanjiList = data;
                super.deliverResult(data);
            }
        };
    }


    /**
     * At the end of the load, starts a new Activity if the data was successfully retrieved
     *   if no results, display TextView "No Result"
     *   if connection error, display ErrorToast
     * @param loader the loader that has finished
     * @param data the output data from the loader
     */
    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

        mProgressBar.setVisibility(View.INVISIBLE);
        if(data == null){
            if(mErrorStatus){
                showErrorMessage();
            }
            mNoResultMessage.setText("No kanji found.");
            mNoResultMessage.setVisibility(View.VISIBLE);
        }
        else {
            Intent newIntent = new Intent(this, SearchResultActivity.class);
            newIntent.putExtra(Intent.EXTRA_TEXT, data);
            startActivity(newIntent);
        }
    }


    private void showErrorMessage(){
        Toast myToast = Toast.makeText(MainActivity.this, "An error has occurred. Please check your connection and try again.", Toast.LENGTH_LONG);
        myToast.show();
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        //not needed
    }
}
