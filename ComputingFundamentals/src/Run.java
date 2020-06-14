
import mx.tec.hermes.HyperHeuristic;
import mx.tec.hermes.frameworks.rulebased.RuleBasedHH;
import mx.tec.hermes.frameworks.rulebased.RuleBasedHHFramework;
import mx.tec.hermes.problems.ProblemSet;
import mx.tec.hermes.problems.ProblemSet.Subset;
import mx.tec.knapsack.problem.KP;
import mx.tec.metaheuristics.evolutionary.geneticAlgorithm.GeneticAlgorithm.Type;
import mx.tec.metaheuristics.evolutionary.multiverse.MultiverseAlgorithm;
import mx.tec.metaheuristics.evolutionary.multiverse.MultiverseFramework;

public class Run {

    public static void main(String[] args) {
        String setName = "instances/Training set";;
        String[] heuristics = heuristics = new String[]{"DEFAULT", "MAX_PROFIT", "MAX_PROFIT/WEIGHT", "MIN_WEIGHT", "MARKOVITZ"};
        KP problem = new KP();
        ProblemSet trainingSet = new ProblemSet(setName);
        RuleBasedHH hyperHeuristic;

//        String[] features = new String[]{"NORM_MEAN_WEIGHT", "NORM_MEAN_PROFIT", "NORM_MEAN_PROFIT_WEIGHT", "NORM_MEDIAN_WEIGHT", "NORM_MEDIAN_PROFIT", "NORM_MEDIAN_PROFIT_WEIGHT", "NORM_STD_WEIGHT", "NORM_STD_PROFIT", "NORM_STD_PROFIT_WEIGHT", "NORM_CORRELATION"};
        
        long[] testSeeds = {12345, 55555, 97563, 41528};
        String[][] testFeatures = {
            {"NORM_MEAN_WEIGHT", "NORM_MEAN_PROFIT", "NORM_MEAN_PROFIT_WEIGHT", "NORM_MEDIAN_WEIGHT", "NORM_MEDIAN_PROFIT", "NORM_MEDIAN_PROFIT_WEIGHT", "NORM_STD_WEIGHT", "NORM_STD_PROFIT", "NORM_STD_PROFIT_WEIGHT", "NORM_CORRELATION"},
            {"NORM_MEAN_WEIGHT", "NORM_MEAN_PROFIT", "NORM_MEAN_PROFIT_WEIGHT", "NORM_MEDIAN_WEIGHT", "NORM_CORRELATION"},
            {"NORM_MEDIAN_WEIGHT", "NORM_MEDIAN_PROFIT", "NORM_MEDIAN_PROFIT_WEIGHT", "NORM_CORRELATION"},
            {"NORM_STD_WEIGHT", "NORM_STD_PROFIT", "NORM_STD_PROFIT_WEIGHT", "NORM_CORRELATION"}
        };

        for (long seed : testSeeds) {
            for(String[] features : testFeatures) {
                //        hyperHeuristic = RuleBasedHHFramework.runGeneticAlgorithm(problem, trainingSet, features, heuristics, 50, 1000, 0.9, 0.1, Type.GENERATIONAL, true, seed);
                hyperHeuristic = MultiverseFramework.runMultiverseOptimizer(problem, trainingSet, features, heuristics, 50, 20, true, seed);
            }
        }

//        /*
//         * Saves the hyper-heuristic to a file (hyperHeuristic.xml).
//         */
//        hyperHeuristic.save("hyperHeuristic.xml");
//        /*
//         * Characterizes the instances in the test set.
//         */
//        System.out.println(problem.characterize(testSet, features));
//        /*
//         * Solves the test set by using the available heuristics.
//         */
//        System.out.println(problem.solve(testSet, heuristics));
//        /*
//         * Solves the test set by using the hyper-heuristic.
//         */
//        System.out.println(problem.solve(testSet, new HyperHeuristic[]{hyperHeuristic}));
    }
}
