package com.quasartec.apis.ABM.agents.bees;


import com.quasartec.apis.ABM.agents.Cell;


public class Bee {
/*
    float carbohydrate, carbohydrateDelta, protein, proteinDelta, temperature, x, y,
            health, defecate, stomachFullness, stomachDelta, sugarConcentration, proteinConcentration, stomachDigestedAmount,
            pollenBasquet, hpGlandFullness, hpGlandRate, position, velocity, orientation, heatTransferFactor, metabolicQ, perceivedTemperature,
            bpFeedSister, bpFeedLarva, bpTendQueen, bpFan, bpHeat, bpInspectCell, bpDisperseWater, bpCapCell, bpBuildCell, bpCleanCell,
            bpAttack, bpPackPollen, bpGrabPollen, bpInspectNectar, bpMakeHoney, bpGrabDebris, bpGuard, bpRest, bpPatroll,
            probability;

    int index, age, action, wantedAction, type, actionDuration, actionElapsed, standingCell, numberWaxFlakes, towardsAction;//towards bee positive, cell negative, self 0

    boolean insideHive, nearQueen, askedUnloading, askedFood, nearPollen, hotBrood, coldBrood, hotHive, outside,
            nearCorpse, nearDirtyCell, nearCappingNeedingCell, nearNectar, onEntranceArea, onBroodArea, changeBehaviour, proteinHunger;

    Cell c1, c2, c3;

    public Bee(int age, float x, float y) {

        this.age = age;
        if (age < -5) {
            type = TYPE_LARVA;
            temperature = c1.tempIn;
            health = 100;
            stomachFullness = 0;
            stomachDelta = -1;
            carbohydrate = 100;
            carbohydrateDelta = -1;
            protein = 100;
            proteinDelta = -1;
            this.x = x;
            this.y = y;
        }
        if (age == 1) {
            bpFeedSister = 0;
            bpFeedLarva = 0;
            bpTendQueen = 0;
            bpFan = 0.03f;
            bpHeat = 0.01f;
            bpInspectCell = 0.48f;
            bpDisperseWater = 0;
            bpCapCell = 0;
            bpBuildCell = 0.01f;
            bpCleanCell = 1;
            bpAttack = 0;
            bpPackPollen = 0;
            bpGrabPollen = 0;
            bpInspectNectar = 0;
            bpMakeHoney = 0;
            bpGrabDebris = 0.5f;
            bpGuard = 0;
            bpRest = 0.25f;
            bpPatroll = 0.25f;
            hpGlandFullness = -1f;
            health = 1;
            defecate = (float) Math.random() / 2;
            stomachFullness = (float) Math.random();
            stomachDelta = 0;
            sugarConcentration = (float) Math.random() / 2;

        }

    }

    private static final int
            TYPE_LARVA = 0,
            TYPE_NURSE = 1,
            TYPE_WORKER = 2,
            TYPE_FOODSTORER = 3,
            TYPE_FORAGER = 4,
            TYPE_DRONE = 5,
            TYPE_QUEEN = 6,
            TYPE_NEWBORN = 7,
            TYPE_NULL = 7,
            ACTION_REST = 0,
            ACTION_WALK = 1,
            ACTION_EAT = 2,
            ACTION_DEF = 3,
            ACTION_HEAT = 4,
            ACTION_FAN = 5,
            ACTION_FEED_SISTER = 6,
            ACTION_FEED_LARVA = 7,
            ACTION_TEND_QUEEN = 8,
            ACTION_INSPECT_CELL = 9,
            ACTION_DISPERSE_WATER = 10,
            ACTION_CAP_CELL = 11,
            ACTION_BUILD_CELL = 12,
            ACTION_CLEAN_CELL = 13,
            ACTION_ATTACK = 14,
            ACTION_PACK_POLLEN = 15,
            ACTION_UNLOAD_POLLEN = 16,
            ACTION_GRAB_POLLEN = 17,
            ACTION_ASK_UNLOAD = 18,
    // ACTION_UNLOAD_NECTAR = 19,
    ACTION_ASK_FOOD = 20,
            ACTION_MAKE_HONEY = 21,
            ACTION_GRAB_DEBRIS = 21,
            ACTION_GUARD = 22,
            ACTION_WAGGLE_DANCE = 23,
            ACTION_TREMBLE_DANCE = 24,
            ACTION_CIRCLE_DANCE = 25,
            ACTION_FLYING = 26,
            ACTION_BORN = 27,
            ACTION_DIE = 28,
            ACTION_CHECK_NECTAR = 28,
            ACTION_RUN = 29,
            ACTION_GO_OUT = 30;



  //  modifies stats
   // progress action or change action


    public void updateTemp() {
        perceivedTemperature = (c1.temp + c2.temp + c3.temp) / 3;
        temperature = Hive.newtonEquation(temperature + metabolicQ, perceivedTemperature, heatTransferFactor);
        c1.beeHeat = Hive.newtonEquation(c1.temp, temperature, heatTransferFactor) / 3;
        c2.beeHeat = Hive.newtonEquation(c2.temp, temperature, heatTransferFactor) / 3;
        c3.beeHeat = Hive.newtonEquation(c3.temp, temperature, heatTransferFactor) / 3;
        if (action == ACTION_FAN) {
            c1.fanning = true;
            c2.fanning = true;
            c3.fanning = true;
        } else {
            c1.fanning = false;
            c2.fanning = false;
            c3.fanning = false;
        }
    }

    public void changeBehaviour(int whom, int act) {
        actionElapsed = 0;
        towardsAction = whom;
        action = act;
        switch (action) {
            case ACTION_REST:
                metabolicQ = 10;
                carbohydrateDelta = -0.1f;
                proteinDelta = -0.1f;
                velocity = 0;
                break;
            case ACTION_WALK:
                metabolicQ = 20;
                carbohydrateDelta = -0.2f;
                proteinDelta = -0.2f;
                velocity = 0.5f;
                break;
            case ACTION_RUN:
                metabolicQ = 25;
                carbohydrateDelta = -0.3f;
                proteinDelta = -0.3f;
                velocity = 1f;
                break;
            case ACTION_EAT:
                stomachDelta = 2;
                velocity = 0f;
                break;
            case ACTION_DEF:
                defecate = 0;
                velocity = 0f;
                break;
            case ACTION_HEAT:
                metabolicQ = 30;
                carbohydrateDelta = -0.5f;
                proteinDelta = -0.3f;
                velocity = 0f;
                break;
            case ACTION_FAN:
                metabolicQ = 30;
                carbohydrateDelta = -0.5f;
                proteinDelta = -0.3f;
                velocity = 0f;
                break;
            case ACTION_FEED_SISTER:
                stomachDelta = -0.5f;
                hpGlandRate = -0.5f;
                velocity = 0f;
                break;
            case ACTION_FEED_LARVA:
                stomachDelta = -0.5f;
                hpGlandRate = -0.7f;
                velocity = 0f;
                break;
            case ACTION_DISPERSE_WATER:
                stomachDelta = -0.5f;
                velocity = 0f;
                break;
            case ACTION_FLYING:
                metabolicQ = 40;
                carbohydrateDelta = -0.4f;
                proteinDelta = -0.4f;
                velocity = 3f;

                break;
        }
    }

    public void metabolize() {
        stomachFullness += -stomachDigestedAmount+ stomachDelta;
        carbohydrate += sugarConcentration * stomachDigestedAmount * metabolicQ;
        protein += proteinConcentration * stomachDigestedAmount * metabolicQ;
    }

    public void decide() {
        probability = (float) Math.random();

        if (protein < 0.3) {
            wantedAction = ACTION_ASK_FOOD;
            proteinHunger = true;
            return;
        }
        if (carbohydrate < 0.3) {
            wantedAction = ACTION_ASK_FOOD;
            proteinHunger = false;
            return;
        }
        if (temperature < 7) {
            wantedAction = ACTION_HEAT;
            return;
        }
        if (perceivedTemperature > 40) {
            wantedAction = ACTION_GO_OUT;
            return;
        }
        if (perceivedTemperature > 35 && onBroodArea) {
            wantedAction = ACTION_FAN;
            return;
        }


    }

    public void think() {
        switch (wantedAction) {
            case ACTION_ASK_FOOD:

                break;
        }
    }

    public void execute() {
        x += velocity;
        metabolize();
        decide();

    }
*/
}
