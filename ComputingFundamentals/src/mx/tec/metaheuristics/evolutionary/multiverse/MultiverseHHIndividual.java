/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.tec.metaheuristics.evolutionary.multiverse;

import java.util.BitSet;

/**
 *
 * @author Mauricio
 */
public class MultiverseHHIndividual extends RuleBHHIndividual{
    
    public MultiverseHHIndividual(int nbRules, int bitsPerFeature, long seed) {
        super(nbRules, bitsPerFeature, seed);
    }
    
    private MultiverseHHIndividual(MultiverseHHIndividual other) {
        super((RuleBHHIndividual) other);
    }
    
    public MultiverseHHIndividual copy() {
        return new MultiverseHHIndividual(this);
    }
}
