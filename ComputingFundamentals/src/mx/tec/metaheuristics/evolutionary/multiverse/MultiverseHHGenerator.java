package mx.tec.metaheuristics.evolutionary.multiverse;

import java.util.ArrayList;
import java.util.Random;
import mx.tec.metaheuristics.Generator;

/**
 *
 * @author Mauricio Mendez Ruiz
 * @author Alejandra de Luna Pámanes
 */
public class MultiverseHHGenerator extends Generator{
    private final static int MIN_INITIAL_RULES = 3, MAX_INITIAL_RULES = 6, BITS_PER_FEATURE = 10;
    private final Random random;
    
    /**
     * Creates a new instance of <code>MultiverseHHGenerator</code>.
     *
     * @param seed The seed to initialize the random number generator.
     */
    public MultiverseHHGenerator(long seed) {
        random = new Random(seed);
    }
    
    @Override
    public MultiverseHHIndividual generate() {
        int nbRules;
        nbRules = MIN_INITIAL_RULES + random.nextInt(MAX_INITIAL_RULES - MIN_INITIAL_RULES);
        return new MultiverseHHIndividual(nbRules, BITS_PER_FEATURE, random.nextLong());
    }
    
    public MultiverseHHIndividual bigBang() {
        return generate();
    }
    
    public ArrayList<MultiverseHHIndividual> multiBigBang(int size) {
        if (size <= 0) {
            System.out.println("The multiverse must contain at least one universe in order to run the multiverse algorithm.");
            System.out.println("The system will halt.");
            System.exit(1);
        }
        
        ArrayList<MultiverseHHIndividual> multiverse = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            multiverse.add(bigBang());
        }
        
        return multiverse;
    }
}
