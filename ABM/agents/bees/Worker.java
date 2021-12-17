package com.quasartec.apis.ABM.agents.bees;

import com.quasartec.apis.ABM.World;
import com.quasartec.apis.ABM.agents.Climate;

public class Worker {

    public int age;
public float  temperature, startingTemperature;
    public void execute() {
        age++;
        temperature = World.newtonEquation(startingTemperature, Climate.temperature, 1, World.minute);
        //System.out.println("Worker temperature " + temperature);
    }
}
