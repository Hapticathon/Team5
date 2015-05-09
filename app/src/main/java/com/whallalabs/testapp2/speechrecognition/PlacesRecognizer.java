package com.whallalabs.testapp2.speechrecognition;

import java.util.List;

/**
 * Created by kamil on 09.05.15.
 */
public class PlacesRecognizer {
    public String[] mockPlaces = {"urząd miasta", "lotnisko"};

    public String getRecognizedPlace(List<String> recognizedWords) {
        for (String recognizedWord : recognizedWords) {
            if (isPlaceValid(recognizedWord)) {
                return recognizedWord;
            }
        }
        return null;
    }

    public boolean isPlaceValid(String place) {
        place = place.toLowerCase().trim();
        for (String s : mockPlaces) {
            if (s.equals(place)) {
                return true;
            }
        }
        return false;
    }
}
