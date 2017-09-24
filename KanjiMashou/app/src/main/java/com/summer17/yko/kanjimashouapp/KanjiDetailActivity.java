package com.summer17.yko.kanjimashouapp;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.SpannedString;
import android.util.Log;
import android.widget.TextView;

import com.summer17.yko.kanjimashouapp.Utilities.BasicKanji;
import com.summer17.yko.kanjimashouapp.Utilities.DetailKanji;
import com.summer17.yko.kanjimashouapp.Utilities.JsonUtilities;

import org.w3c.dom.Text;

public class KanjiDetailActivity extends AppCompatActivity {

    TextView mKanjiDisplay;
    TextView mMeaning;
    TextView mKunyomiHiragana;
    TextView mKunyomiRomaji;
    TextView mOnyomiKatakana;
    TextView mOnyomiRomaji;
    TextView mStrokes;
    TextView mRadical;
    TextView mExamples;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kanji_detail);

        Log.d("02","DETAIL_ACTIVITY : ONCREATE");
        //Retrieve JsonData from the Intent
        Intent myIntent = getIntent();
        String JsonString = myIntent.getStringExtra(Intent.EXTRA_TEXT);
        DetailKanji detailKanji = JsonUtilities.fromJsonToDetailKanji(JsonString);

        //Set all the Textview references
        mKanjiDisplay = (TextView) findViewById(R.id.tv_kanjiDisplay);
        mMeaning = (TextView) findViewById(R.id.tv_kanjiMeaning);
        mKunyomiHiragana = (TextView) findViewById(R.id.tv_kunyomihir1);
        mKunyomiRomaji = (TextView) findViewById(R.id.tv_kunyomirom1) ;
        mOnyomiKatakana = (TextView) findViewById(R.id.tv_onyomikat1) ;
        mOnyomiRomaji = (TextView) findViewById(R.id.tv_onyomirom1) ;
        mStrokes  = (TextView) findViewById(R.id.tv_kanjiStroke) ;
        mRadical  = (TextView) findViewById(R.id.tv_radical) ;
        mExamples  = (TextView) findViewById(R.id.tv_examples) ;


        DetailKanji.KanjiPart kanjiPart = detailKanji.getKanji();

        //TODO : finalize app design

        mKanjiDisplay.setText(kanjiPart.getCharacter());
        mMeaning.setText("Meaning: "+kanjiPart.getMeaning().getEnglish());
        mKunyomiHiragana.setText(kanjiPart.getKunyomi().getHiragana());
        mKunyomiRomaji.setText(kanjiPart.getKunyomi().getRomaji());
        mOnyomiKatakana.setText(kanjiPart.getOnyomi().getKatakana());
        mOnyomiRomaji.setText(kanjiPart.getOnyomi().getRomaji());
        mStrokes.setText("Strokes: "+Integer.toString(kanjiPart.getStrokes()));

        mRadical.setText("Radical: " + detailKanji.getRadical().getCharacter());

        String exampleString = "";
        for(DetailKanji.KanjiExample example : detailKanji.getExamples()){
            exampleString += example.getJapanese()+" <br> "
                            +"<i>"+ example.getMeaning() +" </i> <br>  <br> ";

        }
        mExamples.setText(format(exampleString));
    }

    @SuppressWarnings("deprecation")
    private SpannedString format(String string) {
        Spanned italicizedString;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                italicizedString = Html.fromHtml(string, Html.FROM_HTML_MODE_LEGACY);
        }
        else{
            italicizedString =  Html.fromHtml(string);
        }
        return new SpannedString(italicizedString);
    }
}
