/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.tec.metaheuristics.evolutionary.multiverse;

import java.util.Random;
import mx.tec.hermes.frameworks.rulebased.RuleBasedHH;
import mx.tec.hermes.problems.Problem;
import mx.tec.hermes.problems.ProblemSet;
import mx.tec.metaheuristics.evolutionary.Selector;
import mx.tec.metaheuristics.evolutionary.TournamentSelector;

/**
 *
 * @author Mauricio
 */
public abstract class MultiverseFramework {
           
    public static RuleBasedHH runMultiverseOptimizer(Problem problem, ProblemSet set, String[] features, 
            String[] heuristics, int populationSize, int maxEvaluations, double crossoverRate, 
            double mutationRate, boolean printMode, long seed) {
        Random random = new Random(seed);
        
        // Set static features and heuristics for all individuals
        MultiverseHHIndividual.setFeatures(features);
        MultiverseHHIndividual.setHeuristics(heuristics);
        
        // HyperHeuristic generator, evaluator and selector
        // TODO: change to class MultiverseGenerator, MultiverseEvaluator, MultiverseSelector
        MultiverseHHGenerator generator = new MultiverseHHGenerator(random.nextLong());
        MultiverseHHEvaluator evaluator = new MultiverseHHEvaluator(problem, set);
        Selector selector = new TournamentSelector(3, random.nextLong());

        // Multiverse 
        MultiverseAlgorithm multiverse = new MultiverseAlgorithm(evaluator, generator, selector);
        
        // Evolve multiverse to get the best universe
        MultiverseHHIndividual bestUniverse = multiverse.run(populationSize, maxEvaluations, printMode);
        
        // Get hyperheuristic from best universe
        RuleBasedHH hyperHeuristic = bestUniverse.getHyperHeuristic();
        
        return hyperHeuristic;
    }
    
    public static void test(String[] features, String[] heuristics, long seed, Problem problem, ProblemSet set) {
        Random random = new Random(seed);
        
        MultiverseHHIndividual.setFeatures(features);
        MultiverseHHIndividual.setHeuristics(heuristics);
        
        MultiverseHHGenerator generator = new MultiverseHHGenerator(random.nextLong());
        MultiverseHHEvaluator evaluator = new MultiverseHHEvaluator(problem, set);
        Selector selector = new TournamentSelector(3, random.nextLong());
        
        MultiverseAlgorithm multiverse = new MultiverseAlgorithm(evaluator, generator, selector);
        MultiverseHHIndividual bestUniverse = multiverse.run(10, seed, true);
        
        System.out.println(bestUniverse.toString());
        System.out.println(bestUniverse.getEvaluation());
    }
}
