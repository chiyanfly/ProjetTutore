package tool;

import org.apache.commons.collections.map.MultiValueMap;

import java.util.Arrays;
import java.util.List;

/**
 * Created by XH on 26/05/2018.
 */
// this class offers the fonctions for identify the relation between
// resource and group

public class Groupresource {


    public static List<String> Groupenamelist =  Arrays.asList("Location", "Communication", "Peripheral", "Personnalinfo", "Storage", "High risks resource");
    public static List<String> Resnamelist =  Arrays.asList(
            "GPS", "Coarse",
            "MobileData", "Wifi", "Bluetooth", "NFC",
            "Camera","Microphone","Sensors",
            "SMS","Contacts","Phone","Identification"
            ,"Interval","External",
            "draw over application",
           "Automation services","System settings");



    public static MultiValueMap getrelationmultivaluemap(){
        MultiValueMap map = new MultiValueMap();

        for (String resname: Resnamelist){
           map.put(Groupenamelist.get(whichgroup(resname)),resname);
        }



        return  map;

    }

    public static int whichgroup(String resname) {


        switch (resname) {


            case "GPS":
                return 0;


            case "Coarse":
                return 0;


            case "MobileData":
                return 1;

            case "Wifi":
                return 1;

            case "Bluetooth":
                return 1;


            case "NFC":
                return 1;


            case "Camera":
                return 2;


            case "Microphone":
                return 2;


            case "Sensors":
                return 2;

            case "SMS":
                return 3;

            case "Contacts":
                return 3;

            case "Phone":
                return 3;

            case "Identification":
                return 3;

            case "Internal":
                return 4;

            case "External":
                return 4;


            case "draw over application":
                return 5;


            case "Automation services":
                return 5;

            case "System settings":
                return 5;


            default:
                return -1;


        }


    }


}
