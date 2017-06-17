package com.summer17.yko.kanjimashouapp.Utilities;

/**
 * Created by Yko on 6/8/2017.
 */

public class BasicKanji {
    public class kanji {

        String character;
        int stroke;

        public kanji(String aCharacter, int aStroke){
            character = aCharacter;
            stroke = aStroke;
        }

        //Getters
        public String getCharacter(){
            return character;
        }

        public int getStroke(){
            return stroke;
        }


    }

    public class radical {

        String character;
        int stroke;
        int order;

        public radical(String aCharacter, int aStroke, int aOrder){
            character = aCharacter;
            stroke = aStroke;
            order = aOrder;
        }

        //Getters
        public String getCharacter(){
            return character;
        }

        public int getStroke(){
            return stroke;
        }

        public int getOrder(){
            return order;
        }

    }

    kanji kanji;

    radical radical;

    public BasicKanji(String kanjiCharacter, int kanjiStroke,
                      String radicalCharacter, int radicalStroke, int radicalOrder){

        kanji = new kanji(kanjiCharacter, kanjiStroke);
        radical = new radical(radicalCharacter,radicalStroke,radicalOrder);
    }

    //Getters
    public kanji getKanji(){
        return kanji;
    }

    public radical getRadical(){
        return radical;
    }
}
