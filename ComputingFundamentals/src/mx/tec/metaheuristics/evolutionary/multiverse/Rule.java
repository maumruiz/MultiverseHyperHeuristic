/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.tec.metaheuristics.evolutionary.multiverse;

import java.text.DecimalFormat;

/**
 * Provides the methods to create and use rules for rule-based hyper-heuristics in HERMES.
 *
 * @author José Carlos Ortiz Bayliss (jcobayliss@tec.mx)
 * @version 2.0
 */
class Rule {

    protected double[] featuresValues;
    protected int heuristicValue;

    /**
     * Creates a new instance of <code>Rule</code>.
     *
     * @param condition The condition of the rule.
     * @param actionId The identifier of the action to apply when this rule is satisfied.
     */
    public Rule(double[] features, int heuristic) {
        this.featuresValues = features;
        this.heuristicValue = heuristic;
    }

    /**
     * Returns the condition of this rule.
     *
     * @return The condition of this rule.
     */
    public double[] getFeaturesCopy() {
        return featuresValues.clone();
    }
    
    public Rule copy() {
        double[] tempFeatures = getFeaturesCopy();
        return new Rule(tempFeatures, heuristicValue);
    }

    /**
     * Returns the string representation of this rule.
     *
     * @return The string representation of this rule.
     */    
    public String toString() {
        StringBuilder string;
        DecimalFormat format;
        format = new DecimalFormat("0.0000");
        string = new StringBuilder();
        string.append("[");
        for (double feature : featuresValues) {
            string.append(format.format(feature)).append(", ");
        }
        string.delete(string.length() - 2, string.length());
        string.append("] => ");
        string.append(heuristicValue);
        return string.toString();
    }

}
