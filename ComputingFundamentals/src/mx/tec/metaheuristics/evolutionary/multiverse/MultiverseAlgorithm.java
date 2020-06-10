/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.tec.metaheuristics.evolutionary.multiverse;

import java.util.Collections;
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
    
    public MultiverseHHIndividual run(int multiverseSize, long maxEvaluations, boolean printMode) {
        // Initialize universes
        this.multiverse = generator.multiBigBang(multiverseSize);
        
        // Evaluate created universes
        double evaluation;
        for (MultiverseHHIndividual universe : this.multiverse) {
            evaluation = evaluator.evaluate(universe);
            universe.setEvaluation(evaluation);
        }
        
        // Sort universes according to its evaluation
        Collections.sort(this.multiverse);
        
        
        bestUniverse = this.multiverse.get(0).copy();
        
        return bestUniverse;
    }
}
