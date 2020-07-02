package mx.tec.metaheuristics.evolutionary.multiverse;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Mauricio Mendez Ruiz
 * @author Alejandra de Luna Pámanes
 */
public class MultiverseAlgorithm {
    private MultiverseHHIndividual bestUniverse;
    private List<MultiverseHHIndividual> multiverse;
    
    private final MultiverseHHEvaluator evaluator;
    private final MultiverseHHGenerator generator;
    private final RouletteWheelSelector selector;
    
    // Minimum and maximum Wormhole Existence Probability
    private final double WEP_Max = 1.0;
    private final double WEP_Min = 0.2;
    private double WEP;
    
    // Coefficient used to calculate Travelling Distance Rate
    private final double TDR_Coefficient = 6.0;
    private double TDR;
    
    public MultiverseAlgorithm(MultiverseHHEvaluator evaluator, MultiverseHHGenerator generator, RouletteWheelSelector selector) {
        this.evaluator = evaluator;
        this.generator = generator;
        this.selector = selector;
    }
    
    public MultiverseHHIndividual run(int multiverseSize, long maxTime, boolean printMode, boolean HeuristicWH) {
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
        
        // Main cycle to evolve the multiverse
        return evolve(maxTime, printMode, HeuristicWH);
    }
    
    public MultiverseHHIndividual evolve(long maxTime, boolean printMode, boolean HeuristicWH) {
        double averageFitness = 0;
        
        // Get first average fitness
        for (MultiverseHHIndividual universe : multiverse) {
            averageFitness += universe.getEvaluation();
        }
        averageFitness /= multiverse.size();
        
        // Print first evaluation
        if(printMode) {
            printFitness(-1, bestUniverse.getEvaluation(), averageFitness);
        }
        
        // Main cycle to evolve the multiverse
        int time = 0;
        while(time <= maxTime) {
            // Update WEP and TDR
            WEP = WEP_Min + time * ((WEP_Max - WEP_Min) / maxTime);
            TDR = 1 - (Math.pow(time, (1/TDR_Coefficient)) / Math.pow(maxTime, (1/TDR_Coefficient)));
            
            int blackHoleIndex = 0;
            averageFitness = 0;
            
            for(MultiverseHHIndividual universe : multiverse) {
                // White/Black hole tunnels for each universe
                universe.blackWhiteHoleTunnel(multiverse, selector);
                
                // Wormhole for all the universe except the best
                if(blackHoleIndex > 0) {
                    universe.wormHoleTunnel(WEP, TDR, bestUniverse, HeuristicWH);    
                }
                
                // Set new evaluation for this universe
                double evaluation = evaluator.evaluate(universe);
                universe.setEvaluation(evaluation);
                averageFitness += universe.getEvaluation();
                blackHoleIndex++;
            }
            
            // Get previous evaluation of best universe
            double lastEvaluation = bestUniverse.getEvaluation();
            
            // Sort universes
            Collections.sort(this.multiverse);
            bestUniverse = this.multiverse.get(0).copy();
            
            // Set new normalized inflation rates
            double maxInflationRate = Math.abs(this.multiverse.get(0).getEvaluation());
            double minInflationRate = Math.abs(this.multiverse.get(this.multiverse.size() - 1).getEvaluation());
            double normalizedEvaluation;
            for (MultiverseHHIndividual universe : this.multiverse) {
                normalizedEvaluation = evaluator.normalizedEvaluation(universe, maxInflationRate, minInflationRate);
                universe.setNormalizedInflationRate(normalizedEvaluation);
            }
            
            // Print this iteration evaluation
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
