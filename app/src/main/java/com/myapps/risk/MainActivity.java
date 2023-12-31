package com.myapps.risk;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

    int intAttackResult = 0, intDefenseResult = 0;

    Button roll;

    ImageView currentPlayerGraphic;

    int unitPlacementCounter = 120;


    TextView textView0, textView1;


    Region fortifier, attacker, defender;

    boolean attacking = false;

    Button endAttack;

    int occupyNumber, fortifyNumber, reinforcements;

    boolean firstReinforceOfTurn = true;

    String occupyNumberString = "-5", fortifyNumberString = "-5";


    TextView gamePhaseTextView, unitPlacementCounterTextView;

    TextSwitcher turnPhaseTextView;

    boolean setupFirstPlacement = true, firstFortify = true;

    TextView reinforcement_counter_text_view;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        boardModel = BoardModel.getInstance();

        reinforcement_counter_text_view = findViewById(R.id.reinforcement_count_text_view);
        reinforcement_counter_text_view.setAlpha(0);

        unitPlacementCounterTextView = findViewById(R.id.unit_place_counter_text_view);

        gamePhaseTextView = findViewById(R.id.game_phase_text_view);
        turnPhaseTextView = findViewById(R.id.turn_phase_text_view);
        turnPhaseTextView.setInAnimation(getApplicationContext(), android.R.anim.slide_in_left);
        turnPhaseTextView.setOutAnimation(getApplicationContext(), android.R.anim.slide_out_right);
        turnPhaseTextView.setAlpha(0);
        textView0 = findViewById(R.id.text_view_0);
        textView1 = findViewById(R.id.text_view_1);


        endAttack = findViewById(R.id.end_attack_button);
        endAttack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endAttacking();
            }
        });









        //TODO working here
        currentPlayerGraphic = findViewById(R.id.current_player_graphic);
        currentPlayerGraphic.setClickable(true);
        currentPlayerGraphic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if in setup
                if (boardModel.getCurrentGamePhase().equals("Setup")) {
                    //setup first placement needs to be false to ensure the previous player set a unit down
                    if (!setupFirstPlacement) {
                        //if there are blank spots
                        if (unitPlacementCounter != 78) {
                            boardModel.switchPlayerTurn();
                            updateCurrentPlayerGraphic();
                            setupFirstPlacement = true;
                        }
                        //if there are no blank spots
                        else {
                            boardModel.setCurrentGamePhase("Place");
                            gamePhaseTextView.setText(boardModel.getCurrentGamePhase());
                            gamePhaseTextView.setGravity(Gravity.CENTER);
                            boardModel.switchPlayerTurn();
                            updateCurrentPlayerGraphic();
                            setupFirstPlacement = true;
                        }
                    }
                }

                //if in placing additional units phase
                else if (boardModel.getCurrentGamePhase().equals("Place")) {
                    //setup first placement needs to be false to ensure the previous player set a unit down
                    if (!setupFirstPlacement) {
                        //if there are still units to place, place them
                        if (unitPlacementCounter != 0) {
                            boardModel.switchPlayerTurn();
                            updateCurrentPlayerGraphic();
                            setupFirstPlacement = true;
                        }
                        //if all units have been placed, switch to play mode
                        else {
                            boardModel.setCurrentGamePhase("Play");
                            //turn on and off the various UI elements
                            turnPhaseTextView.setAlpha(1);
                            gamePhaseTextView.setAlpha(0);
                            unitPlacementCounterTextView.setAlpha(0);

                            startTurn();

                        }
                    }
                }


                //if in play
                else {
                    fortifyUnhighlight();
                    unhighlight();
                    //if fortify pressing the button sends you into the next turn
                    if (boardModel.getCurrentPhase().equals("Fortify")) {
                        startTurn();
                    }
                    //if not in fortify pressing the button sends you to next phase
                    else {
                        if(boardModel.getCurrentPhase().equals("Reinforce")) {
                            reinforcement_counter_text_view.setAlpha(0);
                            reinforcement_counter_text_view.setGravity(Gravity.CENTER);
                        }
                        //clear attack UI if player left it up
                        endAttacking();
                        //remove players if players defeated
                        //remove player operation needs to be outside of occupy, causes concurrency problem
                        boardModel.removePlayer();
                        //check if game is over if only one player left
                        isGameOver();

                        boardModel.switchCurrentPhase();
                        turnPhaseTextView.setText(boardModel.getCurrentPhase());
                        textView0.setGravity(Gravity.CENTER);
                        textView1.setGravity(Gravity.CENTER);
                    }
                }
            }
        });






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

                //attacker must have at least 3 units to prevent running out during combat
                if (attacker.getUnitCount() < 3) {
                    Toast.makeText(MainActivity.this, "Must have at least 3 units to attack.", Toast.LENGTH_SHORT).show();
                    return;
                }


                //first hide any lines from previous combat
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

                //reset values
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

                calculateLosses();



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


        startGame();



    }

    private void endAttacking() {
        hideAttackUI();
        unhighlight();
        attacking = false;
    }

    private void isGameOver() {
        if (boardModel.playerTurns.size() == 1) {
            String winner = "Everyone";

            if (boardModel.playerTurns.get(0) == 'b') {
                winner = "Blue";
            }
            if (boardModel.playerTurns.get(0) == 'o') {
                winner = "Orange";
            }
            if (boardModel.playerTurns.get(0) == 'p') {
                winner = "Purple";
            }
            if (boardModel.playerTurns.get(0) == 'g') {
                winner = "Green";
            }


            // Create the object of AlertDialog Builder class
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            // Set the message show for the Alert time
            builder.setMessage(winner + " wins!");

            // Set Alert Title
            builder.setTitle("Game Over");

            // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
            builder.setCancelable(false);


            // Create the Alert dialog
            AlertDialog alertDialog = builder.create();
            // Show the Alert Dialog box
            alertDialog.show();









        }
    }

    private void calculateReinforcements() {
        reinforcements = boardModel.countControlledRegions() / 3;

        //determine continent bonus if applicable
        boardModel.getContinentBonus();

        if (boardModel.northAmericaBonus) {
            reinforcements = reinforcements + boardModel.NORTH_AMERICA_BONUS_VALUE;
        }
        if (boardModel.southAmericaBonus) {
            reinforcements = reinforcements + boardModel.SOUTH_AMERICA_BONUS_VALUE;
        }
        if (boardModel.europeBonus) {
            reinforcements = reinforcements + boardModel.EUROPE_BONUS_VALUE;
        }
        if (boardModel.africaBonus) {
            reinforcements = reinforcements + boardModel.AFRICA_BONUS_VALUE;
        }
        if (boardModel.asiaBonus) {
            reinforcements = reinforcements + boardModel.ASIA_BONUS_VALUE;
        }
        if (boardModel.australiaBonus) {
            reinforcements = reinforcements + boardModel.AUSTRALIA_BONUS_VALUE;
        }

        firstReinforceOfTurn = false;

        //only show reinforcement counter during play phase
        if (boardModel.getCurrentGamePhase().equals("Play")) {
            reinforcement_counter_text_view.setText(Integer.toString(reinforcements));
            reinforcement_counter_text_view.setAlpha(1);
            reinforcement_counter_text_view.setGravity(Gravity.CENTER);
        }
    }

    private boolean scanForNumUnits() {
        int counter = 0;
        for (int i = 0; i < boardModel.getRegionArray().length; i++) {
            counter = boardModel.getRegionArray()[i].getUnitCount() + counter;
        }

        if (counter == 120) {
            return false;
        }
        else {
            return true;
        }
    }

    //return false if there are no more blank spots
    private boolean scanForBlankSpots() {
        int counter = 0;
        for (int i = 0; i < boardModel.getRegionArray().length; i++) {
            if (boardModel.getRegionArray()[i].getColorControl() == '0') {
                counter++;
            }
        }
        if (counter == 0) {
            return false;
        }
        return true;
    }

    private void calculateLosses() {
        int attackCountAfterLoss = attacker.getUnitCount() + intAttackResult;
        int defenseCountAfterLoss = defender.getUnitCount() + intDefenseResult;

        //prevent negative unit counts
        if (attackCountAfterLoss <= 1) {
            attackCountAfterLoss = 1;
        }

        //if defender count is zero move attacker forces in and occupy
        if (defenseCountAfterLoss <= 0) {
            defenseCountAfterLoss = 0;
            attacker.setUnitCount(attackCountAfterLoss);
            defender.setUnitCount(defenseCountAfterLoss);
            occupy();
            return;
        }


        attacker.setUnitCount(attackCountAfterLoss);
        defender.setUnitCount(defenseCountAfterLoss);
        updateUI();
    }

    private void checkIfPlayerDefeated() {
        int blueCounter = 0;
        int orangeCounter = 0;
        int purpleCounter = 0;
        int greenCounter = 0;

        for (int i = 0; i < boardModel.getRegionArray().length; i++) {
            if (boardModel.getRegionArray()[i].getColorControl() == 'b') {
                blueCounter++;
            }
            if (boardModel.getRegionArray()[i].getColorControl() == 'o') {
                orangeCounter++;
            }
            if (boardModel.getRegionArray()[i].getColorControl() == 'p') {
                purpleCounter++;
            }
            if (boardModel.getRegionArray()[i].getColorControl() == 'g') {
                greenCounter++;
            }
        }

        if (blueCounter == 0) {
            if (!boardModel.blueDefeated) {
                Toast.makeText(this, "Blue has been defeated!", Toast.LENGTH_SHORT).show();
                boardModel.blueDefeated = true;
            }
        }
        if (orangeCounter == 0) {
            if (!boardModel.orangeDefeated) {
                Toast.makeText(this, "Orange has been defeated!", Toast.LENGTH_SHORT).show();
                boardModel.orangeDefeated = true;

            }
        }
        if (purpleCounter == 0) {
            if (!boardModel.purpleDefeated) {
                Toast.makeText(this, "Purple has been defeated!", Toast.LENGTH_SHORT).show();
                boardModel.purpleDefeated = true;
            }
        }
        if (greenCounter == 0) {
            if (!boardModel.greenDefeated) {
                Toast.makeText(this, "Green has been defeated!", Toast.LENGTH_SHORT).show();
                boardModel.greenDefeated = true;
            }
        }
    }

    private void occupy() {
        // Create the object of AlertDialog Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Set the message show for the Alert time
        builder.setMessage("Select the number of units you wish to occupy the territory. You have " +
                Integer.toString(attacker.getUnitCount()) + " total units. You may move between 1 and " + Integer.toString(attacker.getUnitCount() - 1) + ".");

        // Set Alert Title
        builder.setTitle("Defender is out of units!");

        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(false);

        //create edit text for player to enter number of troops to move
        EditText editText = new EditText(this);
        editText.setHint("1 to " + Integer.toString(attacker.getUnitCount() - 1));

        //to only allow numbers to be entered
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //reset value to default
                occupyNumberString = "-5";
                occupyNumberString = s.toString();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        builder.setView(editText);

        builder.setNegativeButton("Done", (DialogInterface.OnClickListener) (dialog, which) -> {
            occupyNumber = Integer.parseInt(occupyNumberString);
            if (occupyNumber > 0 && occupyNumber < attacker.getUnitCount()) {
                dialog.cancel();
                occupyNumber = Integer.parseInt(occupyNumberString);
                defender.setColorControl(attacker.getColorControl());
                defender.setUnitCount(occupyNumber);
                attacker.setUnitCount(attacker.getUnitCount() - occupyNumber);
                unhighlight();
                hideAttackUI();
                updateUI();
                attacking = false;
                checkIfPlayerDefeated();
            }
            else {
                Toast.makeText(this, "Value must be in range", Toast.LENGTH_SHORT).show();
                occupy();
            }
        });



        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();
        // Show the Alert Dialog box
        alertDialog.show();
    }


    private void startGame() {
        gamePhaseTextView.setText(boardModel.getCurrentGamePhase());
        gamePhaseTextView.setGravity(Gravity.CENTER);
        turnPhaseTextView.setText(boardModel.getCurrentPhase());
        textView0.setGravity(Gravity.CENTER);
        textView1.setGravity(Gravity.CENTER);

        hideAttackUI();
        boardModel.setCurrentPlayerTurn(boardModel.randomizePlayer());
        unitPlacementCounterTextView.setText(Integer.toString(unitPlacementCounter));
        unitPlacementCounterTextView.setGravity(Gravity.CENTER);
        startTurn();
    }


    private void startTurn() {
        //to make coding easier update graphic after player turn switches for the first time,
        // instead of on randomize
        boardModel.switchPlayerTurn();
        updateCurrentPlayerGraphic();
        calculateReinforcements();
        firstReinforceOfTurn = true;
        firstFortify = true;
        fortifyNumberString = "-5";
        occupyNumberString = "-5";
        boardModel.setCurrentPhase("Reinforce");
        turnPhaseTextView.setText(boardModel.getCurrentPhase());
        textView0.setGravity(Gravity.CENTER);
        textView1.setGravity(Gravity.CENTER);


    }

    private void updateCurrentPlayerGraphic() {
        char currentPlayer = boardModel.getCurrentPlayerTurn();

        if (currentPlayer == 'b') {
            currentPlayerGraphic.setImageResource(R.drawable.turn_changer_circle_blue);
        }
        if (currentPlayer == 'o') {
            currentPlayerGraphic.setImageResource(R.drawable.turn_changer_circle_orange);
        }
        if (currentPlayer == 'p') {
            currentPlayerGraphic.setImageResource(R.drawable.turn_changer_circle_purple);
        }
        if (currentPlayer == 'g') {
            currentPlayerGraphic.setImageResource(R.drawable.turn_changer_circle_green);
        }
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


    //TODO onClick
    @Override
    public void onClick(View v) {
        //disable clicking on other spots while attacking
        if (attacking) {
            return;
        }

        //create local region from the view
        Region localRegion = boardModel.getRegionFromView(v);

        if (boardModel.getCurrentGamePhase().equals("Setup")) {
            if (localRegion.getColorControl() == '0') {
                //make sure they only place one unit
                if (setupFirstPlacement) {
                    setup(localRegion);
                    setupFirstPlacement = false;
                }
            }
        }

        //to do might need to remove the check for player color
        else if (boardModel.getCurrentGamePhase().equals("Place")) {
            if (localRegion.getColorControl() == boardModel.getCurrentPlayerTurn()) {
                if (setupFirstPlacement) {
                    setup(localRegion);
                    setupFirstPlacement = false;
                }
            }
        }


        //else game phase is play phase
        else {

            if (boardModel.getCurrentPhase().equals("Reinforce")) {
                if (localRegion.getColorControl() == boardModel.getCurrentPlayerTurn()) {
                    reinforce(localRegion);
                }
            }


            if (boardModel.getCurrentPhase().equals("Attack")) {


                //if the spot is highlighted
                if (localRegion.getTextColor() == 'w') {
                    attack(localRegion);
                    //return is necessary
                    return;
                }

                //unhighlight if any textview is clicked to clear old highlights
                unhighlight();

                //highlight if player turn is same as controlling player
                if (localRegion.getColorControl() == boardModel.getCurrentPlayerTurn()) {
                    attacker = localRegion;
                    //must have at least 3 units to attack
                    if (attacker.getUnitCount() < 3) {
                        Toast.makeText(this, "Must have at least 3 units to attack.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    highlight(localRegion);
                }
            }


            //if fortifying
            if (boardModel.getCurrentPhase().equals("Fortify")) {

                if (firstFortify) {

                    //if the spot is highlighted for fortifying, fortify
                    if (localRegion.getTextColor() == 's') {
                        fortify(localRegion);
                        //return is necessary
                        firstFortify = false;
                        return;
                    }


                    //highlight if player turn is same as controlling player
                    if (localRegion.getColorControl() == boardModel.getCurrentPlayerTurn()) {
                        fortifier = localRegion;
                        //need 2 units to fortify
                        if (fortifier.getUnitCount() < 2) {
                            Toast.makeText(this, "You must have at least 2 units to fortify.", Toast.LENGTH_SHORT).show();
                            //return to prevent highlighting
                            return;
                        }
                        fortifyHighlight(localRegion);
                    } else {
                        fortifyUnhighlight();
                    }
                }
            }
        }
    }

    private void reinforce(Region localRegion) {
        if (reinforcements > 0) {
            localRegion.setUnitCount(localRegion.getUnitCount() + 1);
            updateUI();
            reinforcements--;
            reinforcement_counter_text_view.setText(Integer.toString(reinforcements));
            reinforcement_counter_text_view.setGravity(Gravity.CENTER);
        }
    }



    private void fortifyUnhighlight() {
        for (int i = 0; i < boardModel.getRegionArray().length; i++) {
            if (boardModel.getRegionArray()[i].getTextColor() == 's') {
                boardModel.getRegionArray()[i].setTextColor('0');
            }
        }
        updateUI();
    }

    private void fortify(Region localRegion) {
        // Create the object of AlertDialog Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Set the message show for the Alert time
        builder.setMessage("Select the number of units you wish to move. You have " +
                Integer.toString(fortifier.getUnitCount()) + " total units. You may move between 1 and " + Integer.toString(fortifier.getUnitCount() - 1) + ".");

        // Set Alert Title
        builder.setTitle("Fortify");

        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(false);

        //create edit text for player to enter number of troops to move
        EditText editText = new EditText(this);
        editText.setHint("1 to " + Integer.toString(fortifier.getUnitCount() - 1));

        //to only allow numbers to be entered
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                fortifyNumberString = "-5";
                fortifyNumberString = s.toString();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        builder.setView(editText);

        builder.setNegativeButton("Done", (DialogInterface.OnClickListener) (dialog, which) -> {
            // If user click no then dialog box is canceled.
            fortifyNumber = Integer.parseInt(fortifyNumberString);
            if (fortifyNumber > 0 && fortifyNumber < fortifier.getUnitCount()) {
                dialog.cancel();
                localRegion.setUnitCount(fortifyNumber + 1);
                fortifier.setUnitCount(fortifier.getUnitCount() - fortifyNumber);
                fortifyUnhighlight();
            }
            else {
                Toast.makeText(this, "Value must be in range", Toast.LENGTH_SHORT).show();
                fortify(localRegion);
            }
        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();
        // Show the Alert Dialog box
        alertDialog.show();
    }

    private void fortifyHighlight(Region localRegion) {
        //need at leat one unit to fortify
        if (localRegion.getUnitCount() > 1) {

            for (int i = 0; i < localRegion.getConnected().length; i++) {
                if (localRegion.getConnected()[i].getColorControl() == boardModel.getCurrentPlayerTurn()) {
                    localRegion.getConnected()[i].setTextColor('s');
                }
            }
            updateUI();
        }
    }

    private void setup(Region localRegion) {
        if (boardModel.getCurrentGamePhase().equals("Setup")) {
            //place a single unit onto a blank space
            if (localRegion.getUnitCount() == 0) {
                localRegion.setColorControl(boardModel.getCurrentPlayerTurn());
                localRegion.setTextColor(localRegion.getColorControl());
                boardModel.increaseUnitCountByOne(localRegion);
                updateUI();
                unitPlacementCounter--;
                unitPlacementCounterTextView.setText(Integer.toString(unitPlacementCounter));
            }
        }

        if (boardModel.getCurrentGamePhase().equals("Place")) {
            //place unit onto spot that is already occupied
            if (localRegion.getColorControl() == boardModel.getCurrentPlayerTurn()) {
                boardModel.increaseUnitCountByOne(localRegion);
                updateUI();
                unitPlacementCounter--;
                unitPlacementCounterTextView.setText(Integer.toString(unitPlacementCounter));

            }
        }
    }

    private void attack(Region localRegion) {
        //check to see if player has enough units to attack
            defender = localRegion;
            attacking = true;
            showAttackUI();
            //unhighlight all of the other possible defenders
            unhighlight();
            //reset the one defender to be highlighted
            attacker.setTextColor('r');
            defender.setTextColor('w');
            updateUI();
    }

    private void showAttackUI() {
        attackDie0.setAlpha(1);
        attackDie1.setAlpha(1);
        attackDie2.setAlpha(1);

        defenseDie0.setAlpha(1);
        defenseDie1.setAlpha(1);
        attackResult.setAlpha(1);
        defenseResult.setAlpha(1);
        roll.setAlpha(1);
        roll.setClickable(true);
        endAttack.setAlpha(1);
        endAttack.setClickable(true);
    }

    private void hideAttackUI() {
        attacking = false;

        attackDie0.setAlpha(0);
        attackDie0.setText("");
        attackDie1.setAlpha(0);
        attackDie1.setText("");
        attackDie2.setAlpha(0);
        attackDie2.setText("");


        defenseDie0.setAlpha(0);
        defenseDie0.setText("");
        defenseDie1.setAlpha(0);
        defenseDie1.setText("");

        intAttackResult = 0;
        intDefenseResult = 0;

        attackResult.setAlpha(0);
        attackResult.setText("");
        defenseResult.setAlpha(0);
        defenseResult.setText("");

        roll.setAlpha(0);
        roll.setClickable(false);
        endAttack.setAlpha(0);
        endAttack.setClickable(false);

        //hide lines
        line0to0.setAlpha(0);
        line0to1.setAlpha(0);
        line1to0.setAlpha(0);
        line1to1.setAlpha(0);
        line2to0.setAlpha(0);
        line2to1.setAlpha(0);

    }

    private void updateUI() {
        updateUnitCount();
    }

    private void unhighlight() {
        for (int i = 0; i < boardModel.getRegionArray().length; i++) {
            if (boardModel.getRegionArray()[i].getTextColor() == 'w' || boardModel.getRegionArray()[i].getTextColor() == 'r') {
                boardModel.getRegionArray()[i].setTextColor('0');
            }
        }
        updateUI();
    }

    private void highlight(Region localRegion) {
        highlightAttacker(localRegion);
        highlightDefenders(localRegion);
        updateUI();
    }

    private void highlightDefenders(Region localRegion) {
        for (int i = 0; i < localRegion.getConnected().length; i++) {
            if (localRegion.getConnected()[i].getColorControl() != boardModel.getCurrentPlayerTurn()) {
                localRegion.getConnected()[i].setTextColor('w');
            }
        }
    }

    private void highlightAttacker(Region localRegion) {
        localRegion.setTextColor('r');
    }

    private void updateUnitCount() {
        for (int i = 0; i < boardModel.getRegionArray().length; i++) {

            TextView localTextView = findViewById(boardModel.getRegionArray()[i].getViewID());
            Region localRegion = boardModel.getRegionArray()[i];

            //set unit color

            //if unhighlighted, text color should equal the controlling color
            if (localRegion.getTextColor() == '0') {
                localRegion.setTextColor(localRegion.getColorControl());
            }
            if (localRegion.getTextColor() == 'b') {
                localTextView.setTextColor(getResources().getColor(R.color.blue));
            }
            if (localRegion.getTextColor() == 'o') {
                localTextView.setTextColor(getResources().getColor(R.color.orange));
            }
            if (localRegion.getTextColor() == 'p') {
                localTextView.setTextColor(getResources().getColor(R.color.purple));
            }
            if (localRegion.getTextColor() == 'g') {
                localTextView.setTextColor(getResources().getColor(R.color.green));
            }
            if (localRegion.getTextColor() == 'r') {
                localTextView.setTextColor(getResources().getColor(R.color.red));
            }
            if (localRegion.getTextColor() == 'w') {
                localTextView.setTextColor(getResources().getColor(R.color.white));
            }
            if (localRegion.getTextColor() == 's') {
                localTextView.setTextColor(getResources().getColor(R.color.white));
            }
            //set unit count
            localTextView.setText(Integer.toString(localRegion.getUnitCount()));
            localTextView.setGravity(Gravity.CENTER);
        }
    }
}