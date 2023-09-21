package com.myapps.risk;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class BoardModel {

    private Region[] regionArray;

    private char currentPlayerTurn;

    private char[] players;


    private Region alaskaReg, north_west_territoryReg, albertaReg, ontarioReg, quebecReg,
            western_united_statesReg, eastern_united_statesReg, central_americaReg,
            greenlandReg, venezuelaReg, peruReg, brazilReg, argentinaReg, icelandReg, scandinaviaReg,
            great_britainReg, western_europeReg, northern_europeReg, southern_europeReg,
            ukraineReg, north_africaReg, egyptReg, east_africaReg, congoReg, south_africaReg,
            madagascarReg, middle_eastReg, afghanistanReg, uralReg, siberiaReg, yakutskReg,
            kamchatkaReg, japanReg, irkutskReg, mongoliaReg, chinaReg, indiaReg, siamReg, indonesiaReg, new_guineaReg,
            western_australiaReg, eastern_australiaReg;


    private String alaska, north_west_territory, alberta, ontario, quebec,
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
        north_west_territory = "NORTH WEST TERRITORY";
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

        //initialize all regions with connections and View ID
        alaskaReg = new Region(new String[]{north_west_territory, alberta, kamchatka}, R.id.alaska);
        north_west_territoryReg = new Region(new String[]{alaska, alberta, ontario, greenland}, R.id.north_west_territory);
        albertaReg = new Region(new String[]{alaska, north_west_territory, ontario, western_united_states}, R.id.alberta);
        ontarioReg = new Region(new String[]{north_west_territory, alberta, western_united_states, eastern_united_states, quebec, greenland}, R.id.ontario);
        quebecReg = new Region(new String[]{ontario, eastern_united_states, greenland}, R.id.quebec);
        greenlandReg = new Region(new String[]{iceland, quebec, ontario, north_west_territory}, R.id.greenland);
        western_united_statesReg = new Region(new String[]{alberta, ontario, eastern_united_states, central_america}, R.id.western_united_states);
        eastern_united_statesReg = new Region(new String[]{western_united_states, central_america, quebec, ontario}, R.id.eastern_united_states);
        central_americaReg = new Region(new String[]{western_united_states, eastern_united_states, venezuela}, R.id.central_america);


        venezuelaReg = new Region(new String[]{central_america, peru, brazil}, R.id.venezuela);
        peruReg = new Region(new String[]{venezuela, argentina, brazil}, R.id.peru);
        brazilReg = new Region(new String[]{venezuela, peru, argentina, north_africa}, R.id.brazil);
        argentinaReg = new Region(new String[]{peru, brazil}, R.id.argentina);




        //stick regions into region array
        regionArray = new Region[]{alaskaReg, north_west_territoryReg, albertaReg, ontarioReg, quebecReg, greenlandReg,
                western_united_statesReg, eastern_united_statesReg, central_americaReg, venezuelaReg,
        peruReg, brazilReg, argentinaReg};

    }

    public static BoardModel getInstance() {
        return instance;
    }


    private final int[] dieNumbers = {1, 2, 3, 4, 5, 6};

    public int rollDie() {
        Random random = new Random();
        return dieNumbers[random.nextInt(6)];
    }


    public Region getRegionFromView(View v) {
        for (int i = 0; i < regionArray.length; i++) {
            if (regionArray[i].getViewID() == v.getId()) {
                return regionArray[i];
            }
        }
        return null;
    }


    //used when setting up the board an placing pieces one by one
    public void increaseUnitCountByOne(Region regionToChange) {
        regionToChange.setUnitCount(regionToChange.getUnitCount() + 1);
    }
}
