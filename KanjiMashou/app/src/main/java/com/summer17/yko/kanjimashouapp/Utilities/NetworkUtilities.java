package com.summer17.yko.kanjimashouapp.Utilities;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by Yko on 6/5/2017.
 */

public class NetworkUtilities {

    final static String KANJIALIVE_BASE_URL = "https://kanjialive-api.p.mashape.com/api/public/search/";

    final static String MASHAPE_KEY_PARAM = "X-Mashape-Key";

    //TODO : figure out proper way to play around with keys
    static String MASHAPE_KEY_VALUE = "udUluyTVq6mshTKQzPPZYP8OW86xp1CLntWjsnyalXb58zBkUz";


    /**
     * This build the Url for a Basic Search to connect with KanjiAlive
     * @param query must be a single word (kanji / hiragana / katakana / English)
     * @return the String version of the Url
     */
    public static String basicSearchBuildUrl(String query){
        String stringURL = null;

        HttpUrl.Builder urlBuilder = HttpUrl.parse(KANJIALIVE_BASE_URL + query).newBuilder();

        stringURL = urlBuilder.build().toString();

        //TODO : try-catch for malformed url ?


        return stringURL;
    }

    public static ArrayList<BasicKanji> basicSearchGetHttpResponse(String aURL){
        ArrayList<BasicKanji> kanjiArray = new ArrayList<BasicKanji>();

        //Obtain kanji list from API
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .header(MASHAPE_KEY_PARAM, MASHAPE_KEY_VALUE)
                .url(aURL)
                .build();
        try{
            Response response = client.newCall(request).execute();
            try{
                JsonReader reader = new JsonReader(new InputStreamReader(response.body().byteStream() , "UTF-8"));

                reader.beginArray();

                while(reader.hasNext()){
                    BasicKanji currentKanji = new Gson().fromJson(reader, BasicKanji.class);
                    kanjiArray.add(currentKanji);
                }
                reader.close();
            }
            catch (FileNotFoundException e){
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

        //catch exception if there was one during the request
        catch (IOException e){
            e.printStackTrace();
        }
        return kanjiArray;
    }



}
