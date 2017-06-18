package com.summer17.yko.kanjimashouapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.summer17.yko.kanjimashouapp.Utilities.BasicKanji;
import com.summer17.yko.kanjimashouapp.Utilities.JsonUtilities;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {

    TextView mKanjiDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        mKanjiDisplay = (TextView) findViewById(R.id.tv_kanjiTest);

        Intent parentIntent = this.getIntent();

        String kanjiJson = parentIntent.getStringExtra(Intent.EXTRA_TEXT);

        ArrayList<BasicKanji> kanjiList = JsonUtilities.fromJsonToBasicKanji(kanjiJson);

        mKanjiDisplay.setText(kanjiJson);
        //TODO : recyclerview
    }
}
