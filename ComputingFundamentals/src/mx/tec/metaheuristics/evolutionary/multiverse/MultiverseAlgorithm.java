/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.tec.metaheuristics.evolutionary.multiverse;

import java.util.Random;
import mx.tec.hermes.frameworks.HHEvaluator;
import mx.tec.hermes.frameworks.rulebased.RuleBasedHH;
import mx.tec.hermes.frameworks.rulebased.RuleBasedHHGenerator;
import mx.tec.hermes.frameworks.rulebased.RuleBasedHHIndividual;
import mx.tec.hermes.problems.Problem;
import mx.tec.hermes.problems.ProblemSet;
import mx.tec.metaheuristics.Evaluator;
import mx.tec.metaheuristics.Generator;
import mx.tec.metaheuristics.evolutionary.Selector;
import mx.tec.metaheuristics.evolutionary.TournamentSelector;
import mx.tec.metaheuristics.evolutionary.geneticAlgorithm.GeneticAlgorithm;

/**
 *
 * @author Mauricio
 */
public class MultiverseAlgorithm {
    private String saludo;
    
    public MultiverseAlgorithm() {
        
    }
    
    public static RuleBasedHH run(Problem problem, ProblemSet set, String[] features, 
            String[] heuristics, int populationSize, int maxEvaluations, double crossoverRate, 
            double mutationRate, boolean printMode, long seed) {
        Random random = new Random(seed);
        
        // Set static features and heuristics for all individuals
        RuleBasedHHIndividual.setFeatures(features);
        RuleBasedHHIndividual.setHeuristics(heuristics);
        
        // HyperHeuristic generator, evaluator and selector
        // TODO: change to class MultiverseGenerator, MultiverseEvaluator, MultiverseSelector
        Generator generator = new RuleBasedHHGenerator(random.nextLong());
        Evaluator evaluator = new HHEvaluator(problem, set);
        Selector selector = new TournamentSelector(3, random.nextLong());

        // Multiverse 
        Multiverse multiverse = new Multiverse(evaluator, generator, selector);
        
        // Evolve multiverse to get the best universe
        // TODO: Universe class is a RuleBasedHHIndividual
//        Universe bestUniverse = multiverse.evolve(populationSize, maxEvaluations, printMode);
        
        // Get hyperheuristic from best universe
        // TODO: implement method in Universe class
//        RuleBasedHH hyperHeuristic = bestUniverse.getHyperHeuristic();
        
        
        // TODO: return hyperheuristic
        return null;
    }
}
