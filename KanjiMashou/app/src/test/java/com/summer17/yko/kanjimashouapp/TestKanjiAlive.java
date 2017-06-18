package com.summer17.yko.kanjimashouapp;

import com.summer17.yko.kanjimashouapp.Utilities.BasicKanji;
import com.summer17.yko.kanjimashouapp.Utilities.JsonUtilities;
import com.summer17.yko.kanjimashouapp.Utilities.NetworkUtilities;

import static org.junit.Assert.assertTrue;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;


/**
 * Created by Yko on 6/8/2017.
 */

public class TestKanjiAlive {

    String mData;
    BasicKanji mTemplateKanji;


    /**
     * mData : mock input data to make request to KanjiAlive API
     * mTemplateKanji : expected output of the KanjiAlive API
     */
    @Before
    public void setup(){
        mData = "cold";
        //mTemplateKanji  = new BasicKanji("寒", 12, "⼧", 3, 48);
        mTemplateKanji  = new BasicKanji("冷", 7, "⼎", 2, 19);
        //mTemplateKanji  = new BasicKanji("雨", 8, "⾬", 8, 210);
    }


    /**
     * test : basicSearchBuildUrl & basicSearchGetHttpResponse
     */
    @Test (expected = IOException.class)
    public void testBasicSearchRequest() throws IOException
    {

        String url = NetworkUtilities.basicSearchBuildUrl(mData);
        String kanjiJsonString  = NetworkUtilities.basicSearchGetHttpResponse(url);


        BasicKanji myObject = JsonUtilities.fromJsonToBasicKanji(kanjiJsonString).get(1);

        // JSONObject Output from KanjiAlive
        // isolating each property to test them

        String kanjiCharacter = myObject.getKanji().getCharacter();
        int kanjiStroke = myObject.getKanji().getStroke();
        String radCharacter = myObject.getRadical().getCharacter();
        int radStroke=myObject.getRadical().getStroke();
        int radOrder = myObject.getRadical().getOrder();

        // Test Data
        String test_kanjiCharacter = mTemplateKanji.getKanji().getCharacter();
        int test_kanjiStroke = mTemplateKanji.getKanji().getStroke();
        String test_radCharacter = mTemplateKanji.getRadical().getCharacter();
        int test_radStroke = mTemplateKanji.getRadical().getStroke();
        int test_radOrder = mTemplateKanji.getRadical().getOrder();

        assertTrue(Character.UnicodeBlock.of(kanjiCharacter.charAt(0)) == Character.UnicodeBlock.of(test_kanjiCharacter.charAt(0)) );
        assertTrue(Character.UnicodeBlock.of(radCharacter.charAt(0)) == Character.UnicodeBlock.of(test_radCharacter.charAt(0)));
        assertTrue(radCharacter.equals(test_radCharacter));
        assertTrue(kanjiStroke == test_kanjiStroke);
        assertTrue( radStroke == test_radStroke);
        assertTrue(radOrder == test_radOrder);

    }
}
