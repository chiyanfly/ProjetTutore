package com.example.ressources;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.achartengine.GraphicalView;

import java.util.ArrayList;

import supportelement.GraphItem;


/**
 * Created by hxu on 09/04/18.
 */
public class Recyleradapter extends RecyclerView.Adapter<Recyleradapter.GraphViewHolder> {
    private ArrayList<GraphicalView> myList;
    //int mLastPosition = 0;


    public Recyleradapter(ArrayList<GraphItem> myList) {
//  get the graphicalview of each graphitem  no need for tag or data here
        ArrayList<GraphicalView> list = new ArrayList<>();

        for (GraphItem g : myList) {
            list.add(g.getGraphicalView());
        }

        this.myList = list;
        //mListner = listner;
    }


    @Override
    public GraphViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ritem, parent, false);
        GraphViewHolder g = new GraphViewHolder(v);
        return g;


    }

    @Override
    public void onBindViewHolder(GraphViewHolder holder, int position) {

        GraphicalView glocal = myList.get(position);

        System.err.println("lalall");
        holder.f.removeAllViews();
        holder.f.addView(glocal);


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


    public class GraphViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout f;


        public GraphViewHolder(View vitem) {

            super(vitem);
            f = (LinearLayout) vitem.findViewById(R.id.frame);

        }
    }

}