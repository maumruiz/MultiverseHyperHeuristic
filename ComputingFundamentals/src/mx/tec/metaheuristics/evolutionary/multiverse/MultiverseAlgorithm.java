/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.tec.metaheuristics.evolutionary.multiverse;

/**
 *
 * @author Mauricio
 */
public class MultiverseAlgorithm {
    private String saludo;
    
    public MultiverseAlgorithm(String saludo) {
        this.saludo = saludo;
    }
    
    public void saluda() {
        System.out.println(this.saludo);
    }
}
