/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.tec.metaheuristics.multiverseoptimizer;

/**
 *
 * @author Mauricio
 */
public class MultiverseOptimizer {
    private String saludo;
    
    public MultiverseOptimizer(String saludo) {
        this.saludo = saludo;
    }
    
    public void saluda() {
        System.out.println(this.saludo);
    }
