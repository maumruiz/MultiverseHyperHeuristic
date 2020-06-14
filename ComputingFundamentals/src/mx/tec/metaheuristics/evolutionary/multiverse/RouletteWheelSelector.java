/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.tec.metaheuristics.evolutionary.multiverse;

import java.util.Arrays;
import java.util.List;
import mx.tec.metaheuristics.Solution;
import mx.tec.metaheuristics.evolutionary.Selector;

/**
 *
 * @author Mauricio
 */
public class RouletteWheelSelector extends Selector {
    
    public RouletteWheelSelector(long seed) {
        super(seed);
    }
    
    @Override
    public Solution[] select(List<Solution> population) {
        int selectionSize = 1;
        return select(population, selectionSize);
    }
    
    // Population array is supposed to be sorted in decreasing order
    public Solution[] select(List<? extends Solution> population, int selectionSize) {
        double[] cumulativeFitnesses = new double[population.size()];
        cumulativeFitnesses[0] = Math.abs(population.get(0).getEvaluation());
        
        for (int i = 1; i < population.size(); i++)
        {
            double fitness = Math.abs(population.get(i).getEvaluation());
            cumulativeFitnesses[i] = cumulativeFitnesses[i - 1] + fitness;
        }
        
        Solution[] selection = new Solution[selectionSize];
        for (int i = 0; i < selectionSize; i++)
        {
            double randomFitness = random.nextDouble() * cumulativeFitnesses[cumulativeFitnesses.length - 1];
            int index = Arrays.binarySearch(cumulativeFitnesses, randomFitness);
            
            if (index < 0)
            {
                // Convert negative insertion point to array index.
                index = Math.abs(index + 1);
            }
            
            selection[i] = population.get(index);
        }
        return selection;
    }
}
