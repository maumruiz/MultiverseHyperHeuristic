/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.tec.multiverseoptimization;

/**
 *
 * @author Mauricio
 */
public class Multiverse {
    private String saludo;
    
    public Multiverse(String saludo) {
        this.saludo = saludo;
    }
    
    public void saluda() {
        System.out.println(this.saludo);
    }
}

