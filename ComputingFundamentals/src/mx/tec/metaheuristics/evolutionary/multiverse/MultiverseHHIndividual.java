/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.tec.metaheuristics.evolutionary.multiverse;

import java.util.List;
import mx.tec.metaheuristics.Solution;
import static mx.tec.metaheuristics.evolutionary.multiverse.RuleBHHIndividual.features;

/**
 *
 * @author Mauricio
 */
public class MultiverseHHIndividual extends RuleBHHIndividual {

    protected double normalizedInflationRate;

    public MultiverseHHIndividual(int nbRules, int bitsPerFeature, long seed) {
        super(nbRules, bitsPerFeature, seed);
    }

    private MultiverseHHIndividual(MultiverseHHIndividual other) {
        super((RuleBHHIndividual) other);
    }

    public MultiverseHHIndividual copy() {
        return new MultiverseHHIndividual(this);
    }

    public void setNormalizedInflationRate(double value) {
        normalizedInflationRate = value;
    }

    public double getNormalizedInflationRate() {
        return normalizedInflationRate;
    }

    public void blackWhiteHoleTunnel(List<MultiverseHHIndividual> multiverse, RouletteWheelSelector selector) {
        // Iterate rules
        for (int i = 0; i < numberOfRules; i++) {
            // Heuristic
            double r1 = random.nextDouble();
            if (r1 < normalizedInflationRate) {
                Solution[] whiteHole = selector.select(multiverse, 1);
                MultiverseHHIndividual whiteHoleUniverse = (MultiverseHHIndividual) whiteHole[0];

                int index = i % (whiteHoleUniverse.numberOfRules - 1);
                Rule whiteHoleRule = whiteHoleUniverse.rules[index].copy();
                rules[i] = whiteHoleRule;
            }
        }
    }

    public void wormHoleTunnel(double WEP, double TDR, MultiverseHHIndividual bestUniverse) {
        // Iterate rules
        for (int i = 0; i < numberOfRules; i++) {
            // Iterate features
            for (int j = 0; j < features.length; j++) {
                double wormHoleExistence = random.nextDouble();
                if (wormHoleExistence < WEP) {
                    double interstellarTravel = random.nextDouble();
                    double lightYears = random.nextDouble();

                    // Get new feature value
                    double newValue;
                    int index = i % (bestUniverse.numberOfRules - 1);
                    double bestUniverseValue = bestUniverse.rules[index].featuresValues[j];
                    
                    if (interstellarTravel < 0.5) {
                        newValue = bestUniverseValue + TDR * ((MAX_VALUE - MIN_VALUE) * lightYears + MIN_VALUE);
                    } else {
                        newValue = bestUniverseValue - TDR * ((MAX_VALUE - MIN_VALUE) * lightYears + MIN_VALUE);
                    }

                    rules[i].featuresValues[j] = newValue;
                }
            }

//            double wormHoleExistence = random.nextDouble();
//            if (wormHoleExistence < WEP) {
//                double interstellarTravel = random.nextDouble();
//                double lightYears = random.nextDouble();
//
//                // Get new feature value
//                double newValue;
//                BitSet bestUniverseHeuristic = getHeuristic(bestUniverse, i);
//                double bestUniverseValue = BitManipulator.toDouble(bestUniverseHeuristic, bitsPerHeuristic, heuristics.length - 1, 0);
//                if (interstellarTravel < 0.5) {
//                    newValue = bestUniverseValue + TDR * ((heuristics.length - 1) * lightYears);
//                } else {
//                    newValue = bestUniverseValue - TDR * ((heuristics.length - 1) * lightYears);
//                }
//
//                // Convert to bits the new value
//                BitSet newBitSet = BitManipulator.toBitSet(newValue, bitsPerHeuristic, heuristics.length - 1, 0);
//                int index = (i * bitsPerRule) + (bitsPerFeature * features.length);
//                int lengthDiff = bitsPerHeuristic - newBitSet.length();
//                for (int k = bitsPerHeuristic - 1; k >= lengthDiff; k--) {
//                    individual.set(index + k, newBitSet.get(k));
//                }
//                if (lengthDiff > 0) {
//                    individual.set(index, index + lengthDiff, false);
//                }
//            }
        }
    }
}
