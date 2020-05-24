//import mx.tec.hermes.HyperHeuristic;
//import mx.tec.hermes.frameworks.rulebased.RuleBasedHH;
//import mx.tec.hermes.frameworks.rulebased.RuleBasedHHFramework;
//import mx.tec.hermes.problems.ProblemSet;
//import mx.tec.hermes.problems.ProblemSet.Subset;
//import mx.tec.knapsack.problem.KP;
//import mx.tec.metaheuristics.evolutionary.geneticAlgorithm.GeneticAlgorithm.Type;

import mx.tec.multiverseoptimization.Multiverse;

public class Run {

    public static void main(String[] args) {
        Multiverse multiverse = new Multiverse("Hello Universe");
        
        multiverse.saluda();
//        long seed;
//        String setName;
//        String[] features, heuristics;
//        KP problem;
//        ProblemSet trainingSet, testSet, set;
//        RuleBasedHH hyperHeuristic;
//        /*
//         * Initializes the random number generator.
//         */
//        seed = 12345;
//        /*
//         * Defines the features to characterize the knapsack instances (these are, at the moment, all the available
//         * features).
//         */
//        features = new String[]{"NORM_MEAN_WEIGHT", "NORM_MEAN_PROFIT", "NORM_CORRELATION"};
//        /*
//         * Defines the heuristics to select the next item (these are, at the moment, all the available heuristics).
//         */
//        heuristics = new String[]{"DEFAULT", "MAX_PROFIT", "MAX_PROFIT/WEIGHT", "MIN_WEIGHT"};
//        /*
//         * Defines the folder that contains the instances to be considered for this run.
//         */
//        setName = "instances";                          
//        /*
//         * Defines the instances to be used for training (60% of the instances in the folder).
//         */
//        trainingSet = new ProblemSet(setName, Subset.TRAIN, 0.60, seed);
//        /*
//         * Defines the instances to be used for testing (the remaining 40% of the instances in the folder).
//         */
//        testSet = new ProblemSet(setName, Subset.TEST, 0.60, seed);
//        /*
//         * Creates an empty instance of the knapsack problem.
//         */
//        problem = new KP();
//        /*
//         * Generates a new hyper-heuristic by using a genetic algorithm.
//         */
//        hyperHeuristic = RuleBasedHHFramework.runGeneticAlgorithm(problem, trainingSet, features, heuristics, 50, 2000, 0.9, 0.1, Type.GENERATIONAL, true, seed);
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
