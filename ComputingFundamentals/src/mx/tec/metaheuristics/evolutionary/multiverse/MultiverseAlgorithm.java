/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.tec.metaheuristics.evolutionary.multiverse;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import mx.tec.metaheuristics.Solution;

/**
 *
 * @author Mauricio
 */
public class MultiverseAlgorithm {
    
    // TO DO: Change classes to the real ones (not the abstract ones)
    private MultiverseHHIndividual bestUniverse;
    private List<MultiverseHHIndividual> multiverse;
    
    private final MultiverseHHEvaluator evaluator;
    private final MultiverseHHGenerator generator;
    private final RouletteWheelSelector selector;
    
    // Minimum and maximum Wormhole Existence Probability
    private double WEP_Max = 1.0;
    private double WEP_Min = 0.2;
    private double WEP;
    
    // Coefficient used to calculate Travelling Distance Rate
    private double TDR_Coefficient = 6.0;
    private double TDR;
    
    public MultiverseAlgorithm(MultiverseHHEvaluator evaluator, MultiverseHHGenerator generator, RouletteWheelSelector selector) {
        this.evaluator = evaluator;
        this.generator = generator;
        this.selector = selector;
    }
    
    public MultiverseHHIndividual run(int multiverseSize, long maxTime, boolean printMode) {
        // Initialize universes
        this.multiverse = generator.multiBigBang(multiverseSize);
        
        // Evaluate created universes
        double evaluation;
        for (MultiverseHHIndividual universe : this.multiverse) {
            evaluation = evaluator.evaluate(universe);
            universe.setEvaluation(evaluation);
        }
        
        // Sort universes according to its evaluation
        Collections.sort(this.multiverse);
        bestUniverse = this.multiverse.get(0).copy();
        
        // Set normalized inflation rates
        double maxInflationRate = Math.abs(this.multiverse.get(0).getEvaluation());
        double minInflationRate = Math.abs(this.multiverse.get(multiverseSize - 1).getEvaluation());
        double normalizedEvaluation;
        for (MultiverseHHIndividual universe : this.multiverse) {
            normalizedEvaluation = evaluator.normalizedEvaluation(universe, maxInflationRate, minInflationRate);
            universe.setNormalizedInflationRate(normalizedEvaluation);
        }
        
        return evolve(maxTime, printMode);
    }
    
    public MultiverseHHIndividual evolve(long maxTime, boolean printMode) {
        double averageFitness = 0;
        List<MultiverseHHIndividual> nextMultiverse;
        
        // Get first average fitness
        for (MultiverseHHIndividual universe : multiverse) {
            averageFitness += universe.getEvaluation();
        }
        averageFitness /= multiverse.size();
        
        if(printMode) {
            printFitness(1, bestUniverse.getEvaluation(), averageFitness);
        }
        
        // Main cycle to evolve universe
        int time = 2;
        while(time <= maxTime) {
            WEP = WEP_Min + time * ((WEP_Max - WEP_Min) / maxTime);
            TDR = 1 - (Math.pow(time, (1/TDR_Coefficient)));
            averageFitness = 0;
            
            for(MultiverseHHIndividual universe : multiverse) {
                universe.blackWhiteHoleTunnel(multiverse, selector);
//                universe.wormHoleTunnel();
                
                // Set new evaluation
                double evaluation = evaluator.evaluate(universe);
                universe.setEvaluation(evaluation);
                averageFitness += universe.getEvaluation();
            }
            
            // Sort universes
            Collections.sort(this.multiverse);
            bestUniverse = this.multiverse.get(0).copy();
            
            // Set normalized inflation rates
            double maxInflationRate = Math.abs(this.multiverse.get(0).getEvaluation());
            double minInflationRate = Math.abs(this.multiverse.get(this.multiverse.size() - 1).getEvaluation());
            double normalizedEvaluation;
            for (MultiverseHHIndividual universe : this.multiverse) {
                normalizedEvaluation = evaluator.normalizedEvaluation(universe, maxInflationRate, minInflationRate);
                universe.setNormalizedInflationRate(normalizedEvaluation);
            }
            
            if(printMode) {
                averageFitness /= multiverse.size();
                printFitness(time, bestUniverse.getEvaluation(), averageFitness);
            }
            
            time++;
        }
        
        return bestUniverse;
    }
    
    public void printFitness(int iteration, double best, double average) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0000");
        System.out.println("Generation: " + (iteration) + "    Best: " + decimalFormat.format(best) + "    Average: " + decimalFormat.format(average));
    }
}
