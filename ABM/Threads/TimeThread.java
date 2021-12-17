package com.quasartec.apis.ABM.Threads;

import com.quasartec.apis.ABM.World;
import com.quasartec.apis.ABM.agents.Climate;

public class TimeThread extends Thread {
    static long actualTime, lastExecutionTime;

    @Override
    public void run() {
        while (World.running) {
            //     try {
            actualTime = System.currentTimeMillis();

            if (actualTime - lastExecutionTime >= 100) {
                World.minute++;
           //     System.out.println("minutes " + World.minute);
                lastExecutionTime = actualTime;
                World.update();

            }




          /*  } catch (InterruptedException e) {
            }*/
        }
    }


}
