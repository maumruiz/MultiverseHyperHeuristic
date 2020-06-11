/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.tec.metaheuristics.evolutionary.multiverse;

import java.text.DecimalFormat;
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
    
    // Coefficient used to calculate Travelling Distance Rate
    private double TDR_Coefficient = 6.0;
    
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
        
        Solution[] selected = selector.select(multiverse, 3);
        
        System.out.println("Selected universes:");
        System.out.println(selected[0].getEvaluation());
        System.out.println(selected[1].getEvaluation());
        System.out.println(selected[2].getEvaluation());
        
        // Main cycle to evolve universe
//        int time = 2;
//        while(time <= maxTime) {
//            nextMultiverse = new ArrayList(multiverse.size());
//            
//            // Blackhole/whitehole tunnels
//            
//            // Wormwhole tunnels
//            
//            multiverse = nextMultiverse;
//            Collections.sort(this.multiverse);
//            if (multiverse.get(0).getEvaluation() < bestUniverse.getEvaluation()) {
//                bestUniverse = multiverse.get(0).copy();
//            }
//            averageFitness /= multiverse.size();
//            
//            if(printMode) {
//                printFitness(time, bestUniverse.getEvaluation(), averageFitness);
//            }
//            
//            time++;
//        }
        
        return bestUniverse;
    }
    
    public void printFitness(int iteration, double best, double average) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0000");
        System.out.println("Generation: " + (iteration + 1) + "    Best: " + decimalFormat.format(best) + "    Average: " + decimalFormat.format(average));
    }
}
