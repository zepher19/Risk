package com.myapps.risk;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class BoardModel {

    private final String[] turnPhases = {"Reinforce", "Attack", "Fortify"};
    private String currentPhase = "Reinforce";


    private final String[] gamePhases = {"Setup", "Play"};
    private String currentGamePhase = "Setup";


    private final Region[] regionArray;

    private char currentPlayerTurn;

    private final char[] players = {'b', 'o', 'p', 'g'};


    private static final BoardModel instance = new BoardModel();

    // private constructor to avoid client applications using the constructor
    private BoardModel(){

        //initialize all regions with View ID
        Region alaskaReg = new Region(R.id.alaska);
        Region north_west_territoryReg = new Region (R.id.north_west_territory);
        Region albertaReg = new Region(R.id.alberta);
        Region ontarioReg = new Region(R.id.ontario);
        Region quebecReg = new Region(R.id.quebec);
        Region greenlandReg = new Region(R.id.greenland);
        Region western_united_statesReg = new Region(R.id.western_united_states);
        Region eastern_united_statesReg = new Region(R.id.eastern_united_states);
        Region central_americaReg = new Region(R.id.central_america);

        Region venezuelaReg = new Region(R.id.venezuela);
        Region peruReg = new Region(R.id.peru);
        Region brazilReg = new Region(R.id.brazil);
        Region argentinaReg = new Region(R.id.argentina);

        Region icelandReg = new Region(R.id.iceland);
        Region great_britainReg = new Region(R.id.great_britain);
        Region scandinaviaReg = new Region(R.id.scandinavia);
        Region ukraineReg = new Region(R.id.ukraine);
        Region northern_europeReg = new Region(R.id.northern_europe);
        Region southern_europeReg = new Region(R.id.southern_europe);
        Region western_europeReg = new Region(R.id.western_europe);

        Region north_africaReg = new Region(R.id.north_africa);
        Region egyptReg = new Region(R.id.egypt);
        Region east_africaReg = new Region(R.id.east_africa);
        Region congoReg = new Region(R.id.congo);
        Region south_africaReg = new Region(R.id.south_africa);
        Region madagascarReg = new Region(R.id.madagascar);

        Region middle_eastReg = new Region(R.id.middle_east);
        Region afghanistanReg = new Region(R.id.afghanistan);
        Region uralReg = new Region(R.id.ural);
        Region siberiaReg = new Region(R.id.siberia);
        Region yakutskReg = new Region(R.id.yakutsk);
        Region kamchatkaReg = new Region(R.id.kamchatka);
        Region japanReg = new Region(R.id.japan);
        Region irkutskReg = new Region(R.id.irkutsk);
        Region mongoliaReg = new Region(R.id.mongolia);
        Region chinaReg = new Region(R.id.china);
        Region indiaReg = new Region(R.id.india);
        Region siamReg = new Region(R.id.siam);

        Region indonesiaReg = new Region(R.id.indonesia);
        Region new_guineaReg = new Region(R.id.new_guinea);
        Region western_australiaReg = new Region(R.id.western_australia);
        Region eastern_australiaReg = new Region(R.id.eastern_australia);


        //set connected Regions
        alaskaReg.setConnected(new Region[]{north_west_territoryReg, albertaReg, kamchatkaReg});
        north_west_territoryReg.setConnected(new Region[]{alaskaReg, albertaReg, ontarioReg, greenlandReg});
        albertaReg.setConnected(new Region[]{alaskaReg, north_west_territoryReg, ontarioReg, western_united_statesReg});
        ontarioReg.setConnected(new Region[]{north_west_territoryReg, albertaReg, western_united_statesReg, eastern_united_statesReg, quebecReg, greenlandReg});
        quebecReg.setConnected(new Region[]{ontarioReg, eastern_united_statesReg, greenlandReg});
        greenlandReg.setConnected(new Region[]{icelandReg, quebecReg, ontarioReg, north_west_territoryReg});
        western_united_statesReg.setConnected(new Region[]{albertaReg, ontarioReg, eastern_united_statesReg, central_americaReg});
        eastern_united_statesReg.setConnected(new Region[]{western_united_statesReg, central_americaReg, quebecReg, ontarioReg});
        central_americaReg.setConnected(new Region[]{western_united_statesReg, eastern_united_statesReg, venezuelaReg});

        venezuelaReg.setConnected(new Region[]{central_americaReg, peruReg, brazilReg});
        peruReg.setConnected(new Region[]{venezuelaReg, argentinaReg, brazilReg});
        brazilReg.setConnected(new Region[]{venezuelaReg, peruReg, argentinaReg, north_africaReg});
        argentinaReg.setConnected(new Region[]{peruReg, brazilReg});

        icelandReg.setConnected(new Region[]{greenlandReg, great_britainReg, scandinaviaReg});
        great_britainReg.setConnected(new Region[]{icelandReg, western_europeReg, northern_europeReg, scandinaviaReg});
        scandinaviaReg.setConnected(new Region[]{icelandReg, great_britainReg, ukraineReg, northern_europeReg});
        ukraineReg.setConnected(new Region[]{uralReg, afghanistanReg, middle_eastReg, northern_europeReg, southern_europeReg, scandinaviaReg});
        northern_europeReg.setConnected(new Region[]{scandinaviaReg, ukraineReg, southern_europeReg, western_europeReg, great_britainReg});
        southern_europeReg.setConnected(new Region[]{northern_europeReg, ukraineReg, western_europeReg, middle_eastReg, egyptReg, north_africaReg});
        western_europeReg.setConnected(new Region[]{great_britainReg, northern_europeReg, southern_europeReg, north_africaReg});

        north_africaReg.setConnected(new Region[]{egyptReg, brazilReg, congoReg, east_africaReg, western_europeReg, southern_europeReg});
        egyptReg.setConnected(new Region[]{southern_europeReg, middle_eastReg, east_africaReg, north_africaReg});
        east_africaReg.setConnected(new Region[]{north_africaReg, egyptReg, middle_eastReg, madagascarReg, south_africaReg, congoReg});
        congoReg.setConnected(new Region[]{north_africaReg, east_africaReg, south_africaReg});
        south_africaReg.setConnected(new Region[]{congoReg, east_africaReg, madagascarReg});
        madagascarReg.setConnected(new Region[]{south_africaReg, east_africaReg});

        middle_eastReg.setConnected(new Region[]{southern_europeReg, ukraineReg, afghanistanReg, egyptReg, east_africaReg, indiaReg});
        afghanistanReg.setConnected(new Region[]{ukraineReg, uralReg, chinaReg, indiaReg, middle_eastReg});
        uralReg.setConnected(new Region[]{ukraineReg, siberiaReg, chinaReg, afghanistanReg});
        siberiaReg.setConnected(new Region[]{uralReg, yakutskReg, irkutskReg, mongoliaReg, chinaReg});
        yakutskReg.setConnected(new Region[]{siberiaReg, kamchatkaReg, irkutskReg});
        kamchatkaReg.setConnected(new Region[]{yakutskReg, irkutskReg, mongoliaReg, japanReg, alaskaReg});
        japanReg.setConnected(new Region[]{kamchatkaReg, mongoliaReg});
        irkutskReg.setConnected(new Region[]{siberiaReg, yakutskReg, kamchatkaReg, mongoliaReg});
        mongoliaReg.setConnected(new Region[]{siberiaReg, irkutskReg, kamchatkaReg, japanReg, chinaReg});
        chinaReg.setConnected(new Region[]{afghanistanReg, uralReg, siberiaReg, mongoliaReg, siamReg, indiaReg});
        indiaReg.setConnected(new Region[]{middle_eastReg, afghanistanReg, chinaReg, siamReg});
        siamReg.setConnected(new Region[]{indiaReg, chinaReg, indonesiaReg});

        indonesiaReg.setConnected(new Region[]{siamReg, new_guineaReg, western_australiaReg});
        new_guineaReg.setConnected(new Region[]{indonesiaReg, eastern_australiaReg, western_australiaReg});
        western_australiaReg.setConnected(new Region[]{indonesiaReg, new_guineaReg, eastern_australiaReg});
        eastern_australiaReg.setConnected(new Region[]{new_guineaReg, western_australiaReg});


        //stick regions into region array
        regionArray = new Region[]{alaskaReg, north_west_territoryReg, albertaReg, ontarioReg, quebecReg, greenlandReg,
                western_united_statesReg, eastern_united_statesReg, central_americaReg, venezuelaReg,
                peruReg, brazilReg, argentinaReg, icelandReg, great_britainReg, scandinaviaReg, ukraineReg, northern_europeReg, southern_europeReg, western_europeReg,
        north_africaReg, egyptReg, east_africaReg, congoReg, south_africaReg, madagascarReg, middle_eastReg, afghanistanReg, uralReg, siberiaReg,
        yakutskReg, kamchatkaReg, japanReg, irkutskReg, mongoliaReg, chinaReg, indiaReg, siamReg, indonesiaReg, new_guineaReg, western_australiaReg, eastern_australiaReg};

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

    char randomizePlayer() {
        Random random = new Random();
        return players[random.nextInt(4)];
    }

    public void setCurrentPlayerTurn(char currentPlayerTurn) {
        this.currentPlayerTurn = currentPlayerTurn;
    }

    public char getCurrentPlayerTurn() {
        return currentPlayerTurn;
    }

    void switchPlayerTurn()  {
        if (currentPlayerTurn == 'b') {
            currentPlayerTurn = 'o';
            return;
        }
        if (currentPlayerTurn == 'o') {
            currentPlayerTurn = 'p';
            return;
        }
        if (currentPlayerTurn == 'p') {
            currentPlayerTurn = 'g';
            return;
        }
        if (currentPlayerTurn == 'g') {
            currentPlayerTurn = 'b';
        }
    }





    public void changePhase() {
        if (currentPhase.equals("Reinforce")) {
            currentPhase = "Attack";
            return;
        }
        if (currentPhase.equals("Attack")) {
            currentPhase = "Fortify";
            return;
        }
        if (currentPhase.equals("Fortify")) {
            currentPhase = "Reinforce";
        }
    }

    public String getCurrentPhase() {
        return currentPhase;
    }


    public void setCurrentGamePhase(String currentGamePhase) {
        this.currentGamePhase = currentGamePhase;
    }

    public String getCurrentGamePhase() {
        return currentGamePhase;
    }

    void changeGamePhase() {
        if (currentGamePhase.equals("Setup")) {
            currentGamePhase = "Play";
            return;
        }
        if (currentGamePhase.equals("Play")) {
            currentGamePhase = "Setup";
        }
    }


    public Region[] getRegionArray() {
        return regionArray;
    }
}
