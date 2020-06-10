/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.tec.metaheuristics.evolutionary.multiverse;

import java.util.List;
import mx.tec.metaheuristics.Evaluator;
import mx.tec.metaheuristics.evolutionary.Selector;

/**
 *
 * @author Mauricio
 */
public class MultiverseAlgorithm {
    
    // TO DO: Change classes to the real ones (not the abstract ones)
    private MultiverseHHIndividual bestUniverse;
    private List<MultiverseHHIndividual> multiverse;
    
    private final MultiverseHHEvaluator evaluator;
    private final MultiverseHHGenerator generator;
    private final Selector selector;
    
    public MultiverseAlgorithm(MultiverseHHEvaluator evaluator, MultiverseHHGenerator generator, Selector selector) {
        this.evaluator = evaluator;
        this.generator = generator;
        this.selector = selector;
    }
    
    public MultiverseHHIndividual evolve(int multiverseSize, long maxEvaluations, boolean printMode) {
        this.multiverse = generator.multiBigBang(multiverseSize);
        
        double evaluation;
        for (MultiverseHHIndividual universe : this.multiverse) {
            evaluation = evaluator.evaluate(universe);
            System.out.println(evaluation);
            universe.setEvaluation(evaluation);
        }
        
        return null;
    }
}
