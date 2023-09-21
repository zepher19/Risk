package com.myapps.risk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView alaska, northwest_territory, alberta, ontario, quebec,
            western_united_states, eastern_united_states, central_america,
            greenland, venezuela, peru, brazil, argentina, iceland, scandinavia,
            great_britain, western_europe, northern_europe, southern_europe,
            ukraine, north_africa, egypt, east_africa, congo, south_africa,
            madagascar, middle_east, afghanistan, ural, siberia, yakutsk,
            kamchatka, japan, irkutsk, mongolia, china, india, siam, indonesia, new_guinea,
            western_australia, eastern_australia;


    TextSwitcher defenseDie0, defenseDie1, attackDie0, attackDie1, attackDie2;

    BoardModel boardModel;


    TextView defenseDie0TextView0, defenseDie0TextView1, defenseDie1TextView0,
            defenseDie1TextView1, attackDie0TextView0, attackDie0TextView1, 
            attackDie1TextView0, attackDie1TextView1, attackDie2TextView0, attackDie2TextView1;

    TextView line0to0, line0to1, line1to0, line1to1, line2to0, line2to1;

    TextView attackResult, defenseResult;

    String stringAttackResults, stringdefenseResults;
    int intAttackResult, intDefenseResult;


    Button roll;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        boardModel = BoardModel.getInstance();


        line0to0 = findViewById(R.id.line_0_to_0);
        line0to0.setAlpha(0);
        line0to1 = findViewById(R.id.line_0_to_1);
        line0to1.setAlpha(0);
        line1to0 = findViewById(R.id.line_1_to_0);
        line1to0.setAlpha(0);
        line1to1 = findViewById(R.id.line_1_to_1);
        line1to1.setAlpha(0);
        line2to0 = findViewById(R.id.line_2_to_0);
        line2to0.setAlpha(0);
        line2to1 = findViewById(R.id.line_2_to_1);
        line2to1.setAlpha(0);

        attackResult = findViewById(R.id.attack_result_text_view);
        defenseResult = findViewById(R.id.defense_result_text_view);


        roll = findViewById(R.id.roll_button);
        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //first hide any lines
                line0to0.setAlpha(0);
                line0to1.setAlpha(0);

                line1to0.setAlpha(0);
                line1to1.setAlpha(0);

                line2to0.setAlpha(0);
                line2to1.setAlpha(0);

                int[] attackDieValues = {boardModel.rollDie(), boardModel.rollDie(), boardModel.rollDie()};

                int[] defenseDieValues = {boardModel.rollDie(), boardModel.rollDie()};

                int attackHigh;
                int attackHigh1;

                int attackHighPosition;
                int attackHighPosition1;

                int defenseHigh;
                int defenseLow;
                int defenseHighPosition;
                int defenseLowPosition;


                if (attackDieValues[0] >= attackDieValues[1]){
                    attackHigh = attackDieValues[0];
                    attackHigh1 = attackDieValues[1];
                }
                else {
                    attackHigh = attackDieValues[1];
                    attackHigh1 = attackDieValues[0];
                }
                if (attackHigh <= attackDieValues[2]) {
                    attackHigh1 = attackHigh;
                    attackHigh = attackDieValues[2];
                }
                else {
                    if (attackHigh1 <= attackDieValues[2]) {
                        attackHigh1 = attackDieValues[2];
                    }
                }


                attackHighPosition = findFirstHigh(attackDieValues, attackHigh);
                attackHighPosition1 = findSecondHigh(attackDieValues, attackHigh1);


                //find defense info
                if (defenseDieValues[0] >= defenseDieValues[1]) {
                    defenseHigh = defenseDieValues[0];
                    defenseLow = defenseDieValues[1];
                    defenseHighPosition = 0;
                    defenseLowPosition = 1;
                }
                else {
                    defenseHigh = defenseDieValues[1];
                    defenseLow = defenseDieValues[0];
                    defenseHighPosition = 1;
                    defenseLowPosition = 0;
                }
                intDefenseResult = 0;
                intAttackResult = 0;

                int winningColorID;

                if (defenseHigh >= attackHigh) {
                    winningColorID = R.color.white;
                    intAttackResult--;
                }
                else {
                    winningColorID = R.color.red;
                    intDefenseResult--;
                }


                //set lines based on dice rolls
                if (attackHighPosition == 0 && defenseHighPosition == 0) {
                    line0to0.setAlpha(1);
                    line0to0.setBackgroundColor(getResources().getColor(winningColorID));
                }
                if (attackHighPosition == 0 && defenseHighPosition == 1) {
                    line0to1.setAlpha(1);
                    line0to1.setBackgroundColor(getResources().getColor(winningColorID));
                }

                if (attackHighPosition == 1 && defenseHighPosition == 0) {
                    line1to0.setAlpha(1);
                    line1to0.setBackgroundColor(getResources().getColor(winningColorID));

                }
                if (attackHighPosition == 1 && defenseHighPosition == 1) {
                    line1to1.setAlpha(1);
                    line1to1.setBackgroundColor(getResources().getColor(winningColorID));

                }

                if (attackHighPosition == 2 && defenseHighPosition == 0) {
                    line2to0.setAlpha(1);
                    line2to0.setBackgroundColor(getResources().getColor(winningColorID));

                }
                if (attackHighPosition == 2 && defenseHighPosition == 1) {
                    line2to1.setAlpha(1);
                    line2to1.setBackgroundColor(getResources().getColor(winningColorID));
                }

                if (defenseLow >= attackHigh1) {
                    winningColorID = R.color.white;
                    intAttackResult--;

                }
                else {
                    winningColorID = R.color.red;
                    intDefenseResult--;
                }

                attackResult.setText(Integer.toString(intAttackResult));
                attackResult.setGravity(Gravity.CENTER);

                defenseResult.setText(Integer.toString(intDefenseResult));
                defenseResult.setGravity(Gravity.CENTER);


                //set lines based on dice rolls
                if (attackHighPosition1 == 0 && defenseLowPosition == 0) {
                    line0to0.setAlpha(1);
                    line0to0.setBackgroundColor(getResources().getColor(winningColorID));


                }
                if (attackHighPosition1 == 0 && defenseLowPosition == 1) {
                    line0to1.setAlpha(1);
                    line0to1.setBackgroundColor(getResources().getColor(winningColorID));

                }

                if (attackHighPosition1 == 1 && defenseLowPosition == 0) {
                    line1to0.setAlpha(1);
                    line1to0.setBackgroundColor(getResources().getColor(winningColorID));

                }
                if (attackHighPosition1 == 1 && defenseLowPosition == 1) {
                    line1to1.setAlpha(1);
                    line1to1.setBackgroundColor(getResources().getColor(winningColorID));
                }

                if (attackHighPosition1 == 2 && defenseLowPosition == 0) {
                    line2to0.setAlpha(1);
                    line2to0.setBackgroundColor(getResources().getColor(winningColorID));

                }
                if (attackHighPosition1 == 2 && defenseLowPosition == 1) {
                    line2to1.setAlpha(1);
                    line2to1.setBackgroundColor(getResources().getColor(winningColorID));

                }





                defenseDie0.setText(Integer.toString(defenseDieValues[0]));
                defenseDie0TextView0.setGravity(Gravity.CENTER);
                defenseDie0TextView1.setGravity(Gravity.CENTER);

                defenseDie1.setText(Integer.toString(defenseDieValues[1]));
                defenseDie1TextView0.setGravity(Gravity.CENTER);
                defenseDie1TextView1.setGravity(Gravity.CENTER);

                attackDie0.setText(Integer.toString(attackDieValues[0]));
                attackDie0TextView0.setGravity(Gravity.CENTER);
                attackDie0TextView1.setGravity(Gravity.CENTER);

                attackDie1.setText(Integer.toString(attackDieValues[1]));
                attackDie1TextView0.setGravity(Gravity.CENTER);
                attackDie1TextView1.setGravity(Gravity.CENTER);

                attackDie2.setText(Integer.toString(attackDieValues[2]));
                attackDie2TextView0.setGravity(Gravity.CENTER);
                attackDie2TextView1.setGravity(Gravity.CENTER);
            }
        });

        //dice roll
        defenseDie0TextView0 = findViewById(R.id.defense_die_0_text_view0);
        defenseDie0TextView1 = findViewById(R.id.defense_die_0_text_view1);
        defenseDie0 = findViewById(R.id.defense_die_0);
        //animatiomn for die roll
        defenseDie0.setInAnimation(getApplicationContext(), android.R.anim.slide_in_left);
        defenseDie0.setOutAnimation(getApplicationContext(), android.R.anim.slide_out_right);
        defenseDie0.setClickable(true);

        //dice roll
        defenseDie1TextView0 = findViewById(R.id.defense_die_1_text_view0);
        defenseDie1TextView1 = findViewById(R.id.defense_die_1_text_view1);
        defenseDie1 = findViewById(R.id.defense_die_1);
        //animatiomn for die roll
        defenseDie1.setInAnimation(getApplicationContext(), android.R.anim.slide_in_left);
        defenseDie1.setOutAnimation(getApplicationContext(), android.R.anim.slide_out_right);
        defenseDie1.setClickable(true);



        //dice roll
        attackDie0TextView0 = findViewById(R.id.attack_die_0_text_view0);
        attackDie0TextView1 = findViewById(R.id.attack_die_0_text_view1);
        attackDie0 = findViewById(R.id.attack_die_0);
        //animatiomn for die roll
        attackDie0.setInAnimation(getApplicationContext(), android.R.anim.slide_in_left);
        attackDie0.setOutAnimation(getApplicationContext(), android.R.anim.slide_out_right);
        attackDie0.setClickable(true);



        //dice roll
        attackDie1TextView0 = findViewById(R.id.attack_die_1_text_view0);
        attackDie1TextView1 = findViewById(R.id.attack_die_1_text_view1);
        attackDie1 = findViewById(R.id.attack_die_1);
        //animatiomn for die roll
        attackDie1.setInAnimation(getApplicationContext(), android.R.anim.slide_in_left);
        attackDie1.setOutAnimation(getApplicationContext(), android.R.anim.slide_out_right);
        attackDie1.setClickable(true);



        //dice roll
        attackDie2TextView0 = findViewById(R.id.attack_die_2_text_view0);
        attackDie2TextView1 = findViewById(R.id.attack_die_2_text_view1);
        attackDie2 = findViewById(R.id.attack_die_2);
        //animatiomn for die roll
        attackDie2.setInAnimation(getApplicationContext(), android.R.anim.slide_in_left);
        attackDie2.setOutAnimation(getApplicationContext(), android.R.anim.slide_out_right);
        attackDie2.setClickable(true);




        //North America
        alaska = findViewById(R.id.alaska);
        alaska.setClickable(true);
        alaska.setOnClickListener(this);

        northwest_territory = findViewById(R.id.north_west_territory);
        northwest_territory.setClickable(true);
        northwest_territory.setOnClickListener(this);

        alberta = findViewById(R.id.alberta);
        alberta.setClickable(true);
        alberta.setOnClickListener(this);

        ontario = findViewById(R.id.ontario);
        ontario.setClickable(true);
        ontario.setOnClickListener(this);

        quebec = findViewById(R.id.quebec);
        quebec.setClickable(true);
        quebec.setOnClickListener(this);

        western_united_states = findViewById(R.id.western_united_states);
        western_united_states.setClickable(true);
        western_united_states.setOnClickListener(this);

        eastern_united_states = findViewById(R.id.eastern_united_states);
        eastern_united_states.setClickable(true);
        eastern_united_states.setOnClickListener(this);

        central_america = findViewById(R.id.central_america);
        central_america.setClickable(true);
        central_america.setOnClickListener(this);

        greenland = findViewById(R.id.greenland);
        greenland.setClickable(true);
        greenland.setOnClickListener(this);
        //NorthAmerica

        //SouthAmerica
        venezuela = findViewById(R.id.venezuela);
        venezuela.setClickable(true);
        venezuela.setOnClickListener(this);

        peru = findViewById(R.id.peru);
        peru.setClickable(true);
        peru.setOnClickListener(this);

        brazil = findViewById(R.id.brazil);
        brazil.setClickable(true);
        brazil.setOnClickListener(this);

        argentina = findViewById(R.id.argentina);
        argentina.setClickable(true);
        argentina.setOnClickListener(this);
        //South America


        //Europe
        iceland = findViewById(R.id.iceland);
        iceland.setClickable(true);
        iceland.setOnClickListener(this);

        scandinavia = findViewById(R.id.scandinavia);
        scandinavia.setClickable(true);
        scandinavia.setOnClickListener(this);

        great_britain = findViewById(R.id.great_britain);
        great_britain.setClickable(true);
        great_britain.setOnClickListener(this);

        western_europe = findViewById(R.id.western_europe);
        western_europe.setClickable(true);
        western_europe.setOnClickListener(this);

        northern_europe = findViewById(R.id.northern_europe);
        northern_europe.setClickable(true);
        northern_europe.setOnClickListener(this);

        southern_europe = findViewById(R.id.southern_europe);
        southern_europe.setClickable(true);
        southern_europe.setOnClickListener(this);

        ukraine = findViewById(R.id.ukraine);
        ukraine.setClickable(true);
        ukraine.setOnClickListener(this);
        //Europe

        //Africa
        north_africa = findViewById(R.id.north_africa);
        north_africa.setClickable(true);
        north_africa.setOnClickListener(this);

        egypt = findViewById(R.id.egypt);
        egypt.setClickable(true);
        egypt.setOnClickListener(this);

        east_africa = findViewById(R.id.east_africa);
        east_africa.setClickable(true);
        east_africa.setOnClickListener(this);

        congo = findViewById(R.id.congo);
        congo.setClickable(true);
        congo.setOnClickListener(this);

        south_africa = findViewById(R.id.south_africa);
        south_africa.setClickable(true);
        south_africa.setOnClickListener(this);

        madagascar = findViewById(R.id.madagascar);
        madagascar.setClickable(true);
        madagascar.setOnClickListener(this);
        //Africa


        //Asia
        middle_east = findViewById(R.id.middle_east);
        middle_east.setClickable(true);
        middle_east.setOnClickListener(this);

        afghanistan = findViewById(R.id.afghanistan);
        afghanistan.setClickable(true);
        afghanistan.setOnClickListener(this);

        ural = findViewById(R.id.ural);
        ural.setClickable(true);
        ural.setOnClickListener(this);

        siberia = findViewById(R.id.siberia);
        siberia.setClickable(true);
        siberia.setOnClickListener(this);

        yakutsk = findViewById(R.id.yakutsk);
        yakutsk.setClickable(true);
        yakutsk.setOnClickListener(this);

        kamchatka = findViewById(R.id.kamchatka);
        kamchatka.setClickable(true);
        kamchatka.setOnClickListener(this);

        japan = findViewById(R.id.japan);
        japan.setClickable(true);
        japan.setOnClickListener(this);

        irkutsk = findViewById(R.id.irkutsk);
        irkutsk.setClickable(true);
        irkutsk.setOnClickListener(this);

        mongolia = findViewById(R.id.mongolia);
        mongolia.setClickable(true);
        mongolia.setOnClickListener(this);

        china = findViewById(R.id.china);
        china.setClickable(true);
        china.setOnClickListener(this);

        india = findViewById(R.id.india);
        india.setClickable(true);
        india.setOnClickListener(this);

        siam = findViewById(R.id.siam);
        siam.setClickable(true);
        siam.setOnClickListener(this);

        indonesia = findViewById(R.id.indonesia);
        indonesia.setClickable(true);
        indonesia.setOnClickListener(this);
        //Asia

        //Australia
        new_guinea = findViewById(R.id.new_guinea);
        new_guinea.setClickable(true);
        new_guinea.setOnClickListener(this);

        western_australia = findViewById(R.id.western_australia);
        western_australia.setClickable(true);
        western_australia.setOnClickListener(this);

        eastern_australia = findViewById(R.id.eastern_australia);
        eastern_australia.setClickable(true);
        eastern_australia.setOnClickListener(this);
        //Australia



    }

    private int findSecondHigh(int[] dieValues, int attackHigh) {
        for (int i = 2; i >= 0; i--) {
            if (dieValues[i] == attackHigh) {
                return i;
            }
        }
        return 0;
    }

    private int findFirstHigh(int[] dieValues, int attackHigh) {
        for (int i = 0; i < dieValues.length; i++) {
            if (dieValues[i] == attackHigh) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public void onClick(View v) {


        /*

        //print name for test
        String regionName = v.getResources().getResourceName(v.getId());
        regionName = regionName.substring(19);
        regionName = regionName.replace('_', ' ');
        regionName = regionName.toUpperCase();
        //Toast.makeText(this, regionName, Toast.LENGTH_SHORT).show();


        //Toast.makeText(this, boardModel.getRegionFromView(v).getConnected()[0], Toast.LENGTH_SHORT).show();


         */


        Region localRegion = boardModel.getRegionFromView(v);
        boardModel.increaseUnitCountByOne(localRegion);
        updateUIUnitCount(localRegion);
    }

    private void updateUIUnitCount(Region localRegion) {
        TextView localView = findViewById(localRegion.getViewID());
        localView.setText(Integer.toString(localRegion.getUnitCount()));
        localView.setGravity(Gravity.CENTER);
    }


    void updateUI(Region regionToChange) {

    }
}