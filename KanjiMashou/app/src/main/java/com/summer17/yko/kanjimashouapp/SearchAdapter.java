package com.summer17.yko.kanjimashouapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.summer17.yko.kanjimashouapp.Utilities.BasicKanji;

import java.util.ArrayList;

/**
 * Created by Yko on 6/19/2017.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.KanjiViewHolder> {

    ArrayList<BasicKanji> mKanjiList;

    public SearchAdapter(){

    }

    @Override
    public KanjiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.item_kanji_properties;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        //bool : will attach to parent later on in time
        View view = layoutInflater.inflate(layoutId, parent, false);

        return new KanjiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(KanjiViewHolder holder, int position) {
        BasicKanji currentKanji = mKanjiList.get(position);

        String kanjiSign = currentKanji.getKanji().getCharacter();
        holder.mKanji.setText(kanjiSign);

        String kanjiProperties = "Strokes: " + currentKanji.getKanji().getStroke()+"\n"
                +"Radical: " + currentKanji.getRadical().getCharacter() +"\n"
                +"Order: "+currentKanji.getRadical().getOrder();
        holder.mProperties.setText(kanjiProperties);
    }

    @Override
    public int getItemCount() {
        if(mKanjiList.size() == 0) return 0;
        return mKanjiList.size();
    }

    public void setKanjiList(ArrayList<BasicKanji> aKanjiList){
        mKanjiList = aKanjiList;
    }

    public class KanjiViewHolder extends RecyclerView.ViewHolder{

        public final TextView mKanji;

        public final TextView mProperties;

        public KanjiViewHolder(View itemView) {
            super(itemView);
            mKanji = (TextView) itemView.findViewById(R.id.tv_kanji);
            mProperties = (TextView) itemView.findViewById(R.id.tv_properties);
        }
    }
}
