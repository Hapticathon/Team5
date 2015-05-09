package com.whallalabs.testapp2.utils;

import com.whallalabs.testapp2.R;

/**
 * Created by Jarek on 09.05.15.
 */
public class MapFactory {
    public enum MapType{
        HOUSE,
        BEACON
    }

    public static Integer[][] getMap(MapType mapType){
        Integer[][] result = null;
        switch(mapType){
            case HOUSE:{
                result = createHouseMap();
                break;
            }
            case BEACON:{
                result = createBeaconMap();
            }
        }

        return result;
    }

    private static Integer[][]  createHouseMap(){
        Integer[][] result = new Integer[][]{{null, R.drawable.h_1,null},
                {R.drawable.h_2,R.drawable.h_3,R.drawable.h_4},
                {null,R.drawable.h_5,null}};

        return result;
    }

    private static Integer[][]  createBeaconMap(){
        Integer[][] result = new Integer[][]{{null, R.drawable.h_1,null},
                {R.drawable.h_2,R.drawable.h_3,R.drawable.h_4},
                {null,R.drawable.h_5,null}};

        return result;
    }

}
