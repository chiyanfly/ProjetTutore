package supportelement;

import java.util.HashMap;

import tool.Groupresource;

public class Interval {

    private int time;
    /*
    private int CPU;
    private int GPS;
    private int SMS;
    private int WIFI;
    private int Mobiledata;
    private int Contacts;
*/
    private HashMap<String, Integer> mapinfo;


    public Interval(int t) {
        time = t;

        mapinfo = new HashMap<>();

        for (String s : Groupresource.Resnamelist) {

            mapinfo.put(s, 0);

        }


    }

/*
    public int getCPU() {
        return CPU;
    }

    public int getGPS() {
        return GPS;
    }

    public int getSMS() {
        return SMS;
    }

    public int getTime() {
        return time;
    }

    public int getWIFI() {
        return WIFI;
    }

    public int getMobiledata() {
        return Mobiledata;
    }


    public void setCPU(int CPU) {
        this.CPU = CPU;
    }


    public void setGPS(int GPS) {
        this.GPS = GPS;
    }

    public void setMobiledata(int mobiledata) {
        Mobiledata = mobiledata;
    }

    public void setSMS(int SMS) {
        this.SMS = SMS;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setWIFI(int WIFI) {
        this.WIFI = WIFI;
    }


    public int getContacts() {
        return Contacts;
    }

    public void setContacts(int contacts) {
        Contacts = contacts;
    }


*/

    public int getvalue(String resname) {

        return mapinfo.get(resname);

    }


    public void setvalue(String name, int value) {

/*
        switch (name) {

            case "GPS":
                GPS = value;
                break;

            case "CPU":
                CPU = value;
                break;

            case "MobileData":
                Mobiledata = value;
                break;

            case "Wifi":
                WIFI = value;
                break;
            case "SMS":
                SMS = value;
                break;
            case "Contacts":
                Contacts = value;
                break;
            default:
                break;


        }*/

        mapinfo.put(name, value);

    }

/*
    @Override
    public String toString() {
        //super.toString();


        return " time " + getTime() + " contacts " + getContacts() +
                " CPU " + getCPU() +
                " GPS " + getGPS() +
                " Mobiledata " + getMobiledata() +
                " SMS " + getSMS() +
                " WIFI " + getWIFI();


    }
    */
}
