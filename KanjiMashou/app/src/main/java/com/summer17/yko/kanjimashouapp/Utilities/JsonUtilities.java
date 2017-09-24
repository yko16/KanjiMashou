package com.summer17.yko.kanjimashouapp.Utilities;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by Yko on 6/17/2017.
 */

public class JsonUtilities {

    public static ArrayList<BasicKanji> fromJsonToBasicKanji(String JsonString){
        ArrayList<BasicKanji> kanjiList = new ArrayList<BasicKanji>();
        try{
            JsonReader reader = new JsonReader(new StringReader(JsonString));

            reader.beginArray();

            while(reader.hasNext()){
                BasicKanji currentKanji = new Gson().fromJson(reader, BasicKanji.class);
                kanjiList.add(currentKanji);
            }
            reader.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return kanjiList;
    }

    public static DetailKanji fromJsonToDetailKanji(String JsonString){
        DetailKanji detailKanji = new Gson().fromJson(JsonString, DetailKanji.class);
        return detailKanji;
    }
}
