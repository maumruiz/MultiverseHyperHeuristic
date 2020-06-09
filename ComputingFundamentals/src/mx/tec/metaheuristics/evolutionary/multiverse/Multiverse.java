/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.tec.metaheuristics.evolutionary.multiverse;

import java.util.List;
import mx.tec.hermes.frameworks.rulebased.RuleBasedHHIndividual;
import mx.tec.metaheuristics.Evaluator;
import mx.tec.metaheuristics.Generator;
import mx.tec.metaheuristics.Solution;
import mx.tec.metaheuristics.evolutionary.Selector;

/**
 *
 * @author Mauricio
 */
public class Multiverse {
    // TO DO: Change classes to the real ones (not the abstract ones)
    private Solution best;
    private List<Solution> population;
    private final Evaluator evaluator;
    private final Generator generator;
    private final Selector selector;
    
    public Multiverse(Evaluator evaluator, Generator generator, Selector selector) {
        this.evaluator = evaluator;
        this.generator = generator;
        this.selector = selector;
    }
    
    public RuleBasedHHIndividual evolve(int populationSize, long maxEvaluations, boolean printMode) {
        return null;
    }
}
