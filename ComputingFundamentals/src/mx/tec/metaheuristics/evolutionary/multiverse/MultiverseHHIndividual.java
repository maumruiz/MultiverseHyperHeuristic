/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.tec.metaheuristics.evolutionary.multiverse;

import java.util.BitSet;
import java.util.List;
import mx.tec.hermes.utils.BitManipulator;
import mx.tec.metaheuristics.Solution;
import static mx.tec.metaheuristics.evolutionary.multiverse.RuleBHHIndividual.MAX_VALUE;
import static mx.tec.metaheuristics.evolutionary.multiverse.RuleBHHIndividual.MIN_VALUE;
import static mx.tec.metaheuristics.evolutionary.multiverse.RuleBHHIndividual.features;
import static mx.tec.metaheuristics.evolutionary.multiverse.RuleBHHIndividual.heuristics;

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
        // Iterate rules
        for (int i = 0; i < numberOfRules; i++) {
            BitSet rule = individual.get(i * bitsPerRule, (i + 1) * bitsPerRule);
            // Iterate features
            for (int j = 0; j < features.length; j++) {
                
                double r1 = random.nextDouble();
                if(r1 < normalizedInflationRate) {
                    Solution[] whiteHole = selector.select(multiverse, 1);
                    MultiverseHHIndividual whiteHoleUniverse = (MultiverseHHIndividual) whiteHole[0];
                    
                    BitSet whiteWholeFeature = getFeature(whiteHoleUniverse, i, j);
                    int index = (i * bitsPerRule) + (j * bitsPerFeature);
                    int lengthDiff = bitsPerFeature - whiteWholeFeature.length();
                    for (int k = bitsPerFeature - 1; k >= lengthDiff; k--) {
                        individual.set(index + k, whiteWholeFeature.get(k));
                    }
                    if(lengthDiff > 0) {
                        individual.set(index, index + lengthDiff, false);
                    }
                }
            }
            
            // Heuristic
            double r1 = random.nextDouble();
            if(r1 < normalizedInflationRate) {
                Solution[] whiteHole = selector.select(multiverse, 1);
                MultiverseHHIndividual whiteHoleUniverse = (MultiverseHHIndividual) whiteHole[0];
                
                BitSet whiteHoleHeuristic = getHeuristic(whiteHoleUniverse, i);
                int index = (i * bitsPerRule) + (bitsPerFeature * features.length);
                int lengthDiff = bitsPerHeuristic - whiteHoleHeuristic.length();
                for (int k = bitsPerHeuristic; k >= lengthDiff; k--) {
                    individual.set(index + k, whiteHoleHeuristic.get(k));
                }
                if(lengthDiff > 0) {
                    individual.set(index, index + lengthDiff, false);
                }
            }
        }
    }
    
    public void wormHoleTunnel() {
        
    }
    
    public BitSet getFeature(MultiverseHHIndividual universe, int ruleIndex, int featureIndex) {        
        BitSet rule = getRule(universe, ruleIndex);
        BitSet feature = rule.get(featureIndex * bitsPerFeature, (featureIndex + 1) * bitsPerFeature);
        return feature;
    }
    
    public BitSet getHeuristic(MultiverseHHIndividual universe, int ruleIndex) {
        BitSet rule = getRule(universe, ruleIndex);
        BitSet heuristic = rule.get(bitsPerFeature * features.length, bitsPerFeature * features.length + bitsPerHeuristic);
        return heuristic;
    }
    
    public BitSet getRule(MultiverseHHIndividual universe, int ruleIndex) {
        if(ruleIndex > universe.numberOfRules - 1) {
            ruleIndex = ruleIndex % (universe.numberOfRules -1);
        }
        
        BitSet rule = universe.individual.get(ruleIndex * bitsPerRule, (ruleIndex + 1) * bitsPerRule);
        return rule;
    }
}
