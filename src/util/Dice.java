package util;

import java.util.Date;
import java.util.Random;

/* probability change of the desired face wrt prob is P(face) = 1 - prob/12
*  probability of other faces is P(other) = prob/60 */

public class Dice {

    private static Random rand;
    private static final int prob = 0;

    public Dice(int seed) {
        rand = new Random(new Date().getTime() + seed);
    }

    public int roll() {
        int num = rand.nextInt(60);
        return (1+num/10);
    }
    public int roll(int face) {
        int num = rand.nextInt(60);
        if(num<5*prob) {
            if(1+num/prob<face)
                return (1+num/prob);
            else
                return (2+num/prob);
        }
        return face;
    }

}