package com.quasartec.apis.ABM.agents;

import com.quasartec.apis.ABM.World;

import org.apache.commons.math3.special.Gamma;

public class Climate {
    public static int windSpeed, windDirection, raining, cloudyness;
    static boolean daylight;
    public static int[] meanTemperaturesPerDay;

    static float[] monthlyProbabilityWetAdterDryDay;
    static float[] monthlyProbabilityWetAdterWetDay;
    static float[] alpha, beta;
    public static boolean dayIsWet, nextDayIsWet, tempAtSunsetRegisteredForDay;
    public static float precipAmountInDay, precipAmountNextDay;
    public static float minTempInDay, maxTempInDay, radianceInDay, temperature, radianceNextDay, minTempPrevDay, maxTempPrevDay, prevDayLength, prevSunsetHourInDecimal, prevSunriseHourInDecimal;
    public static float timeDilation,minTempNextDay, maxTempNextDay, dayLength, nextDayLength, tempAtSunset, tempAtSunsetPrevDay;
    public static float sunriseHourInDecimalNextDay, sunsetHourInDecimalNextDay;


    float[] hourlyTemperatures;
    public static float[] residualsInDay;
    public static float sunriseHourInDecimal, sunsetHourInDecimal;
    public static int sunriseHour, sunriseMinutes, sunsetHour, sunsetMinutes;
    static float latitude = 34;

    public static float meanOfDailyMeanMaximumTemperatureInDryDays = 66f;
    public static float meanOfDailyMeanMaximumTemperatureInWetDays = 64f;
    public static float amlitudeVariationOfDailyMeanMaximumTemperatureInDryDays = 25.5f;
    public static float amlitudeVariationOfDailyMeanMaximumTemperatureInWetDays = 23.8f;
    public static float posOfHarmonicInDaysOfDailyMeanMaximumTemperatureInDryDays = 200.6f;
    public static float posOfHarmonicInDaysOfDailyMeanMaximumTemperatureInWetDays = 201.1f;

    public static float meanOfDailyCoefOfVarMaximumTemperatureInDryDays = 0.19f;
    public static float meanOfDailyCoefOfVarMaximumTemperatureInWetDays = 0.19f;
    public static float amlitudeVariationOfDailyCoefOfVarMaximumTemperatureInDryDays = -0.13f;
    public static float amlitudeVariationOfDailyCoefOfVarMaximumTemperatureInWetDays = -0.13f;
    public static float posOfHarmonicInDaysOfDailyCoefOfVarMaximumTemperatureInDryDays = 200.6f;
    public static float posOfHarmonicInDaysOfDailyCoefOfVarMaximumTemperatureInWetDays = 201.1f;

    public static float meanOfDailyMeanMinimumTemperatureInDays = 44.7f;
    public static float amlitudeVariationOfDailyMeanMinimumTemperatureInDays = 23.3f;
    public static float posOfHarmonicInDaysOfDailyMeanMinimumTemperatureInDays = 200.6f;

    public static float meanOfDailyCoefOfVarMinimumTemperatureInDays = 0.26f;
    public static float amlitudeVariationOfDailyCoefOfVarMinimumTemperatureInDays = -0.22f;
    public static float posOfHarmonicInDaysOfDailyCoefOfVarMinimumTemperatureInDays = 198.8f;

    public static float meanOfDailyMeanRadianceInDryDays = 430.8f;
    public static float meanOfDailyMeanRadianceInWetDays = 258.7f;
    public static float amlitudeVariationOfDailyMeanRadianceInDryDays = 226.7f;
    public static float amlitudeVariationOfDailyMeanRadianceInWetDays = 185.7f;
    public static float posOfHarmonicInDaysOfDailyMeanRadianceInDryDays = 175.6f;
    public static float posOfHarmonicInDaysOfDailyMeanRadianceInWetDays = 178.6f;

    public static float meanOfDailyCoefOfVarRadianceInDryDays = 0.28f;
    public static float meanOfDailyCoefOfVarRadianceInWetDays = 0.59f;
    public static float amlitudeVariationOfDailyCoefOfVarRadianceInDryDays = -0.11f;
    public static float amlitudeVariationOfDailyCoefOfVarRadianceInWetDays = -0.22f;
    public static float posOfHarmonicInDaysOfDailyCoefOfVarRadianceInDryDays = 200.0f;
    public static float posOfHarmonicInDaysOfDailyCoefOfVarRadianceInWetDays = 189.6f;


    public Climate() {
        meanTemperaturesPerDay = new int[365];
        for (int i = 0; i < 365; i++) {
            meanTemperaturesPerDay[i] = 30;
        }
        temperature = 20;

        monthlyProbabilityWetAdterWetDay = new float[]{0.424f, 0.415f, 0.452f, 0.478f, 0.455f, 0.377f, 0.400f, 0.441f, 0.406f, 0.394f, 0.361f, 0.410f};
        monthlyProbabilityWetAdterDryDay = new float[]{0.265f, 0.254f, 0.303f, 0.276f, 0.260f, 0.269f, 0.243f, 0.231f, 0.179f, 0.12f, 0.242f, 0.244f};
        alpha = new float[]{0.834f, 0.811f, 0.828f, 0.789f, 0.751f, 0.622f, 0.581f, 0.607f, 0.635f, 0.628f, 0.731f, 0.679f};
        beta = new float[]{0.299f, 0.384f, 0.387f, 0.383f, 0.423f, 0.604f, 0.793f, 0.810f, 0.645f, 0.610f, 0.478f, 0.508f};

        residualsInDay = new float[]{0, 0, 0};

        calculateSunriseSunset();
        newDayWet(World.month, dayIsWet);
        if (nextDayIsWet) getPrecipitationAmount(World.month);
        getTempInDay(World.dayOfYear + 1);

        minTempPrevDay = minTempNextDay;
        maxTempPrevDay = maxTempNextDay;
        minTempInDay = minTempNextDay;
        maxTempInDay = maxTempNextDay;
        dayIsWet = nextDayIsWet;
        radianceInDay = radianceNextDay;
        precipAmountInDay = precipAmountNextDay;


        sunriseHour = (int) sunriseHourInDecimalNextDay;
        sunriseMinutes = (int) (60 * (sunriseHourInDecimalNextDay - sunriseHour));
        sunsetHour = (int) sunsetHourInDecimalNextDay;
        sunsetMinutes = (int) (60 * (sunsetHourInDecimalNextDay - sunsetHour));

        sunriseHourInDecimal = sunriseHourInDecimalNextDay;
        sunsetHourInDecimal = sunsetHourInDecimalNextDay;


        dayLength = nextDayLength;
        prevDayLength = dayLength;
        prevSunsetHourInDecimal = sunsetHourInDecimal;
        prevSunriseHourInDecimal = sunriseHourInDecimal;
        tempAtSunsetPrevDay = (float) ((maxTempInDay - minTempInDay) * Math.sin((Math.PI * (sunsetHourInDecimal - sunriseHourInDecimal)) / (dayLength + 4)) + minTempInDay);

    }

   /* public static void updateClimate(int hour, int day, int season) {
        temperature = meanTemperaturesPerDay[day] + (int) Math.round(Math.random() * 20) - 10;

        //   System.out.println("temperatuire " + temperature);
    }

    //<
*/

    public static void newDayWet(int month, boolean previousWet) {

        if (previousWet) {
            if (Math.random() < monthlyProbabilityWetAdterWetDay[month]) nextDayIsWet = true;
            else nextDayIsWet = false;
        } else {
            if (Math.random() < monthlyProbabilityWetAdterDryDay[month]) nextDayIsWet = true;
            else nextDayIsWet = false;
        }

    }

    public static void getPrecipitationAmount(int month) {

        float alphaInMonth = alpha[month];
        float betaInMonth = beta[month];
        float p = (float) Math.random();
        precipAmountNextDay = (float) ((Math.pow(p, alphaInMonth - 1) * Math.pow(Math.E, -p / betaInMonth)) / (Math.pow(betaInMonth, alphaInMonth) * Gamma.gamma(alphaInMonth)));

    }

    public static void getTempInDay(int day) {
        float[] A = new float[]{0.567f, 0.086f, -0.002f,
                0.253f, 0.504f, -0.059f,
                0.006f, -0.039f, 0.244f
        };

        float[] B = new float[]{0.781f, 0, 0,
                0.328f, 0.637f, 0,
                0.238f, -0.341f, 0.873f

        };

        float[] randomComponents = new float[]{(float) Math.random(), (float) Math.random(), (float) Math.random()};
        float[] XIJ = new float[3];

        XIJ[0] = 0.567f * residualsInDay[0] + 0.086f * residualsInDay[1] + -0.002f * residualsInDay[2] + 0.781f * randomComponents[0];
        XIJ[1] = 0.253f * residualsInDay[0] + 0.504f * residualsInDay[1] + -0.059f * residualsInDay[2] + 0.328f * randomComponents[0] + 0.637f * randomComponents[1];
        XIJ[2] = 0.006f * residualsInDay[0] + -0.039f * residualsInDay[1] + 0.244f * residualsInDay[2] + 0.238f * randomComponents[0] - 0.341f * randomComponents[1] + 0.873f * randomComponents[2];

        residualsInDay[0] = XIJ[0];
        residualsInDay[1] = XIJ[1];
        residualsInDay[2] = XIJ[2];
        if (nextDayIsWet) {
            double dailyMeanMax = meanOfDailyMeanMaximumTemperatureInWetDays + amlitudeVariationOfDailyMeanMaximumTemperatureInWetDays * Math.cos(0.0172 * (day - posOfHarmonicInDaysOfDailyMeanMaximumTemperatureInWetDays));
            double dailyCoefOfVarMax = meanOfDailyCoefOfVarMaximumTemperatureInWetDays + amlitudeVariationOfDailyCoefOfVarMaximumTemperatureInWetDays * Math.cos(0.0172 * (day - posOfHarmonicInDaysOfDailyCoefOfVarMaximumTemperatureInWetDays));

            maxTempNextDay = toCelcius(dailyMeanMax * (residualsInDay[0] * dailyCoefOfVarMax + 1));


            double dailyMeanRad = meanOfDailyMeanRadianceInWetDays + amlitudeVariationOfDailyMeanRadianceInWetDays * Math.cos(0.0172 * (day - posOfHarmonicInDaysOfDailyMeanRadianceInWetDays));
            double dailyCoefOfVarRad = meanOfDailyCoefOfVarRadianceInWetDays + amlitudeVariationOfDailyCoefOfVarRadianceInWetDays * Math.cos(0.0172 * (day - posOfHarmonicInDaysOfDailyCoefOfVarRadianceInWetDays));

            radianceNextDay = (float) (dailyMeanRad * (residualsInDay[0] * dailyCoefOfVarRad + 1));
        } else {
            double dailyMeanMax = meanOfDailyMeanMaximumTemperatureInDryDays + amlitudeVariationOfDailyMeanMaximumTemperatureInDryDays * Math.cos(0.0172 * (day - posOfHarmonicInDaysOfDailyMeanMaximumTemperatureInDryDays));
            double dailyCoefOfVarMax = meanOfDailyCoefOfVarMaximumTemperatureInDryDays + amlitudeVariationOfDailyCoefOfVarMaximumTemperatureInDryDays * Math.cos(0.0172 * (day - posOfHarmonicInDaysOfDailyCoefOfVarMaximumTemperatureInDryDays));

            maxTempNextDay = toCelcius(dailyMeanMax * (residualsInDay[0] * dailyCoefOfVarMax + 1));

            double dailyMeanRad = meanOfDailyMeanRadianceInDryDays + amlitudeVariationOfDailyMeanRadianceInDryDays * Math.cos(0.0172 * (day - posOfHarmonicInDaysOfDailyMeanRadianceInDryDays));
            double dailyCoefOfVarRad = meanOfDailyCoefOfVarRadianceInDryDays + amlitudeVariationOfDailyCoefOfVarRadianceInDryDays * Math.cos(0.0172 * (day - posOfHarmonicInDaysOfDailyCoefOfVarRadianceInDryDays));

            radianceNextDay = (float) (dailyMeanRad * (residualsInDay[0] * dailyCoefOfVarRad + 1));
        }

        double dailyMeanMin = meanOfDailyMeanMinimumTemperatureInDays + amlitudeVariationOfDailyMeanMinimumTemperatureInDays * Math.cos(0.0172 * (day - posOfHarmonicInDaysOfDailyMeanMinimumTemperatureInDays));
        double dailyCoefOfVarMin = meanOfDailyCoefOfVarMinimumTemperatureInDays + amlitudeVariationOfDailyCoefOfVarMinimumTemperatureInDays * Math.cos(0.0172 * (day - posOfHarmonicInDaysOfDailyCoefOfVarMinimumTemperatureInDays));

        minTempNextDay = toCelcius(dailyMeanMin * (residualsInDay[0] * dailyCoefOfVarMin + 1));


    }


    public static float toCelcius(double f) {
        return (float) Math.round((((f - 32) * 5) / 9f) * 10) / 10f;

    }


    public static void calculateSunriseSunset() {
        float SHA = 0 / 15;
        //float hourAngleRise= Math.acos(-Math.tan(Math.toRadians(34))*Math.tan(declination));

        float julianDays = World.dayOfYear + (12 - SHA) / 24f;

        float meanSolarAnomalyInDegrees = 0.9856f * julianDays - 3.289f;

        double v = meanSolarAnomalyInDegrees + 1.915 * Math.sin(Math.toRadians(meanSolarAnomalyInDegrees)) + 0.02f * Math.sin(Math.toRadians(meanSolarAnomalyInDegrees * 2));

        double eclipticLongitude = (v + 180 + 102.937348f) % 360;

        double declinationOfSun = Math.toDegrees(Math.asin((Math.sin(Math.toRadians(eclipticLongitude)) * Math.sin(Math.toRadians(23.44)))));

        double rifghtAscencionOfSun = Math.toDegrees(Math.atan((Math.cos(Math.toRadians(23.44)) * Math.tan(Math.toRadians(eclipticLongitude)))));


        rifghtAscencionOfSun = rifghtAscencionOfSun + 90 * (Math.floor(eclipticLongitude / 90f) - Math.floor(rifghtAscencionOfSun / 90f));

        float h0 = 0;

        double sunLocalHourAngle = Math.toDegrees(Math.acos((Math.sin(h0) - Math.sin(Math.toRadians(latitude)) * Math.sin(Math.toRadians(declinationOfSun))) / (Math.cos(Math.toRadians(latitude * (Math.cos(Math.toRadians(declinationOfSun))))))));

        sunriseHourInDecimalNextDay = (float) ((360 - sunLocalHourAngle + rifghtAscencionOfSun) / 15f - (0.06571 * (julianDays - 0.25)) - 6.622);
        sunsetHourInDecimalNextDay = (float) ((sunLocalHourAngle + rifghtAscencionOfSun) / 15f - (0.06571 * (julianDays + 0.25)) - 6.622);

        if (sunriseHourInDecimalNextDay > 24) sunriseHourInDecimalNextDay -= 24;
        if (sunsetHourInDecimalNextDay > 24) sunsetHourInDecimalNextDay -= 24;
        if (sunriseHourInDecimalNextDay < 0) sunriseHourInDecimalNextDay += 24;
        if (sunsetHourInDecimalNextDay < 0) sunsetHourInDecimalNextDay += 24;

        nextDayLength = sunsetHourInDecimalNextDay - sunriseHourInDecimalNextDay;

    }

    public static void generateMinuteTemperature(float t) {
        if (t < sunriseHourInDecimal) {
       //     System.out.println("A tempAtSunsetPrevDay " + tempAtSunsetPrevDay + " dayLength " + dayLength);
            //  float tempAtSunsetPrevDay = (float) ((maxTempPrevDay - minTempPrevDay) * Math.sin((Math.PI * (prevSunsetHourInDecimal - prevSunriseHourInDecimal)) / (prevDayLength + 4)) + minTempPrevDay);
            temperature = (float) (tempAtSunsetPrevDay - ((tempAtSunsetPrevDay - minTempInDay) / Math.log(24 - prevDayLength)) * Math.log(t + 24 - prevSunsetHourInDecimal));
        } else if (t >= sunriseHourInDecimal && t <= sunsetHourInDecimal) {
           // System.out.println("B");
            temperature = (float) ((maxTempInDay - minTempInDay) * Math.sin((Math.PI * (t - sunriseHourInDecimal) / (dayLength + 4))) + minTempInDay);


        } else {

            if (!tempAtSunsetRegisteredForDay) {
                tempAtSunset = (float) ((maxTempInDay - minTempInDay) * Math.sin((Math.PI * (sunsetHourInDecimal - sunriseHourInDecimal)) / (dayLength + 4)) + minTempInDay);
                tempAtSunsetRegisteredForDay = true;

                timeDilation = ((24 - sunsetHourInDecimal) / (25f - sunsetHourInDecimal));

            }


          //  System.out.println("C (f(t)) " + (   (1/(24-sunsetHourInDecimal))*t-(sunsetHourInDecimal/(24-sunsetHourInDecimal)) ) );

            // float tempAtSunset = (float) ((maxTempInDay - minTempInDay) * Math.sin((Math.PI * (sunsetHourInDecimal- sunriseHourInDecimal)) / (dayLength + 4)) + minTempInDay);
            temperature = (float) (tempAtSunset - ((tempAtSunset - minTempNextDay) / Math.log(24 - dayLength)) * Math.log((t - sunsetHourInDecimal + 1) *World.linearInterpolation(1,timeDilation,(1/(24-sunsetHourInDecimal))*t-(sunsetHourInDecimal/(24-sunsetHourInDecimal)))));

        }
        temperature=(Math.round(temperature*100))/100f;

    }


    public static void advanceDay() {
        System.out.println("advanceDay");
        minTempPrevDay = minTempInDay;
        maxTempPrevDay = maxTempInDay;
        minTempInDay = minTempNextDay;
        maxTempInDay = maxTempNextDay;
        dayIsWet = nextDayIsWet;
        radianceInDay = radianceNextDay;
        precipAmountInDay = precipAmountNextDay;
        prevDayLength = dayLength;
        dayLength = nextDayLength;
        prevSunsetHourInDecimal = sunsetHourInDecimal;
        prevSunriseHourInDecimal = sunriseHourInDecimal;

        sunriseHourInDecimal = sunriseHourInDecimalNextDay;
        sunsetHourInDecimal = sunsetHourInDecimalNextDay;

        sunriseHour = (int) sunriseHourInDecimalNextDay;
        sunriseMinutes = (int) (60 * (sunriseHourInDecimalNextDay - sunriseHour));
        sunsetHour = (int) sunsetHourInDecimalNextDay;
        sunsetMinutes = (int) (60 * (sunsetHourInDecimalNextDay - sunsetHour));
        tempAtSunsetPrevDay = tempAtSunset;
        tempAtSunsetRegisteredForDay = false;
        calculateSunriseSunset();
        newDayWet(World.month, dayIsWet);
        if (nextDayIsWet) getPrecipitationAmount(World.month);
        getTempInDay(World.dayOfYear + 1);
    }
}
