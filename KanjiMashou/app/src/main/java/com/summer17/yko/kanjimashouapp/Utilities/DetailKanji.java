package com.summer17.yko.kanjimashouapp.Utilities;

import java.io.Serializable;

/**
 * Created by Yko on 6/19/2017.
 */

public class DetailKanji implements Serializable{


    //Exception 2 fields kanji

    KanjiPart kanji;

    RadicalPart radical;

    KanjiExample[] examples;

    public class KanjiPart {

        String character;
        KanjiMeaning meaning;
        KanjiStrokes strokes;
        KanjiOnyomi onyomi;
        KanjiKunyomi kunyomi;

        public class KanjiMeaning{
            String english;

            public String getEnglish() {
                return english;
            }
        }

        public class KanjiStrokes{
            int count;

            public int getCount(){
                return count;
            }
        }

        public class KanjiOnyomi{
            String romaji;
            String katakana;

            public String getRomaji() {
                return romaji;
            }

            public String getKatakana() {
                return katakana;
            }
        }

        public class KanjiKunyomi{
            String romaji;
            String hiragana;

            public String getRomaji() {
                return romaji;
            }

            public String getHiragana() {
                return hiragana;
            }
        }


        //Getters
        public String getCharacter(){
            return character;
        }

        public int getStrokes(){
            return strokes.getCount();
        }

        public KanjiMeaning getMeaning() {
            return meaning;
        }

        public KanjiOnyomi getOnyomi() {
            return onyomi;
        }

        public KanjiKunyomi getKunyomi() {
            return kunyomi;
        }


        @Override
        public KanjiPart clone(){
            try{
                KanjiPart kanjiPartClone = (KanjiPart) super.clone();
                return kanjiPartClone;
            }
            catch (CloneNotSupportedException e){
                return null;
            }
        }
    }


    public class RadicalPart implements Cloneable{

        String character;

        RadicalMeaning meaning;

        public class RadicalMeaning{
            String english;

            public String getEnglish() {
                return english;
            }
        }

        public RadicalMeaning getMeaning() {
            return meaning;
        }


        public String getCharacter(){
            return character;
        }


        @Override
        public RadicalPart clone(){
            try{
                RadicalPart radPartClone = (RadicalPart) super.clone();
                return radPartClone;
            }
            catch (CloneNotSupportedException e){
                return null;
            }
        }
    }


    public class KanjiExample {

        String japanese;
        ExampleMeaning meaning;

        public class ExampleMeaning{
            String english;

            public String getEnglish() {
                return english;
            }
        }

        public String getJapanese() {
            return japanese;
        }

        public String getMeaning() {
            return meaning.getEnglish();
        }
    }


    //Getters
    public KanjiPart getKanji(){
        return kanji;
    }

    public RadicalPart getRadical(){
        return radical.clone();
    }

    public KanjiExample[] getExamples() {
        return examples;
    }
}
