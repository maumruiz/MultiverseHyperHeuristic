package mx.tec.metaheuristics.evolutionary.multiverse;

import java.util.ArrayList;
import java.util.Random;
import mx.tec.hermes.HyperHeuristic;
import mx.tec.hermes.frameworks.rulebased.RuleBasedHH;
import mx.tec.hermes.problems.Problem;
import mx.tec.hermes.problems.ProblemSet;
import mx.tec.knapsack.problem.KP;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;

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
            String[] heuristics, int populationSize, int maxEvaluations, boolean printMode, long[] seeds, String hhName, String key) {
        ArrayList<RuleBasedHH> hyperHeuristics
                = runMultipleMultiverse(problem, set, features, heuristics, populationSize, maxEvaluations, printMode, seeds, hhName, key, 1, seeds.length);

        return hyperHeuristics;
    }

    public static ArrayList<RuleBasedHH> runMultipleMultiverse(Problem problem, ProblemSet set, String[] features,
            String[] heuristics, int populationSize, int maxEvaluations, boolean printMode, long[] seeds, String hhName, String key, int from_seed) {
        ArrayList<RuleBasedHH> hyperHeuristics
                = runMultipleMultiverse(problem, set, features, heuristics, populationSize, maxEvaluations, printMode, seeds, hhName, key, from_seed, seeds.length);

        return hyperHeuristics;
    }

    public static ArrayList<RuleBasedHH> runMultipleMultiverse(Problem problem, ProblemSet set, String[] features,
            String[] heuristics, int populationSize, int maxEvaluations, boolean printMode, long[] seeds, String hhName, String key, int from_seed, int to_seed) {
        ArrayList<RuleBasedHH> hyperHeuristics = new ArrayList<RuleBasedHH>();
        RuleBasedHH hyperHeuristic;

        for (int i = from_seed - 1; i < to_seed; i++) {
            if (printMode) {
                System.out.println("");
                System.out.println("-----------------------------------------------------------");
                System.out.println("HH with seed: " + seeds[i]);
            }
            hyperHeuristic = MultiverseFramework.runMultiverseOptimizer(problem, set, features, heuristics, populationSize, maxEvaluations, printMode, seeds[i]);
            hyperHeuristic.save("HyperHeuristics/" + key + '/' + hhName + '_' + (i + 1) + ".xml");
            hyperHeuristics.add(hyperHeuristic);
        }

        return hyperHeuristics;
    }

    public static void testMultipleHHFromXML(KP problem, ProblemSet testSet, String setName, String hhName, String key, int from_seed, int to_seed) {
        for (int i = from_seed; i <= to_seed; i++) {
            System.out.println("");
            System.out.println("-----------------------------------------------------------");
            System.out.println("Test: " + i);

            testHHFromXML(problem, testSet, setName, hhName, key, i);
        }
    }

    public static void testHHFromXML(KP problem, ProblemSet testSet, String setName, String hhName, String key, int seed) {
        String solved;
        String xmlFileName = "HyperHeuristics/" + key + '/' + hhName + '_' + seed + ".xml";
        RuleBasedHH hyperHeuristic = new RuleBasedHH(xmlFileName);
        solved = problem.solve(testSet, new HyperHeuristic[]{hyperHeuristic});
        System.out.println(solved);
        
        String[] solvedArr = solved.split("\t");
        saveCsv(solvedArr, "testSetEvaluations" + "/" + key + '/' + setName + '/' + setName + " Evaluation_" + hhName + '_' + key + '_' + seed + ".csv");
    }
    
    public static void saveCsv(String[] testResults, String name) {
        try (PrintWriter writer = new PrintWriter(new File(name))) {
            StringBuilder sb = new StringBuilder();
            
            for (int i = 0; i < testResults.length; i+=2) {
                sb.append(testResults[i]);
                sb.append(",");
                sb.append(testResults[i+1]);
            }
            
            writer.write(sb.toString());
            System.out.println("Finished writing " + name);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
