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

    private final SearchAdapterClickHandler mClickHandler;

    public SearchAdapter(SearchAdapterClickHandler aClickHandler){
        mClickHandler = aClickHandler;
    }

    /**
     * Create viewHolder, set up its layout
     * @param parent the ViewGroup that these KanjiViewHolders are contained within
     * @param viewType default param, in case there are different types of ViewHolder
     * @return
     */
    @Override
    public KanjiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.item_kanji_properties;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        //bool : will attach to parent later on in time
        View view = layoutInflater.inflate(layoutId, parent, false);

        return new KanjiViewHolder(view);
    }


    /**
     * Set up the data in the given KanjiViewHolder
     * Takes the data from the ArrayList at the given position
     * @param holder the KanjiViewHOlder to set up
     * @param position the index of the Arraylist from which to get the data
     */
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

    /**
     * Get the number of item in our data set
     * @return item count
     */
    @Override
    public int getItemCount() {
        if(mKanjiList.size() == 0) return 0;
        return mKanjiList.size();
    }

    /**
     * Set the reference to the data set
     * @param aKanjiList
     */
    public void setKanjiList(ArrayList<BasicKanji> aKanjiList){
        mKanjiList = aKanjiList;
    }

    public class KanjiViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener{

        public final TextView mKanji;

        public final TextView mProperties;

        /**
         * Creates a KanjiViewHolder,
         * Store a reference of the 2 textviews
         * set the onClickListener of the view that was passed
         * @param itemView
         */
        public KanjiViewHolder(View itemView) {
            super(itemView);
            mKanji = (TextView) itemView.findViewById(R.id.tv_kanji);
            mProperties = (TextView) itemView.findViewById(R.id.tv_properties);
            itemView.setOnClickListener(this);
        }

        /**
         * Detects when the ViewHolder is clicked
         * @param v the view that was clicked
         */
        @Override
        public void onClick(View v) {
            mClickHandler.onListItemClicked(mKanji.getText().toString());
        }
    }


    /**
     * Interface, will be implemented by the main activity
     * define what the click action triggers
     */
    public interface SearchAdapterClickHandler {
        void onListItemClicked(String clickedKanji);
    }
}
