package mx.tec.metaheuristics.evolutionary.multiverse;

import java.util.List;
import mx.tec.metaheuristics.Solution;
import static mx.tec.metaheuristics.evolutionary.multiverse.RuleBHHIndividual.features;

/**
 *
 * @author Mauricio Mendez Ruiz
 * @author Alejandra de Luna Pámanes
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
            double r1 = random.nextDouble();
            if (r1 < normalizedInflationRate) {
                // Select a high inflation rate universe as white hole
                Solution[] whiteHole = selector.select(multiverse, 1);
                MultiverseHHIndividual whiteHoleUniverse = (MultiverseHHIndividual) whiteHole[0];
                
                // Exchange objects through white/black hole tunnels
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
                        newValue = bestUniverseValue + (TDR * lightYears - 1) * (bestUniverseValue - MIN_VALUE);
                    } else {
                        newValue = bestUniverseValue + (1 - TDR * lightYears) * (MAX_VALUE - bestUniverseValue);
                    }
                    
                    rules[i].featuresValues[j] = newValue;
                }
            }
            
            /*
                The wormhole in the heuristics were removed, because it was giving us worst performance.
                We should find a better way to exchange heuristics in a way to improve the evaluation performance
            */
            /*
            double wormHoleExistence = random.nextDouble();
            if (wormHoleExistence < WEP) {
                double interstellarTravel = random.nextDouble();
                double lightYears = random.nextDouble();

                // Get new feature value
                int newHeuristic;
                int index = i % (bestUniverse.numberOfRules - 1);
                int bestUniverseHeuristic = bestUniverse.rules[index].heuristicValue;
                
                if (interstellarTravel < 0.5) {
                    newHeuristic = (int) Math.round(bestUniverseHeuristic + (TDR * lightYears - 1) * bestUniverseHeuristic);
                } else {
                    newHeuristic = (int) Math.round(bestUniverseHeuristic + (1 - (TDR * lightYears)) * (heuristics.length - 1 - bestUniverseHeuristic));
                }
                
                rules[i].heuristicValue = newHeuristic;
            }
            */
        }
    }
}
