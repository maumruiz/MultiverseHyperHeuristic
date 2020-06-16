package mx.tec.metaheuristics.evolutionary.multiverse;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import mx.tec.hermes.HyperHeuristic;
import mx.tec.hermes.problems.Problem;
import mx.tec.hermes.problems.ProblemSet;
import mx.tec.metaheuristics.Evaluator;
import mx.tec.metaheuristics.Solution;

/**
 *
 * @author Mauricio Mendez Ruiz
 * @author Alejandra de Luna Pámanes
 */
public class MultiverseHHEvaluator extends Evaluator {
    private final Problem problem;
    private final List<String> problems;
    
    /**
     * Creates a new instance of <code>HHEvaluator</code>.
     *
     * @param problem The problem to solve.
     * @param set The that contains the problems used to evaluate a hyper-heuristic.
     */
    public MultiverseHHEvaluator(Problem problem, ProblemSet set) {
        this.problem = problem;
        problems = set.getFiles();
        nbEvaluations = 0;
    }
    
    @Override
    public double evaluate(Solution solution) {
        double objValue;
        Problem p;
        Constructor<?> constructor;
        HyperHeuristic hh;
        objValue = 0;
        nbEvaluations++;        
        try {            
            hh = ((MultiverseHHIndividual) solution).getHyperHeuristic();
            for (String file : problems) {
                constructor = problem.getClass().getConstructor(String.class);
                p = (Problem) constructor.newInstance(file);
                p.solve(hh);
                objValue += p.getObjValue();
            }
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            System.err.println(e);
            System.err.println(e.getCause());
            System.err.println("The system will halt.");
            System.exit(1);
        }
        return objValue / problems.size();
    }
    
    public double normalizedEvaluation(Solution solution, double max, double min) {
        double absValue = Math.abs(solution.getEvaluation());
        return Math.abs(absValue - max) / (max - min);
    }
}
