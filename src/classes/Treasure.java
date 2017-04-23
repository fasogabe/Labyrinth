/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import ch.aplu.jgamegrid.Actor;

/**
 *
 * @author seanflynn
 */
public class Treasure extends Actor {
    public Treasure(String type){
        super(true, "sprites/"+type.toLowerCase()+".png");
                  


    }
    
    
}
