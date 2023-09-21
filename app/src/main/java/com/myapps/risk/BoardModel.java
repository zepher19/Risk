package com.myapps.risk;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class BoardModel {

    private Region[] regionArray;


    private String alaska, northwest_territory, alberta, ontario, quebec,
            western_united_states, eastern_united_states, central_america,
            greenland, venezuela, peru, brazil, argentina, iceland, scandinavia,
            great_britain, western_europe, northern_europe, southern_europe,
            ukraine, north_africa, egypt, east_africa, congo, south_africa,
            madagascar, middle_east, afghanistan, ural, siberia, yakutsk,
            kamchatka, japan, irkutsk, mongolia, china, india, siam, indonesia, new_guinea,
            western_australia, eastern_australia;


    private static final BoardModel instance = new BoardModel();

    // private constructor to avoid client applications using the constructor
    private BoardModel(){
        alaska = "ALASKA";
        northwest_territory = "NORTH WEST TERRITORY";
        alberta = "ALBERTA";
        ontario = "ONTARIO";
        quebec = "QUEBEC";
        western_united_states = "WESTERN UNITED STATES";
        eastern_united_states = "EASTERN UNITED STATES";
        central_america = "CENTRAL AMERICA";
        greenland = "GREENLAND";
        venezuela = "VENEZUELA";
        peru = "PERU";
        brazil = "BRAZIL";
        argentina = "ARGENTINA";
        iceland = "ICELAND";
        scandinavia = "SCANDINAVIA";
        great_britain = "GREAT BRITAIN";
        western_europe = "WESTERN EUROPE";
        northern_europe = "NORTHERN EUROPE";
        southern_europe = "SOUTHERN EUROPE";
        ukraine = "UKRAINE";
        north_africa = "NORTH AFRICA";
        egypt = "EGYPT";
        east_africa = "EAST AFRICA";
        congo = "CONGO";
        south_africa = "SOUTH AFRICA";
        madagascar = "MADAGASCAR";
        middle_east = "MIDDLE EAST";
        afghanistan = "AFGHANISTAN";
        ural = "URAL";
        siberia = "SIBERIA";
        yakutsk = "YAKUTSK";
        kamchatka = "KAMCHATKA";
        japan = "JAPAN";
        irkutsk = "IRKUTSK";
        mongolia = "MONGOLIA";
        china = "CHINA";
        india = "INDIA";
        siam = "SIAM";
        indonesia = "INDONESIA";
        new_guinea = "NEW GUINEA";
        western_australia = "WESTERN AUSTRALIA";
        eastern_australia = "EASTERN AUSTRALIA";

        Region alaskaReg = new Region(new String[]{northwest_territory, alberta, kamchatka}, R.id.alaska);
        Region north_west_territoryReg = new Region(new String[]{alaska, alberta, ontario, greenland}, R.id.north_west_territory);
        Region albertaReg = new Region(new String[]{alaska, northwest_territory, ontario, western_united_states}, R.id.alberta);
        Region ontarioReg = new Region(new String[]{northwest_territory, alberta, western_united_states, eastern_united_states, quebec, greenland}, R.id.ontario);
        Region quebecReg = new Region(new String[]{ontario, eastern_united_states, greenland}, R.id.quebec);
        Region greenlandReg = new Region(new String[]{iceland, quebec, ontario, northwest_territory}, R.id.greenland);
        Region western_united_statesReg = new Region(new String[]{alberta, ontario, eastern_united_states, central_america}, R.id.western_united_states);
        Region eastern_united_statesReg = new Region(new String[]{western_united_states, central_america, quebec, ontario}, R.id.eastern_united_states);
        Region central_americaReg = new Region(new String[]{western_united_states, eastern_united_states, venezuela}, R.id.central_america);

        regionArray = new Region[]{alaskaReg, north_west_territoryReg, albertaReg, ontarioReg, quebecReg, greenlandReg,
                western_united_statesReg, eastern_united_statesReg, central_americaReg};

    }

    public static BoardModel getInstance() {
        return instance;
    }


    private final int[] dieNumbers = {1, 2, 3, 4, 5, 6};

    public int rollDie() {
        Random random = new Random();
        return dieNumbers[random.nextInt(6)];
    }


    public Region getRegionFromViewID(View v) {
        for (int i = 0; i < regionArray.length; i++) {
            if (regionArray[i].getViewID() == v.getId()) {
                return regionArray[i];
            }
        }
        return null;
    }


    public void increaseUnitCount(View v) {
    }
}
