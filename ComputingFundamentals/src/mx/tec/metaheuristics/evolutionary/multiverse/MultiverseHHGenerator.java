/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.tec.metaheuristics.evolutionary.multiverse;

import java.util.Random;
import mx.tec.metaheuristics.Generator;

/**
 *
 * @author Mauricio
 */
public class MultiverseHHGenerator extends Generator{
    // TODO: Consider generation without bits, but doubles
    private final static int MIN_INITIAL_RULES = 3, MAX_INITIAL_RULES = 5, BITS_PER_FEATURE = 10;
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
}
