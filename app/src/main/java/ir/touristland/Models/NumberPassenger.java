package ir.touristland.Models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZAMAN on 5/22/2018.
 */

public class NumberPassenger {
    private static final NumberPassenger ourInstance = new NumberPassenger();
    public int numberAdult = 1;
    public int numberChild = 0;
    public int numberBaby = 0;

    public Map<String, String> params = new HashMap<>();

    private NumberPassenger() {
    }

    public static NumberPassenger getInstance() {
        return ourInstance;
    }

    public int getNumberAdult() {
        return numberAdult;
    }

    public void setNumberAdult(int numberAdult) {
        this.numberAdult = numberAdult;
    }

    public int getNumberChild() {
        return numberChild;
    }

    public void setNumberChild(int numberChild) {
        this.numberChild = numberChild;
    }

    public int getNumberBaby() {
        return numberBaby;
    }

    public void setNumberBaby(int numberBaby) {
        this.numberBaby = numberBaby;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
