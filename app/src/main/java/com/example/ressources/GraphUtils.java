package com.example.ressources;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.view.View;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import supportelement.Interval;


//   GraphUtils.getInstance().getLineChartView
//   (MainActivity.this, studentGradeList , "B");


public class GraphUtils {
    private static String[] s = new String[]{"A", "B", "C"};
    private static GraphUtils graph;
    private static ArrayList<Pie> pieL = new ArrayList<Pie>();
    private static ArrayList<HashMap<String, StudentGradeMessage>> stuGradeList = new ArrayList<HashMap<String, StudentGradeMessage>>();
    private static ArrayList<HashMap<Integer, Interval>> myinfolist = new ArrayList<>();

    public static GraphUtils getInstance() {
        ;
        if (graph == null) {
            graph = new GraphUtils();
        }
        return graph;
    }

    public static View createGraph(Context context, ArrayList<Date> dateList, ArrayList<Integer> dataList, String tag, String scale) {
        String format = "";
        if (scale.equals("Hour") || scale.equals("Day")) {
            format = "HH:mm";
        } else if (scale.equals("Month")) {
            format = "dd/MM";
        } else {
            format = "MM";
        }
        // Write data into TimeSeries
        TimeSeries timeSeries = new TimeSeries(tag);
        for (int index = 0; index < dataList.size(); index++) {
            timeSeries.add(dateList.get(index), dataList.get(index));
        }
        // Add TimeSeries into expected XYMultipleSeriesDataset
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(timeSeries);
        // Create XYSeriesRenderer to customize timeSeries
        XYSeriesRenderer timeSeriesRenderer = new XYSeriesRenderer();
        timeSeriesRenderer.setColor(Color.RED);
        timeSeriesRenderer.setPointStyle(PointStyle.CIRCLE);
        timeSeriesRenderer.setFillPoints(true);
        timeSeriesRenderer.setLineWidth(2);
        timeSeriesRenderer.setDisplayChartValues(true);
        // Create XYMultipleSeriesRenderer
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setChartTitle("Accesses over "+scale);
        multiRenderer.setXTitle("Time");
        multiRenderer.setYTitle("Number of Accesses");
        multiRenderer.setZoomButtonsVisible(false);
        multiRenderer.setBackgroundColor(Color.WHITE);
        multiRenderer.setApplyBackgroundColor(true);
        multiRenderer.setMarginsColor(Color.parseColor("#efefef"));
        multiRenderer.setYLabelsColor(0, Color.BLACK);
        multiRenderer.setXLabelsColor(Color.BLACK);
        multiRenderer.setAxesColor(Color.BLACK);
        multiRenderer.setPanEnabled(false, false);
        // Add XYSeriesRendere to XYMultipleSeriesRenderer
        multiRenderer.addSeriesRenderer(timeSeriesRenderer);
        return ChartFactory.getTimeChartView(context, dataset, multiRenderer, format);
    }

    /**
     * 学生成绩饼图
     *
     * @param context
     * @return
     */
    public static View getPieChartView(Context context, ArrayList<Pie> pie) {
        pieL = pie;
        double[] values = new double[pieL.size()];
        for (int i = 0; i < pieL.size(); i++) {
            values[i] = pieL.get(i).getValue();
        }
        int[] colors = new int[]{Color.parseColor("#DCD900"), Color.parseColor("#1E8C04"), Color.parseColor("#23BA00"),
                Color.parseColor("#90BA00")};
        DefaultRenderer renderer = buildCategoryRenderer(colors);
        renderer.setZoomButtonsVisible(true);
        renderer.setZoomEnabled(true);
        renderer.setChartTitleTextSize(20);
        renderer.setLabelsColor(Color.BLACK);
        renderer.setLegendHeight(30);
        renderer.setFitLegend(true);
        renderer.setShowLegend(true);
        renderer.setClickEnabled(true);
        return ChartFactory.getPieChartView(context, buildCategoryDataset("", values), renderer);
    }


    /**
     * 饼图
     *
     * @param colors
     * @return
     */
    protected static DefaultRenderer buildCategoryRenderer(int[] colors) {
        DefaultRenderer renderer = new DefaultRenderer();
        renderer.setLabelsTextSize(15);
        renderer.setLegendTextSize(15);
        renderer.setMargins(new int[]{20, 30, 15, 0});
        for (int color : colors) {
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
            r.setColor(color);
            renderer.addSeriesRenderer(r);
        }
        return renderer;
    }


    protected static CategorySeries buildCategoryDataset(String title,
                                                         double[] values) {
        String[] s = new String[]{"增加 ", "下降", "基本不变", "不变"};
        CategorySeries series = new CategorySeries(title);
        int k = 0;
        for (int i = 0; i < pieL.size(); i++) {
            series.add(s[i], pieL.get(i).getValue());
        }

        return series;
    }

    public static View getLineChartView2(Context context,
                                          HashMap<Integer, Integer> infolist,
                                          String tag) {

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        XYSeries series = new XYSeries(tag);
        int max = 0;
        for (Integer interval : infolist.keySet()) {
            series.add(
                    Double.valueOf(interval + ""),
                    Double.valueOf(infolist.get(interval)));
            if (infolist.get(interval) > max) {
                max = infolist.get(interval);
            }
        }
        dataset.addSeries(series);
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        renderer.setBackgroundColor(Color.parseColor("#efefef"));
        renderer.setApplyBackgroundColor(true);
        renderer.setMarginsColor(Color.parseColor("#efefef"));
        renderer.setPanEnabled(false, false);
        renderer.setLabelsTextSize(20f);
        renderer.setMargins(new int[]{30, 30, 30, 30});
        renderer.setYAxisMin(0);
        renderer.setXLabels(0);
        renderer.setShowGrid(true);
        renderer.setGridColor(Color.parseColor("#eeeeee"));
        renderer.setPointSize(5f);
        Align align = renderer.getYAxisAlign(0);
        renderer.setYLabelsAlign(align);
        renderer.setYLabelsColor(0, Color.BLACK);
        renderer.setYLabels(max / 4);
        renderer.setYAxisMin(0);
        renderer.setYAxisMax(max + 2);
        renderer.setXAxisMin(0);
        renderer.setXAxisMax(infolist.size()-1);
        renderer.setXLabelsColor(Color.BLACK);
        renderer.setAxesColor(Color.BLACK);
        renderer.setYLabelsAlign(Align.RIGHT);
        for (int i=0;i<=10;i++){
            renderer.addYTextLabel(i, String.valueOf(i));
        }
        int j = 0;
        for (int i = 0; i < 10; i++) {
            j++;
//			String name = map.get("name").getName().toString();
            renderer.addTextLabel(j, j + "");
        }
//        XYMultipleSeriesDataset dataset = getXYMultipleSeriesDataset(tag);
        XYSeriesRenderer xyRenderer = new XYSeriesRenderer();
        xyRenderer.setColor(Color.parseColor("#007aa4"));
        xyRenderer.setLineWidth(2f);
        xyRenderer.setDisplayChartValues(true);
        xyRenderer.setChartValuesTextSize(18f);
        xyRenderer.setDisplayChartValuesDistance(30);
        xyRenderer.setPointStyle(PointStyle.CIRCLE);
        xyRenderer.setFillBelowLine(true);
        xyRenderer.setFillBelowLineColor(Color.parseColor("#66FFB040"));
        xyRenderer.setFillPoints(true);

        renderer.addSeriesRenderer(xyRenderer);

        return ChartFactory.getLineChartView(context, dataset, renderer);
//        return ChartFactory.getCubeLineChartView(context, dataset, renderer, 0.33f);
//        return ChartFactory.getBarChartView(context, dataset, renderer, Type.STACKED); // type = DEFAULT | STACKED
//        return ChartFactory.getTimeChartView(context, dataset, renderer, "dd-mm-yyyy");
    }

    public static View getmyLineChartView(Context context,
                                          List<HashMap<Integer, Interval>> infolist,
                                          String tag) {

        myinfolist = (ArrayList<HashMap<Integer, Interval>>) infolist;
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        renderer.setBackgroundColor(Color.parseColor("#efefef"));
        renderer.setApplyBackgroundColor(true);
        renderer.setMarginsColor(Color.parseColor("#efefef"));
        renderer.setPanEnabled(false, false);
        renderer.setLabelsTextSize(20f);
        renderer.setMargins(new int[]{20, 55, 15, 5});
        renderer.setYAxisMin(0);
        renderer.setXLabels(0);
        renderer.setShowGrid(true);
        renderer.setGridColor(Color.parseColor("#eeeeee"));
        renderer.setPointSize(5f);
        Align align = renderer.getYAxisAlign(0);
        renderer.setYLabelsAlign(align);
        renderer.setYLabelsColor(0, Color.BLACK);
        renderer.setYLabels(6);
        renderer.setYAxisMin(0);
        renderer.setYAxisMax(10);
        renderer.setXAxisMin(0.1);
        renderer.setXAxisMax(10.5);
        renderer.setXLabelsColor(Color.BLACK);
        renderer.setAxesColor(Color.BLACK);
        renderer.setYLabelsAlign(Align.RIGHT);


        for (int i=0;i<=10;i++){
            renderer.addYTextLabel(i, String.valueOf(i));

        }

        int j = 0;
        for (int i = 0; i < 10; i++) {
            j++;
//			String name = map.get("name").getName().toString();
            renderer.addTextLabel(j, j + "");
        }
        XYMultipleSeriesDataset dataset = getXYMultipleSeriesDataset(tag);
        XYSeriesRenderer xyRenderer = new XYSeriesRenderer();
        xyRenderer.setColor(Color.parseColor("#007aa4"));
        xyRenderer.setLineWidth(2f);
        xyRenderer.setDisplayChartValues(true);
        xyRenderer.setChartValuesTextSize(18f);
        xyRenderer.setDisplayChartValuesDistance(30);
        xyRenderer.setPointStyle(PointStyle.CIRCLE);
        xyRenderer.setFillBelowLine(true);
        xyRenderer.setFillBelowLineColor(Color.parseColor("#66FFB040"));
        xyRenderer.setFillPoints(true);

        renderer.addSeriesRenderer(xyRenderer);

        return ChartFactory.getCubeLineChartView(context, dataset, renderer, 0.33f);

    }


    /**
     * 圆滑单曲线
     *
     * @param context
     * @param
     * @param tag
     * @return
     */
    public static View getLineChartView(Context context,
                                        List<HashMap<String, StudentGradeMessage>> studentGradeList,
                                        String tag) {

        stuGradeList = (ArrayList<HashMap<String, StudentGradeMessage>>) studentGradeList;
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        renderer.setBackgroundColor(Color.parseColor("#efefef"));
        renderer.setApplyBackgroundColor(true);
        renderer.setMarginsColor(Color.parseColor("#efefef"));
        renderer.setPanEnabled(false, false);
        renderer.setLabelsTextSize(20f);
        renderer.setMargins(new int[]{20, 55, 15, 5});
        renderer.setYAxisMin(0);
        renderer.setXLabels(0);
        renderer.setShowGrid(true);
        renderer.setGridColor(Color.parseColor("#eeeeee"));
        renderer.setPointSize(5f);
        Align align = renderer.getYAxisAlign(0);
        renderer.setYLabelsAlign(align);
        renderer.setYLabelsColor(0, Color.BLACK);
        renderer.setYLabels(6);
        renderer.setYAxisMin(10);
        renderer.setYAxisMax(100);
        renderer.setXAxisMin(0.1);
        renderer.setXAxisMax(10.5);
        renderer.setXLabelsColor(Color.BLACK);
        renderer.setAxesColor(Color.BLACK);
        renderer.setYLabelsAlign(Align.RIGHT);
        renderer.addYTextLabel(100, String.valueOf("100"));
        renderer.addYTextLabel(80, String.valueOf("80"));
        renderer.addYTextLabel(60, String.valueOf("60"));
        renderer.addYTextLabel(40, String.valueOf("40"));
        renderer.addYTextLabel(20, String.valueOf("20"));
        int j = 0;
        for (int i = 0; i < 10; i++) {
            j++;
//			String name = map.get("name").getName().toString();
            renderer.addTextLabel(j, j + "");
        }
        XYMultipleSeriesDataset dataset = getXYMultipleSeriesDataset(tag);
        XYSeriesRenderer xyRenderer = new XYSeriesRenderer();
        xyRenderer.setColor(Color.parseColor("#007aa4"));
        xyRenderer.setLineWidth(2f);
        xyRenderer.setDisplayChartValues(true);
        xyRenderer.setChartValuesTextSize(18f);
        xyRenderer.setDisplayChartValuesDistance(30);
        xyRenderer.setPointStyle(PointStyle.CIRCLE);
        xyRenderer.setFillBelowLine(true);
        xyRenderer.setFillBelowLineColor(Color.parseColor("#66FFB040"));
        xyRenderer.setFillPoints(true);

        renderer.addSeriesRenderer(xyRenderer);

        return ChartFactory.getCubeLineChartView(context, dataset, renderer, 0.33f);

    }

    public static XYMultipleSeriesDataset getXYMultipleSeriesDataset(String tag) {

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

        if ("GPS".equals(tag)) {
            XYSeries series = new XYSeries("GPS");
            System.out.println("lalallall");
            for (int i = 1; i < myinfolist.size() + 1; i++) {
                System.out.println("cqklcjl");
                series.add(
                        Double.valueOf(i + ""),
                        Double.valueOf(myinfolist.get(i - 1).get(1).getGPS()));
            }

            dataset.addSeries(series);
        } else if ("MobileData".equals(tag)) {
            XYSeries series = new XYSeries("MobileData");
            for (int i = 1; i < myinfolist.size() + 1; i++) {
                series.add(
                        Double.valueOf(i + ""),
                        Double.valueOf(myinfolist.get(i - 1).get(1).getMobiledata()));
            }
            dataset.addSeries(series);
        } else if ("WIFI".equals(tag)) {
            XYSeries series = new XYSeries("WIFI");
            for (int i = 1; i < myinfolist.size() + 1; i++) {
                series.add(
                        Double.valueOf(i + ""),
                        Double.valueOf(myinfolist.get(i - 1).get(1).getWIFI()));
            }
            dataset.addSeries(series);
        } else if ("SMS".equals(tag)) {
            XYSeries series = new XYSeries("SMS");
            for (int i = 1; i < myinfolist.size() + 1; i++) {
                series.add(
                        Double.valueOf(i + ""),
                        Double.valueOf(myinfolist.get(i - 1).get(1).getSMS()));
            }
            dataset.addSeries(series);
        } else if ("Contacts".equals(tag)) {
            XYSeries series = new XYSeries("Contacts");
            for (int i = 1; i < myinfolist.size() + 1; i++) {
                series.add(
                        Double.valueOf(i + ""),
                        Double.valueOf(myinfolist.get(i - 1).get(1).getContacts()));
            }
            dataset.addSeries(series);
        }
        /*
		if ("A".equals(tag)) {
			XYSeries series = new XYSeries("A");
			for (int i = 1; i < stuGradeList.size() + 1; i++) {
				series.add(
						Double.valueOf(i + ""),
						Double.valueOf(stuGradeList.get(i - 1).get("name")
								.getChinese()));

			}
			dataset.addSeries(series);
		} else if ("B".equals(tag)) {
			XYSeries series = new XYSeries("B");
			for (int i = 1; i < stuGradeList.size() + 1; i++) {
				series.add(
						Double.valueOf(i + ""),
						Double.valueOf(stuGradeList.get(i - 1).get("name")
								.getMath()));
			}
			dataset.addSeries(series);
		} else if ("C".equals(tag)) {
			XYSeries series = new XYSeries("C");
			for (int i = 1; i < stuGradeList.size() + 1; i++) {
				series.add(
						Double.valueOf(i + ""),
						Double.valueOf(stuGradeList.get(i - 1).get("name")
								.getEnglish()));
			}
			dataset.addSeries(series);
		} else {
			XYSeries series = new XYSeries("D");
			for (int i = 1; i < stuGradeList.size() + 1; i++) {
				series.add(
						Double.valueOf(i + ""),
						Double.valueOf(stuGradeList.get(i - 1).get("name")
								.getTotal()));
			}
			dataset.addSeries(series);
		}*/


        return dataset;
    }


    /**
     * 单曲线 折线
     *
     * @param context
     * @param studentGradeList
     * @param sub
     * @param color
     * @return
     */
    public static View getLineChartViewOfNum(Context context,
                                             List<HashMap<String, StudentGradeMessage>> studentGradeList,
                                             String[] sub, Integer[] color) {
//
        stuGradeList = (ArrayList<HashMap<String, StudentGradeMessage>>) studentGradeList;
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        renderer.setChartTitle("折线图");
        renderer.setChartTitleTextSize(20f);
        renderer.setBackgroundColor(Color.YELLOW);
        renderer.setPanEnabled(false, false);
        renderer.setLabelsTextSize(20f);
        renderer.setYAxisMin(43);
        renderer.setYAxisMax(1);
        renderer.setXLabels(0);
        renderer.setShowGrid(true);
        Align align = renderer.getYAxisAlign(0);
        renderer.setYLabelsAlign(align);


        renderer.setYLabelsColor(0, Color.BLACK);
        renderer.setYLabels(15);
        renderer.setXAxisMin(1);
        int j = 0;
        for (Map<String, StudentGradeMessage> map : stuGradeList) {
            j++;
            String name = map.get("name").getName().toString();
            renderer.addTextLabel(j, name);
        }
        XYMultipleSeriesDataset dataset = getXYMultipleSeriesDatasetOfNum(sub);

        for (int i = 0; i < sub.length; i++) {

            XYSeriesRenderer xyRenderer = new XYSeriesRenderer();
            xyRenderer.setColor(color[i]);
            xyRenderer.setPointStyle(PointStyle.POINT);
            renderer.addSeriesRenderer(xyRenderer);
        }

        return ChartFactory.getLineChartView(context, dataset, renderer);

    }

    public static XYMultipleSeriesDataset getXYMultipleSeriesDatasetOfNum(
            String[] sub) {

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        for (int k = 0; k < sub.length; k++) {
            if ("A".equals(sub[k])) {
                XYSeries series = new XYSeries("A");
                for (int i = 1; i < stuGradeList.size() + 1; i++) {
                    series.add(
                            Double.valueOf(i + ""),
                            Double.valueOf(stuGradeList.get(i - 1).get("name")
                                    .getNumChinese()));

                }
                dataset.addSeries(series);
            } else if ("B".equals(sub[k])) {
                XYSeries series = new XYSeries("B");
                for (int i = 1; i < stuGradeList.size() + 1; i++) {
                    series.add(
                            Double.valueOf(i + ""),
                            Double.valueOf(stuGradeList.get(i - 1).get("name")
                                    .getNumMath()));
                }
                dataset.addSeries(series);
            } else if ("C".equals(sub[k])) {
                XYSeries series = new XYSeries("C");
                for (int i = 1; i < stuGradeList.size() + 1; i++) {
                    series.add(
                            Double.valueOf(i + ""),
                            Double.valueOf(stuGradeList.get(i - 1).get("name")
                                    .getNumEnglish()));
                }
                dataset.addSeries(series);
            } else {
                XYSeries series = new XYSeries(sub[k]);
                for (int i = 1; i < stuGradeList.size() + 1; i++) {
                    series.add(
                            Double.valueOf(i + ""),
                            Double.valueOf(stuGradeList.get(i - 1).get("name")
                                    .getNumTotal()));
                }
                dataset.addSeries(series);
            }
        }
        return dataset;
    }


    /**
     * 柱状图
     *
     * @param context
     * @return
     */
    public View getBarHorView(Context context) {
        String[] titles = new String[]{"1", "2"};
        List<Double> values = new ArrayList<Double>();
        values.add(50.00);
        values.add(10.00);
        int[] colors = new int[]{Color.parseColor("#F4A100"), Color.parseColor("#F4A100")};
        XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
        renderer.setOrientation(Orientation.HORIZONTAL);
        setChartSettings(renderer, "", "", "", 0,
                5, 0, 60, Color.BLUE, Color.BLUE);
        renderer.setXLabels(0);
        renderer.setYLabels(10);
        renderer.addXTextLabel(1, titles[0]);
        renderer.addXTextLabel(2, titles[1]);
        int length = renderer.getSeriesRendererCount();

        for (int i = 0; i < length; i++) {
            SimpleSeriesRenderer seriesRenderer = renderer.getSeriesRendererAt(i);
            seriesRenderer.setDisplayChartValues(true);
        }
        return ChartFactory.getBarChartView(context, buildBarDataset(titles, values), renderer,
                Type.DEFAULT);
    }

    /**
     * Builds a bar multiple series renderer to use the provided colors.
     *
     * @param colors the series renderers colors
     * @return the bar multiple series renderer
     */
    protected XYMultipleSeriesRenderer buildBarRenderer(int[] colors) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();

        renderer.setLabelsTextSize(5);
        renderer.setLabelsColor(Color.BLACK);
        renderer.setBarWidth(59f);
        renderer.setBarSpacing(20);
        renderer.setShowAxes(false);
        renderer.setShowLegend(false);
        renderer.setBackgroundColor(Color.parseColor("#efefef"));
        renderer.setApplyBackgroundColor(true);
        renderer.setPanEnabled(false, false);
        renderer.setZoomEnabled(false, false);
        renderer.setLegendTextSize(5);
        int length = colors.length;
        for (int i = 0; i < length; i++) {
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
            r.setColor(colors[i]);
            renderer.addSeriesRenderer(r);
        }
        return renderer;
    }

    /**
     * Builds a bar multiple series dataset using the provided values.
     *
     * @param titles the series titles
     * @param values the values
     * @return the XY multiple bar dataset
     */
    protected XYMultipleSeriesDataset buildBarDataset(String[] titles, List<Double> values) {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        int length = titles.length;
        for (int i = 0; i < length; i++) {
            CategorySeries series = new CategorySeries(titles[i]);
            double v = values.get(i);

            series.add(v);

            dataset.addSeries(series.toXYSeries());
        }
        return dataset;
    }

    /**
     * Sets a few of the series renderer settings.
     *
     * @param renderer    the renderer to set the properties to
     * @param title       the chart title
     * @param xTitle      the title for the X axis
     * @param yTitle      the title for the Y axis
     * @param xMin        the minimum value on the X axis
     * @param xMax        the maximum value on the X axis
     * @param yMin        the minimum value on the Y axis
     * @param yMax        the maximum value on the Y axis
     * @param axesColor   the axes color
     * @param labelsColor the labels color
     */
    protected static void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle,
                                           String yTitle, double xMin, double xMax, double yMin, double yMax, int axesColor,
                                           int labelsColor) {
        renderer.setChartTitle(title);
        renderer.setXTitle(xTitle);
        renderer.setYTitle(yTitle);
        renderer.setXAxisMin(xMin);
        renderer.setXAxisMax(xMax);
        renderer.setYAxisMin(yMin);
        renderer.setYAxisMax(yMax);
        renderer.setAxesColor(axesColor);
        renderer.setLabelsColor(labelsColor);
    }

    public static View getAChartDoubleLine(Context context, String[] titles, List<double[]> values) {
        List<double[]> x = new ArrayList<double[]>();
        for (int i = 0; i < titles.length; i++) {
            x.add(new double[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        }
        int[] colors = new int[]{Color.parseColor("#53656c"), Color.parseColor("#ff813c"),};
        PointStyle[] styles = new PointStyle[]{PointStyle.CIRCLE, PointStyle.CIRCLE,
        };
        XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
        int length = renderer.getSeriesRendererCount();
        for (int i = 0; i < length; i++) {
            ((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setFillPoints(true);
        }
        setChartSettings(renderer, "", "", "", 0.5, 10.5, 0, 40,
                Color.BLACK, Color.BLACK);
        renderer.setXLabels(12);
        renderer.setYLabels(0);
        Align align = renderer.getYAxisAlign(0);
        renderer.setYLabelsAlign(align);
        renderer.setYLabelsColor(0, Color.BLACK);
        renderer.setAxesColor(Color.BLACK);
        renderer.setXLabelsColor(Color.BLACK);
        renderer.setYLabelsAlign(Align.RIGHT);
        renderer.setShowGrid(true);
        renderer.setGridColor(Color.parseColor("#eeeeee"));
        renderer.setPointSize(5f);

        renderer.setBackgroundColor(Color.parseColor("#efefef"));
        renderer.setApplyBackgroundColor(true);
        renderer.setMarginsColor(Color.parseColor("#efefef"));
        renderer.setPanEnabled(false, false);
        renderer.setXLabelsAlign(Align.RIGHT);
        renderer.setYLabelsAlign(Align.RIGHT);
        renderer.setZoomButtonsVisible(true);


        XYMultipleSeriesDataset dataset = buildDataset(titles, x, values);
        XYSeries series = dataset.getSeriesAt(0);
        return ChartFactory.getLineChartView(context, dataset, renderer);
    }

    protected static XYMultipleSeriesRenderer buildRenderer(int[] colors, PointStyle[] styles) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        setRenderer(renderer, colors, styles);
        return renderer;
    }

    protected static void setRenderer(XYMultipleSeriesRenderer renderer, int[] colors, PointStyle[] styles) {
        renderer.setAxisTitleTextSize(16);
        renderer.setChartTitleTextSize(20);
        renderer.setLabelsTextSize(15);
        renderer.setLegendTextSize(15);
        renderer.setPointSize(5f);
        renderer.setMargins(new int[]{20, 30, 15, 20});
        int length = colors.length;
        for (int i = 0; i < length; i++) {
            XYSeriesRenderer r = new XYSeriesRenderer();
            if (i == 1) {
                r.setFillBelowLine(true);
                r.setFillBelowLineColor(Color.parseColor("#66FFB040"));
                r.setFillPoints(true);
            }
            r.setColor(colors[i]);
            r.setPointStyle(styles[i]);
            renderer.addSeriesRenderer(r);
        }
    }

    protected static XYMultipleSeriesDataset buildDataset(String[] titles, List<double[]> xValues,
                                                          List<double[]> yValues) {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        addXYSeries(dataset, titles, xValues, yValues, 0);
        return dataset;
    }

    public static void addXYSeries(XYMultipleSeriesDataset dataset, String[] titles, List<double[]> xValues,
                                   List<double[]> yValues, int scale) {
        int length = titles.length;
        for (int i = 0; i < length; i++) {
            XYSeries series = new XYSeries(titles[i], scale);
            double[] xV = xValues.get(i);
            double[] yV = yValues.get(i);
            int seriesLength = xV.length;
            for (int k = 0; k < seriesLength; k++) {
                series.add(xV[k], yV[k]);

            }
            dataset.addSeries(series);
        }
    }
}
