package com.whallalabs.testapp2.utils;

import com.whallalabs.testapp2.R;

/**
 * Created by Jarek on 09.05.15.
 */
public class MapFactory {
    public enum MapType{
        OFFICE_DATA,
        OFFICE_DISPLAY,
        AIRPORT_DATA,
        AIRPORT_DISPLAY
    }

    public static Integer[][] getMap(MapType mapType){
        Integer[][] result = null;
        switch(mapType){
            case OFFICE_DATA:{
                result = createOfficeDataMap();
                break;
            }
            case OFFICE_DISPLAY:{
                result = createOfficeDisplayMap();
                break;
            }
            case AIRPORT_DATA:{
                result = createAirportDataMap();
                break;
            }
            case AIRPORT_DISPLAY:{
                result = createAiportDisplayMap();
                break;
            }

        }

        return result;
    }

    private static Integer[][] createOfficeDataMap(){
        Integer[][] result = new Integer[][]{{null, R.drawable.o_2,null},
                {R.drawable.o_1,R.drawable.o_3,R.drawable.o_5},
                {null,R.drawable.o_4,null}};

        return result;
    }

    private static Integer[][] createOfficeDisplayMap(){
        Integer[][] result = new Integer[][]{{null, R.drawable.o2_2,null},
                {R.drawable.o2_1,R.drawable.o2_3,R.drawable.o2_5},
                {null,R.drawable.o2_4,null}};

        return result;
    }

    private static Integer[][] createAirportDataMap(){
        Integer[][] result = new Integer[][]{{null, R.drawable.a_2,null},
                {R.drawable.a_1,R.drawable.a_3,R.drawable.a_5},
                {null,R.drawable.a_4,null}};

        return result;
    }

    private static Integer[][] createAiportDisplayMap(){
        Integer[][] result = new Integer[][]{{null, R.drawable.a2_2,null},
                {R.drawable.a2_1,R.drawable.a2_3,R.drawable.a2_5},
                {null,R.drawable.a2_4,null}};

        return result;
    }

}
