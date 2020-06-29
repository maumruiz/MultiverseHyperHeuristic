
import mx.tec.hermes.HyperHeuristic;
import mx.tec.hermes.frameworks.rulebased.RuleBasedHH;
import mx.tec.hermes.problems.ProblemSet;
import mx.tec.knapsack.problem.KP;
import mx.tec.metaheuristics.evolutionary.multiverse.MultiverseFramework;
//import java.text.DecimalFormat;

public class Run {

    public static void main(String[] args) {
        long seed;
        String setName;
        String[] features, heuristics;
        KP problem;
        ProblemSet trainingSet, testSet;
        RuleBasedHH hyperHeuristic;
        /*
         * Initializes the random number generator.
         */
        seed = 37224;
        // 23451, 55555, 97563, 41528, 61249, 59274, 55489, 70599, 10936, 44816,
        // 15414, 29531, 95655, 34495, 25511, 41334, 14121, 37224, 80372, 64348,
        // 19244, 98800, 30089, 75657, 83087, 54306, 13762, 20434, 80792, 28294
        /*
         * Defines the features to characterize the knapsack instances. You can use all of them or only a subset of
         * them.
         */
        features = new String[]{"NORM_MEAN_WEIGHT", "NORM_MEAN_PROFIT", "NORM_MEAN_PROFIT_WEIGHT", "NORM_MEDIAN_WEIGHT", "NORM_MEDIAN_PROFIT", "NORM_MEDIAN_PROFIT_WEIGHT", "NORM_STD_WEIGHT", "NORM_STD_PROFIT", "NORM_STD_PROFIT_WEIGHT", "NORM_CORRELATION"};
        /*
         * Defines the heuristics to select the next item. You should let your system to decide which ones to apply. In
         * other words, use all of them as input for your system.
         */
        heuristics = new String[]{"DEFAULT", "MAX_PROFIT", "MAX_PROFIT/WEIGHT", "MIN_WEIGHT", "MARKOVITZ"};
        /*
         * Defines the folder that contains the instances to be considered for this run.
         */
        setName = "instances/Training Set";
        /*
         * Defines the instances to be used for training (60% of the instances in the folder).
         */
        //trainingSet = new ProblemSet(setName, Subset.TRAIN, 0.60, seed);
        trainingSet = new ProblemSet(setName);
        /*
         * Defines the instances to be used for testing (the remaining 40% of the instances in the folder).
         */
        //testSet = new ProblemSet(setName, Subset.TEST, 0.60, seed);
        testSet = new ProblemSet("instances/Test set A");
        /*
         * Creates an empty instance of the knapsack problem.
         */
        problem = new KP();
        /*
         * Generates a new hyper-heuristic by using the multiverse optimizer.
         */
        hyperHeuristic = MultiverseFramework.runMultiverseOptimizer(problem, trainingSet, features, heuristics, 50, 120, true, seed);                              
        /*
         * Saves the hyper-heuristic to a file (hyperHeuristic.xml).
         */
        hyperHeuristic.save("hyperHeuristic.xml");
//        RuleBasedHH readHyperHeuristic("hyperHeuristic.xml");
        /*
         * Characterizes the instances in the test set.
         */
        //System.out.println(problem.characterize(testSet, features));
        /*
         * Solves the test set by using the available heuristics.
         */
        //System.out.println(problem.solve(testSet, heuristics));
        /*
         * Solves the test set by using the hyper-heuristic.
         */
        System.out.println(problem.solve(testSet, new HyperHeuristic[]{hyperHeuristic}));
        //testSet = new ProblemSet("instances/Test set B");
        //System.out.println(problem.solve(testSet, new HyperHeuristic[]{hyperHeuristic}));
        testSet = new ProblemSet("instances/Test set C");
        System.out.println(problem.solve(testSet, new HyperHeuristic[]{hyperHeuristic}));
    }
}
