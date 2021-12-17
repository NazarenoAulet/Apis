package com.quasartec.apis.ABM.Threads;

import com.quasartec.apis.ABM.agents.Cell;
import com.quasartec.apis.ABM.World;
import com.quasartec.apis.ABM.agents.Hive;
import com.quasartec.apis.ABM.agents.bees.Drone;
import com.quasartec.apis.ABM.agents.bees.Larva;
import com.quasartec.apis.ABM.agents.bees.Queen;
import com.quasartec.apis.ABM.agents.bees.Worker;

import java.util.ArrayList;

public class HiveThread extends Thread {


    @Override
    public void run() {

        try {
            while (World.running) {

                //   System.out.println("RUNNN");
                int workersSize = Hive.workers.size();
                for (int i = 0; i < workersSize; i++) {
                    Hive.workers.get(i).execute();

                }
                for (int i = 0; i < Hive.cellCant; i++) {
                    // Hive.cells[i].updateTemperature();
                }

                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println("exception");
        }

    }
}


