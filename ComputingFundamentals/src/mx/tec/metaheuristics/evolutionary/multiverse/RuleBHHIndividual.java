/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.tec.metaheuristics.evolutionary.multiverse;

import java.text.DecimalFormat;
import java.util.BitSet;
import mx.tec.hermes.frameworks.rulebased.RuleBasedHH;
import mx.tec.hermes.utils.BitManipulator;
import mx.tec.metaheuristics.Solution;

/**
 *
 * @author Mauricio
 */
public class RuleBHHIndividual extends Solution{
    private int numberOfRules;
    private final int bitsPerRule, bitsPerFeature, bitsPerHeuristic;
    private final BitSet individual;
    private static final double MIN_VALUE = -0.10, MAX_VALUE = 1.10;
    private static String[] features = new String[0], heuristics = new String[0];
    
    /**
     * Sets the heuristics to be used by all the individuals.
     *
     * @param features The features to be used by all the individuals.
     */
    public static void setFeatures(String[] features) {
        RuleBHHIndividual.features = features;
    }
    
    /**
     * Sets the heuristics to be used by all the individuals.
     * 
     * @param heuristics The heuristics to be used by all the individuals.
     */
    public static void setHeuristics(String[] heuristics) {
        RuleBHHIndividual.heuristics = heuristics;
    }
    
    /**
     * Creates a new instance of <code>RuleBasedHHIndividual</code>.
     * 
     * @param nbRules The number of genes in this individual.
     * @param bitsPerFeature The number of bits used to represent each feature.
     * @param seed The seed to initialize the random number generator.
     */
    public RuleBHHIndividual(int nbRules, int bitsPerFeature, long seed) {
        super(0, seed);
        int bitCounter;
        BitSet bits;
        
        this.numberOfRules = nbRules;
        this.bitsPerFeature = bitsPerFeature;
        bitsPerHeuristic = (int) Math.ceil(Math.log(heuristics.length) / Math.log(2));
        this.bitsPerRule = bitsPerFeature * features.length + bitsPerHeuristic;
        
        bitCounter = 0;
        individual = new BitSet();
        for (int i = 0; i < nbRules; i++) {
            for (String feature : features) {
                double temp = random.nextDouble();
                bits = BitManipulator.toBitSet(temp, bitsPerFeature, MIN_VALUE, MAX_VALUE);
                for (int k = 0; k < bitsPerFeature; k++) {
                    individual.set(bitCounter++, bits.get(k));
                }
            }
            
            int heuristicInt = random.nextInt(heuristics.length);               
            bits = BitManipulator.toBitSet(heuristicInt);
            for (int k = 0; k < bitsPerHeuristic; k++) {
                individual.set(bitCounter++, bits.get(k));
            }
        }
    }
    
    /**
     * Creates a new instance of <code>RuleBasedHHIndividual</code>.
     * 
     * @param individual The instance of <code>RuleBasedHHIndividual</code> to copy to this individual.
     */
    public RuleBHHIndividual(RuleBHHIndividual other) {
        super(other.getEvaluation(), other.random.nextLong());
        numberOfRules = other.numberOfRules;
        bitsPerFeature = other.bitsPerFeature;
        bitsPerHeuristic = other.bitsPerHeuristic;
        bitsPerRule = bitsPerFeature * features.length + bitsPerHeuristic;
        individual = (BitSet) other.individual.clone();
    }
    
    /**
     * Returns the hyper-heuristic encoded within this individual.
     * 
     * @return The hyper-heuristic encoded within this individual.
     */
    public RuleBasedHH getHyperHeuristic() {
        int[] output = new int[numberOfRules];
        double[][] input = new double[numberOfRules][features.length];
        BitSet rule, featureBitSet, heuristicBitSet;
        
        for (int i = 0; i < numberOfRules; i++) {
            rule = individual.get(i * bitsPerRule, (i + 1) * bitsPerRule);
            for (int j = 0; j < features.length; j++) {
                featureBitSet = rule.get(j * bitsPerFeature, (j + 1) * bitsPerFeature);
                input[i][j] = BitManipulator.toDouble(featureBitSet, bitsPerFeature, MIN_VALUE, MAX_VALUE);
            }
            heuristicBitSet = rule.get(bitsPerFeature * features.length, bitsPerFeature * features.length + bitsPerHeuristic);
            output[i] = BitManipulator.toInteger(heuristicBitSet) % heuristics.length;
        }
        return new RuleBasedHH(features, heuristics, input, output);
    }
    
    @Override
    public MultiverseHHIndividual[] combine(Solution[] individuals, double crossoverRate) {        
        return null;
    }
    
    @Override
    public void mutate(double mutationRate) {
    }
    
    @Override
    public RuleBHHIndividual copy() {
        return new RuleBHHIndividual(this);
    }
    
    @Override
    public String toString() {
        BitSet rule;
        StringBuilder string;
        string = new StringBuilder();
        
        for (int i = 0; i < numberOfRules; i++) {
            string.append("[");
            rule = individual.get(i * bitsPerRule, (i + 1) * bitsPerRule);
            string.append(toString(rule));
            string.deleteCharAt(string.length() - 1);
            string.append("]\n");
        }
        return string.toString().trim();
    }
    
    /**
     * Returns the string representation of the bitset provided.
     * 
     * @param bits The bitset whose string representation is required.
     * @return The string representation of the gene provided.
     */
    private String toString(BitSet bits) {
        BitSet featureBitSet, heuristicBitSet;
        DecimalFormat format;
        format = new DecimalFormat("0.00000");
        StringBuilder string, tempString;
        string = new StringBuilder();
        
        for (int i = 0; i < features.length; i++) {
            featureBitSet = bits.get(i * bitsPerFeature, (i + 1) * bitsPerFeature);
            tempString = new StringBuilder();
            for (int j = 0; j < bitsPerFeature; j++) {
                tempString.append(featureBitSet.get(j) ? "1" : "0");
            }
            string.append(tempString.reverse().toString())
                    .append(" (")
                    .append(format.format(BitManipulator.toDouble(featureBitSet, bitsPerFeature, MIN_VALUE, MAX_VALUE)))
                    .append(") ");
        }
        tempString = new StringBuilder();
        heuristicBitSet = bits.get(bitsPerFeature * features.length, bitsPerFeature * features.length + bitsPerHeuristic);
        for (int i = 0; i < bitsPerHeuristic; i++) {
            tempString.append(heuristicBitSet.get(i) ? "1" : "0");
        }
        string.append(tempString.reverse().toString())
                .append(" (").append(BitManipulator.toInteger(heuristicBitSet)).append(") ");
        return string.toString();
    }
}
