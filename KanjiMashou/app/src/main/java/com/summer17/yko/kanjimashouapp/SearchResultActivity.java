package com.summer17.yko.kanjimashouapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.summer17.yko.kanjimashouapp.Utilities.BasicKanji;
import com.summer17.yko.kanjimashouapp.Utilities.JsonUtilities;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity
        implements SearchAdapter.SearchAdapterClickHandler{

    SearchAdapter mSearchAdapter;

    RecyclerView mRecycerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        //Get references of the different views of the activity
        mSearchAdapter = new SearchAdapter(this);
        mRecycerView = (RecyclerView) findViewById(R.id.rv_results);


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

    }

    @Override
    public void onListItemClicked(String clickedKanji) {
        Toast myToast = Toast.makeText(this, clickedKanji, Toast.LENGTH_LONG);
        myToast.show();
    }
}
