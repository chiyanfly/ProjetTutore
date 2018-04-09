package com.example.ressources;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import org.achartengine.GraphicalView;

import java.util.ArrayList;


/**
 * Created by hxu on 09/04/18.
 */
public class Recyleradapter extends RecyclerView.Adapter<Recyleradapter.GraphViewHolder> {
    private ArrayList<GraphicalView> myList;
    //int mLastPosition = 0;


    public Recyleradapter(ArrayList<GraphicalView> myList) {
        this.myList = myList;
        //mListner = listner;
    }




    @Override
    public GraphViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ritem,parent,false);
        GraphViewHolder  g = new GraphViewHolder(v);
         return g;


    }
    @Override
    public void onBindViewHolder(GraphViewHolder holder, int position) {




    }



    @Override
    public int getItemCount() {
        return (null != myList ? myList.size() : 0);
    }

    public void notifyData(ArrayList<GraphicalView> myList) {
     //   Log.d("notifyData ", myList.size() + "");
        this.myList = myList;
        notifyDataSetChanged();
    }



    public class GraphViewHolder extends RecyclerView.ViewHolder{

        public GraphViewHolder(View v){

    super(v);






}









    }

}