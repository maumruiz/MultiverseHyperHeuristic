/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.tec.metaheuristics.evolutionary.multiverse;

import java.text.DecimalFormat;
import mx.tec.hermes.frameworks.rulebased.RuleBasedHH;
import mx.tec.metaheuristics.Solution;

/**
 *
 * @author Mauricio
 */
public class RuleBHHIndividual extends Solution{
    protected int numberOfRules;
    protected final int bitsPerRule, bitsPerFeature, bitsPerHeuristic;
    protected final Rule[] rules;
    protected static final double MIN_VALUE = -0.10, MAX_VALUE = 1.10;
    protected static String[] features = new String[0], heuristics = new String[0];
    
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
        
        this.numberOfRules = nbRules;
        this.bitsPerFeature = bitsPerFeature;
        bitsPerHeuristic = (int) Math.ceil(Math.log(heuristics.length) / Math.log(2));
        this.bitsPerRule = bitsPerFeature * features.length + bitsPerHeuristic;
        
        rules = new Rule[nbRules];
        for (int i = 0; i < nbRules; i++) {
            double[] tempFeatures = new double[features.length];
            for (int j = 0; j < features.length; j++) {
                double temp = (random.nextDouble() * (MAX_VALUE - MIN_VALUE)) + MIN_VALUE;
                tempFeatures[j] = temp;
            }
            
            int heuristicInt = random.nextInt(heuristics.length);
            
            Rule tempRule = new Rule(tempFeatures, heuristicInt);
            rules[i] = tempRule;
        }
    }
    
    /**
     * Creates a new instance of <code>RuleBasedHHIndividual</code>.
     * 
     * @param other The instance of <code>RuleBasedHHIndividual</code> to copy to this individual.
     */
    public RuleBHHIndividual(RuleBHHIndividual other) {
        super(other.getEvaluation(), other.random.nextLong());
        numberOfRules = other.numberOfRules;
        bitsPerFeature = other.bitsPerFeature;
        bitsPerHeuristic = other.bitsPerHeuristic;
        bitsPerRule = bitsPerFeature * features.length + bitsPerHeuristic;
        
        Rule[] tmpRule = new Rule[other.rules.length];
        for (int i = 0; i < other.rules.length; i++) {
            double[] tempFeatures = other.rules[i].getFeaturesCopy();
            int heuristicInt = other.rules[i].heuristicValue;
            Rule tempRule = new Rule(tempFeatures, heuristicInt);
            tmpRule[i] = tempRule;
        }
        rules = tmpRule;
    }
    
    /**
     * Returns the hyper-heuristic encoded within this individual.
     * 
     * @return The hyper-heuristic encoded within this individual.
     */
    public RuleBasedHH getHyperHeuristic() {
        int[] output = new int[numberOfRules];
        double[][] input = new double[numberOfRules][features.length];
        
        for (int i = 0; i < numberOfRules; i++) {
            for (int j = 0; j < features.length; j++) {
                input[i][j] = rules[i].featuresValues[j];
            }
            
            output[i] = rules[i].heuristicValue;
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
        Rule rule;
        StringBuilder string = new StringBuilder();
        
        for (int i = 0; i < numberOfRules; i++) {
            string.append("[");
            rule = rules[i];
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
    private String toString(Rule rule) {
        DecimalFormat format;
        format = new DecimalFormat("0.00000");
        StringBuilder string;
        string = new StringBuilder();
        
        for (int i = 0; i < features.length; i++) {
            string.append(" (")
                    .append(format.format(rule.featuresValues[i]))
                    .append(") ");
        }
        
        
        string.append(" (").append(rule.heuristicValue).append(") ");
        return string.toString();
    }
}
