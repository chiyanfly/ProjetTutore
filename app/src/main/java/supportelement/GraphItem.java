package supportelement;

import org.achartengine.GraphicalView;

import java.util.Date;

/**
 * Created by XH on 2018/4/13.
 */


public class GraphItem {


    private GraphicalView graphicalView;
    private String tag;
    Date date;


    public  GraphItem(GraphicalView g, String t) {


        graphicalView = g;
        tag = t;



    }


    public GraphicalView getGraphicalView() {
        return graphicalView;
    }


    public void setGraphicalView(GraphicalView g) {
        graphicalView = g;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Date getDate() {
        return date;
    }


    public String getTag() {
        return tag;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

