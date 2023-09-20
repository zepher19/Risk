package com.myapps.risk;

import java.util.Random;

public class BoardModel {


    private static final BoardModel instance = new BoardModel();

    // private constructor to avoid client applications using the constructor
    private BoardModel(){

    }

    public static BoardModel getInstance() {
        return instance;
    }


    private final int[] dieNumbers = {1, 2, 3, 4, 5, 6};

    public int rollDie() {
        Random random = new Random();
        return dieNumbers[random.nextInt(6)];
    }








}
