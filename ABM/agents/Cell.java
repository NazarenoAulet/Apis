package com.quasartec.apis.ABM.agents;

public class Cell {/*
    public  float temp, beeHeat, wind, quantity, quality, tempIn, tempOut, interCellK, ambientK, convectionK;
    public int usedByAmount;
    public  int state;
    public  boolean droneCell, capped;
    public  final static int STATE_EGG=0,STATE_LARVA=1, STATE_PUPPA=2, STATE_HONEY=3, STATE_POLLEN=4, STATE_NECTAR=5, STATE_EMPTY=6, STATE_DIRTY=7, STATE_NON_EXISTENT=8;
    public boolean blocked, fanning;
    public  Cell c1, c2, c3, c4, c5, c6;


    public void updateTemperature() {
        float mean = (c1.temp + c2.temp + c3.temp + c4.temp + c5.temp + c6.temp) / 6;
        float w = 0;
        if (fanning) w = 30;

        wind = convectionK * (temp - mean);
        temp = Hive.newtonEquation(temp, tempOut, ambientK) + beeHeat + Hive.newtonEquation(temp, mean, interCellK) - wind - w;
        c1.wind = wind + w / 2;
        c2.wind = wind + w / 2;
    }*/
}
