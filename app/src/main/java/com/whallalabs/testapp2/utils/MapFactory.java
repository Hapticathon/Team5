package com.whallalabs.testapp2.utils;

/**
 * Created by Jarek on 09.05.15.
 */
public class MapFactory {
    public enum MapType{
        HOUSE,
        BEACON
    }

    public static String[][] getMap(MapType mapType){
        String[][] result = null;
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

    private static String[][]  createHouseMap(){
        String[][] result = new String[][]{{null,"h_1",null},{"h_2","h_3","h_4"},{null,"h_5",null}};

        return result;
    }

    private static String[][]  createBeaconMap(){
        String[][] result = new String[][]{{null,"b_1",null},{"b_2","b_3","b_4"},{null,"b_5",null}};

        return result;
    }

}
