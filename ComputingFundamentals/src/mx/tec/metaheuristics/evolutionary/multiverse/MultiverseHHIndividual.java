/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.tec.metaheuristics.evolutionary.multiverse;

import java.util.List;
import mx.tec.metaheuristics.Solution;

/**
 *
 * @author Mauricio
 */
public class MultiverseHHIndividual extends RuleBHHIndividual{
    
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
    
    // TODO: bit changing is giving not existing heuristics
    public void blackWhiteHoleTunnel(List<MultiverseHHIndividual> multiverse, RouletteWheelSelector selector) {
        // Iterate bits
        for(int i = 0; i < individual.length(); i++) {
            double r1 = random.nextDouble();
            if(r1 < normalizedInflationRate) {
                Solution[] whiteHole = selector.select(multiverse, 1);
                MultiverseHHIndividual whiteHoleUniverse = (MultiverseHHIndividual) whiteHole[0];
                int whiteHoleObjectIndex = i % whiteHoleUniverse.individual.length();
                individual.set(i, whiteHoleUniverse.individual.get(whiteHoleObjectIndex));
            }
        }
    }
    
    public void wormHoleTunnel() {
        
    }
}
