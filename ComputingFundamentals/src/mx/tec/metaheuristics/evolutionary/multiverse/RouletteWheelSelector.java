package mx.tec.metaheuristics.evolutionary.multiverse;

import java.util.Arrays;
import java.util.List;
import mx.tec.metaheuristics.Solution;
import mx.tec.metaheuristics.evolutionary.Selector;

/**
 *
 * @author Mauricio Mendez Ruiz
 * @author Alejandra de Luna Pámanes
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
    
    public Solution[] select(List<? extends Solution> population, int selectionSize) {
        double[] cumulativeFitnesses = new double[population.size()];
        cumulativeFitnesses[0] = Math.abs(population.get(0).getEvaluation());
        
        // Set cumulative values in the array
        for (int i = 1; i < population.size(); i++)
        {
            double fitness = Math.abs(population.get(i).getEvaluation());
            cumulativeFitnesses[i] = cumulativeFitnesses[i - 1] + fitness;
        }
        
        Solution[] selection = new Solution[selectionSize];
        for (int i = 0; i < selectionSize; i++)
        {
            // Look for the postition where the random value should be inserted
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
