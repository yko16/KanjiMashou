package com.summer17.yko.kanjimashouapp;

import com.summer17.yko.kanjimashouapp.Utilities.BasicKanji;
import com.summer17.yko.kanjimashouapp.Utilities.DetailKanji;
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

public class TestBasicSearch {

    String mData;
    //BasicKanji mTemplateKanji;


    /**
     * mData : mock input data to make request to KanjiAlive API
     * mTemplateKanji : expected output of the KanjiAlive API
     */
    @Before
    public void setup(){
        mData = "cold";
        //mTemplateKanji  = new BasicKanji("寒", 12, "⼧", 3, 48);
        //-->mTemplateKanji  = new BasicKanji("冷", 7, "⼎", 2, 19);
        //mTemplateKanji  = new BasicKanji("雨", 8, "⾬", 8, 210);
    }


    /**
     * test : basicSearchBuildUrl & basicSearchGetHttpResponse
     */
    @Test
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

        // Test Data for cold
        String test_kanjiCharacter = "冷";
        int test_kanjiStroke = 7;
        String test_radCharacter = "⼎";
        int test_radStroke = 2;
        int test_radOrder = 19;

        /*// Test Data for warm
        String test_kanjiCharacter = "寒";
        int test_kanjiStroke = 12;
        String test_radCharacter = "⼧";
        int test_radStroke = 3;
        int test_radOrder = 48;*/

        /*// Test Data for rain
        String test_kanjiCharacter = "雨";
        int test_kanjiStroke = 8;
        String test_radCharacter = "⾬";
        int test_radStroke = 8;
        int test_radOrder = 210;*/

        assertTrue(Character.UnicodeBlock.of(kanjiCharacter.charAt(0)) == Character.UnicodeBlock.of(test_kanjiCharacter.charAt(0)) );
        assertTrue(Character.UnicodeBlock.of(radCharacter.charAt(0)) == Character.UnicodeBlock.of(test_radCharacter.charAt(0)));
        assertTrue(radCharacter.equals(test_radCharacter));
        assertTrue(kanjiStroke == test_kanjiStroke);
        assertTrue( radStroke == test_radStroke);
        assertTrue(radOrder == test_radOrder);

    }

    @Test
    public void testDetailKanjiRequest() throws IOException{
        String url = NetworkUtilities.detailQueryBuildUrl("訪");
        String detailKanjiJsonString  = NetworkUtilities.detailQueryGetHttpResponse(url);
        System.out.print(detailKanjiJsonString);
        DetailKanji detailKanji = JsonUtilities.fromJsonToDetailKanji(detailKanjiJsonString);

        DetailKanji.KanjiPart kanjiPart = detailKanji.getKanji();
        DetailKanji.RadicalPart radicalPart = detailKanji.getRadical();
        DetailKanji.KanjiExample[] examples = detailKanji.getExamples();


        //KanjiPart error : came from kanjiPart clone method (CloneNotSupported)

        //assertTrue(kanjiPart!= null);
        assertTrue(kanjiPart.getCharacter().equals("訪"));
        assertTrue(kanjiPart.getStrokes() == 11);
        assertTrue(kanjiPart.getMeaning().getEnglish().equals("visit"));
        assertTrue(kanjiPart.getOnyomi().getRomaji().equals("hou") );
        assertTrue(kanjiPart.getKunyomi().getRomaji().equals("otozureru, tazuneru, otozu, tazu"));

        System.out.print(radicalPart.getMeaning().getEnglish());
        assertTrue(radicalPart.getMeaning().getEnglish().equals("words, to speak, say"));
        assertTrue(examples[0].getMeaning().getEnglish().equals("make a tour of"));
        /*assertTrue();
        assertTrue();
        assertTrue();
        assertTrue();
        assertTrue();
        assertTrue();*/
    }
}
