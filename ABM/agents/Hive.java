package com.quasartec.apis.ABM.agents;

import com.quasartec.apis.ABM.agents.bees.Drone;
import com.quasartec.apis.ABM.agents.bees.Larva;
import com.quasartec.apis.ABM.agents.bees.Queen;
import com.quasartec.apis.ABM.agents.bees.Worker;

import java.util.ArrayList;

public class Hive {


    public static ArrayList<Worker> workers;
    public static ArrayList<Larva> larvas;
    public static ArrayList<Drone> drones;
    public static ArrayList<Queen> queens;

    public static ArrayList<Cell> cells;
    public static float outTemp;
    public static int timeSlice;
    public static boolean hotHive, smallHive;
    public static int cellCant,beeCant;

    public Hive(){
        initialize();
    }
    public static void initialize(){
        workers =new ArrayList<>();
        workers.add(new Worker());
    }

}
