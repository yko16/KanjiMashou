package com.summer17.yko.kanjimashouapp;

import android.support.v4.app.LoaderManager;
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

import com.summer17.yko.kanjimashouapp.Utilities.BasicKanji;
import com.summer17.yko.kanjimashouapp.Utilities.NetworkUtilities;

import java.util.ArrayList;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity{

    EditText mSearchBoxEditText;

    Button mSearchButton;

    TextView mUrlDisplayTextView;

    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get references to the various UI elements
        mSearchBoxEditText = (EditText) findViewById(R.id.et_searchBox);
        mSearchButton = (Button) findViewById(R.id.searchButton);
        mUrlDisplayTextView = (TextView) findViewById(R.id.tv_urlOutput);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    public void makeBasicSearchQuery(View view){
        String searchQuery = mSearchBoxEditText.getText().toString();
        String builtUrl = NetworkUtilities.basicSearchBuildUrl(searchQuery);
        ArrayList<BasicKanji> test = NetworkUtilities.basicSearchGetHttpResponse(builtUrl);
        //mUrlDisplayTextView.setText(test.peek());
        //mUrlDisplayTextView.setText(builtUrl);
    }

}
