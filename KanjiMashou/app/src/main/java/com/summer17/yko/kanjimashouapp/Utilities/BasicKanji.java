package com.summer17.yko.kanjimashouapp.Utilities;

import java.io.Serializable;

/**
 * Created by Yko on 6/8/2017.
 */

public class BasicKanji implements Serializable{
    public class KanjiPart implements Cloneable{

        String character;
        int stroke;

        public KanjiPart(String aCharacter, int aStroke){
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
        int stroke;
        int order;

        public RadicalPart(String aCharacter, int aStroke, int aOrder){
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

    KanjiPart kanji;

    RadicalPart radical;

    public BasicKanji(String kanjiCharacter, int kanjiStroke,
                      String radicalCharacter, int radicalStroke, int radicalOrder){

        kanji = new KanjiPart(kanjiCharacter, kanjiStroke);
        radical = new RadicalPart(radicalCharacter,radicalStroke,radicalOrder);
    }

    //Getters
    public KanjiPart getKanji(){
        return kanji.clone();
    }

    public RadicalPart getRadical(){
        return radical.clone();
    }
}
