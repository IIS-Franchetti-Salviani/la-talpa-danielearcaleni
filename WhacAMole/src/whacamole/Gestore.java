/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package whacamole;

import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author compu
 */
public class Gestore {

    private int punteggio;
    
    private ArrayList<Talpa> talpe = new ArrayList<>();
    private Random rd = new Random();

    public int esciTalpa() {
        return rd.nextInt(8);
    }

    public void addTalpa(Talpa t){
        talpe.add(t);
    }
    public int getPunteggio(){
        return punteggio;
    }
    public void setPunteggio(int punti){
        punteggio += punti;
    }
    
    @Override
    public String toString(){
        return "" + punteggio;
    }
}
