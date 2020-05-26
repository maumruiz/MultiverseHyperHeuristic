/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.tec.metaheuristics.multiverseoptimizer;

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

/**
 *
 * @author Mauricio
 */
public class MultiverseOptimizer {
    private String saludo;
    
    public MultiverseOptimizer(String saludo) {
        this.saludo = saludo;
    }
    
    public void saluda() {
        System.out.println(this.saludo);
    }
    
    public static RuleBasedHH runMultiverseAlgorithm(Problem problem, ProblemSet set, String[] features, String[] heuristics, int populationSize, int maxEvaluations, double crossoverRate, double mutationRate, boolean printMode, long seed) {
        Random random = new Random(seed);
        
        // Set static features and heuristics for all individuals
        RuleBasedHHIndividual.setFeatures(features);
        RuleBasedHHIndividual.setHeuristics(heuristics);
        


        Generator generator = new RuleBasedHHGenerator(random.nextLong());
        Evaluator evaluator = new HHEvaluator(problem, set);
        Selector selector = new TournamentSelector(3, random.nextLong());
        
                 // Change for multiverse
//        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(evaluator, generator, selector);;
        
//        RuleBasedHHIndividual bestUniverse = multiverse.Run(populationSize, maxEvaluations, crossoverRate, mutationRate, type, printMode)
//        RuleBasedHH hyperHeuristic = bestUniverse.getHyperHeuristic()
//        return hyperHeuristic;
        return new RuleBasedHH("");
    }
}
