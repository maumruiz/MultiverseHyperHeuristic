
import java.util.ArrayList;
import mx.tec.hermes.HyperHeuristic;
import mx.tec.hermes.frameworks.rulebased.RuleBasedHH;
import mx.tec.hermes.problems.ProblemSet;
import mx.tec.knapsack.problem.KP;
import mx.tec.metaheuristics.evolutionary.multiverse.MultiverseFramework;
//import java.text.DecimalFormat;

public class Run {
    public static void main(String[] args) {
        ArrayList<RuleBasedHH> hyperHeuristics;
        
        long[] seeds = {23451, 55555, 97563, 41528, 61249, 59274, 55489, 70599, 10936, 44816, 15414, 29531, 95655, 34495, 25511, 41334, 14121, 37224, 80372, 64348, 19244, 98800, 30089, 75657, 83087, 54306, 13762, 20434, 80792, 28294};        
        String[] features = new String[]{"NORM_MEAN_WEIGHT", "NORM_MEAN_PROFIT", "NORM_MEAN_PROFIT_WEIGHT", "NORM_MEDIAN_WEIGHT", "NORM_MEDIAN_PROFIT", "NORM_MEDIAN_PROFIT_WEIGHT", "NORM_STD_WEIGHT", "NORM_STD_PROFIT", "NORM_STD_PROFIT_WEIGHT", "NORM_CORRELATION"};        
        String[] heuristics = new String[]{"DEFAULT", "MAX_PROFIT", "MAX_PROFIT/WEIGHT", "MIN_WEIGHT", "MARKOVITZ"};
        ProblemSet trainingSet = new ProblemSet("instances/Training Set");
        String testSetName = "Test Set C";
        ProblemSet testSet = new ProblemSet("instances/" + testSetName);
        KP problem = new KP();
        // File identifiers
        String hhName = "HyperHeuristic";
        String key = "AllFeatures_HeuristicWH";
        
        // hyperHeuristic = MultiverseFramework.runMultiverseOptimizer(problem, trainingSet, features, heuristics, 50, 120, true, seed);
        // hyperHeuristic.save("hyperHeuristic.xml");
        
        MultiverseFramework.runMultipleMultiverse(problem, trainingSet, features, heuristics, 50, 120, true, seeds, hhName, key, 2, 30, true);

        MultiverseFramework.testMultipleHHFromXML(problem, testSet, testSetName, hhName, key, 1, 30);
        
        //System.out.println(problem.characterize(testSet, features));
        //System.out.println(problem.solve(testSet, heuristics));
        
        // testSet = new ProblemSet("instances/Test set A");
        // System.out.println(problem.solve(testSet, new HyperHeuristic[]{hyperHeuristic}));
    }
}
