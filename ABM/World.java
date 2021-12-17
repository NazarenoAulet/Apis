package com.quasartec.apis.ABM;

import com.quasartec.apis.ABM.Threads.HiveThread;
import com.quasartec.apis.ABM.Threads.TimeThread;
import com.quasartec.apis.ABM.agents.Climate;
import com.quasartec.apis.ABM.agents.Hive;
import com.quasartec.apis.ABM.agents.bees.Worker;

public class World {

    HiveThread hiveThread;
    TimeThread timeThread;
    public static volatile boolean running;
    Climate climate;
    Hive hive;
    public static int season, hour, minute, day, month, year, dayOfYear;
    public static int[] daysInMonth;

    public static float newtonEquation(float t0, float tEnv, double k, double time) {
        // System.out.println("CPMTS "+(float) Math.pow(Math.E, k * 1));
        return  Math.round((tEnv + (t0 - tEnv) * (Math.pow(Math.E, -0.06 * time)))*100)/100f;//*timeslice
    }


    public void init() {
        climate = new Climate();
        hive = new Hive();
        hiveThread = new HiveThread();
        hiveThread.start();
        timeThread = new TimeThread();
        timeThread.start();

        daysInMonth = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    }

    public static void update() {

        //   Climate.generateHourlyTemperature(Climate.);

        if (minute == 60) {
            hour++;
            minute = 0;

            int workersSize = Hive.workers.size();
            for (int i = 0; i < workersSize; i++) {
                Worker w = Hive.workers.get(i);
                w.startingTemperature = w.temperature;

            }

        }
        boolean newDay = false;
        if (hour == 24) {
            day++;
            dayOfYear++;
            hour = 0;
            newDay = true;
            System.out.println("day___ " + day);

        }
        if (day == daysInMonth[month]) {
            month++;
            day = 0;
            System.out.println("Month______________ " + month);

        }
        if (month == 12) {
            year++;
            dayOfYear = 0;
            month = 0;
            System.out.println("Year_____________________________________ " + year);

        }

        if (newDay) {
            Climate.advanceDay();
            System.out.println("amanecer " + Climate.sunriseHour + ":" + Climate.sunriseMinutes);
            System.out.println("atardcer " + Climate.sunsetHour + ":" + Climate.sunsetMinutes);
            System.out.println("llueve" + Climate.dayIsWet);
            System.out.println("maxima T " + Climate.maxTempInDay);
            System.out.println("minima T " + Climate.minTempInDay);


        }
        Climate.generateMinuteTemperature(hour + minute / 60f);
        System.out.println(" temperature in " + hour + ":" + minute + " is: " + Climate.temperature);

    }

    public void resume() {
        running = true;
        hiveThread = new HiveThread();
        hiveThread.start();
        timeThread = new TimeThread();
        timeThread.start();

    }


    public void pause() {
        running = false;

        while (true) {

            try {
                System.out.println("join1");
                hiveThread.join();


            } catch (InterruptedException e) {
            }

            try {
                System.out.println("join2");
                timeThread.join();


                return;
            } catch (InterruptedException e) {
            }

        }

    }

    public static double linearInterpolation(double a, double b, double f) {
        return a + f * (b - a);
    }
}
