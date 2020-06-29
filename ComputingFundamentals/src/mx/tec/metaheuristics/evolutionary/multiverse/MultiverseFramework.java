package mx.tec.metaheuristics.evolutionary.multiverse;

import java.util.Random;
import mx.tec.hermes.frameworks.rulebased.RuleBasedHH;
import mx.tec.hermes.problems.Problem;
import mx.tec.hermes.problems.ProblemSet;

/**
 *
 * @author Mauricio Mendez Ruiz
 * @author Alejandra De Luna Pámanes
 * 
 */
public abstract class MultiverseFramework {
           
    public static RuleBasedHH runMultiverseOptimizer(Problem problem, ProblemSet set, String[] features, 
            String[] heuristics, int populationSize, int maxEvaluations, boolean printMode, long seed) {
        Random random = new Random(seed);
        
        // Set static features and heuristics for all individuals
        MultiverseHHIndividual.setFeatures(features);
        MultiverseHHIndividual.setHeuristics(heuristics);
        
        // HyperHeuristic generator, evaluator and selector
        MultiverseHHGenerator generator = new MultiverseHHGenerator(random.nextLong());
        MultiverseHHEvaluator evaluator = new MultiverseHHEvaluator(problem, set);
        RouletteWheelSelector selector = new RouletteWheelSelector(random.nextLong());

        // Initialize Multiverse Optimizer
        MultiverseAlgorithm multiverseAlgorithm = new MultiverseAlgorithm(evaluator, generator, selector);
        
        // Evolve multiverse to get the best universe
        MultiverseHHIndividual bestUniverse = multiverseAlgorithm.run(populationSize, maxEvaluations, printMode);
        
        // Print the best universe hyper heuristic
        if(printMode) {
            System.out.println(bestUniverse.toString());
        }
        
        // Get hyperheuristic from best universe
        RuleBasedHH hyperHeuristic = bestUniverse.getHyperHeuristic();
        
        return hyperHeuristic;
    }
}
