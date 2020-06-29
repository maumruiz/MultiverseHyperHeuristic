package mx.tec.metaheuristics.evolutionary.multiverse;

import java.util.ArrayList;
import java.util.Random;
import mx.tec.hermes.HyperHeuristic;
import mx.tec.hermes.frameworks.rulebased.RuleBasedHH;
import mx.tec.hermes.problems.Problem;
import mx.tec.hermes.problems.ProblemSet;
import mx.tec.knapsack.problem.KP;

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
        if (printMode) {
            System.out.println(bestUniverse.toString());
        }

        // Get hyperheuristic from best universe
        RuleBasedHH hyperHeuristic = bestUniverse.getHyperHeuristic();

        return hyperHeuristic;
    }

    public static ArrayList<RuleBasedHH> runMultipleMultiverse(Problem problem, ProblemSet set, String[] features,
            String[] heuristics, int populationSize, int maxEvaluations, boolean printMode, long[] seeds, String key) {
        ArrayList<RuleBasedHH> hyperHeuristics
                = runMultipleMultiverse(problem, set, features, heuristics, populationSize, maxEvaluations, printMode, seeds, key, 0, seeds.length - 1);

        return hyperHeuristics;
    }

    public static ArrayList<RuleBasedHH> runMultipleMultiverse(Problem problem, ProblemSet set, String[] features,
            String[] heuristics, int populationSize, int maxEvaluations, boolean printMode, long[] seeds, String key, int from_seed) {
        ArrayList<RuleBasedHH> hyperHeuristics
                = runMultipleMultiverse(problem, set, features, heuristics, populationSize, maxEvaluations, printMode, seeds, key, from_seed, seeds.length - 1);

        return hyperHeuristics;
    }

    public static ArrayList<RuleBasedHH> runMultipleMultiverse(Problem problem, ProblemSet set, String[] features,
            String[] heuristics, int populationSize, int maxEvaluations, boolean printMode, long[] seeds, String key, int from_seed, int to_seed) {
        ArrayList<RuleBasedHH> hyperHeuristics = new ArrayList<RuleBasedHH>();
        RuleBasedHH hyperHeuristic;
        
        for(int i = from_seed - 1; i < to_seed; i++) {
            if(printMode) {
                System.out.println("");
                System.out.println("-----------------------------------------------------------");
                System.out.println("HH with seed: " + seeds[i]);
            }         
            hyperHeuristic = MultiverseFramework.runMultiverseOptimizer(problem, set, features, heuristics, populationSize, maxEvaluations, printMode, seeds[i]);
            hyperHeuristic.save("HyperHeuristic_" + key + "_" + (i + 1) + ".xml");
            hyperHeuristics.add(hyperHeuristic);
        }

        return hyperHeuristics;
    }

    public void testMultipleHHFromXML(KP problem, ProblemSet testSet, String key, int size) {
        testMultipleHHFromXML(problem, testSet, key, 0, size);
    }

    public void testMultipleHHFromXML(KP problem, ProblemSet testSet, String key, int from, int to) {
        for (int i = from; i < to; i++) {
            System.out.println("");
            System.out.println("-----------------------------------------------------------");
            System.out.println("Test: " + (i + 1));
            
            testHHFromXML(problem, testSet, "HyperHeuristic_" + key + "_" + (i + 1) + ".xml");
        }
    }

    public void testHHFromXML(KP problem, ProblemSet testSet, String name) {
        String solved;
        RuleBasedHH hyperHeuristic = new RuleBasedHH(name);
        solved = problem.solve(testSet, new HyperHeuristic[]{hyperHeuristic});
        System.out.println(solved);
    }
}
