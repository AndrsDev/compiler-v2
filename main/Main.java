//Version 0.0.3


package main;

import java.io .* ; 
import analizadorLexico.* ; 
import analizador.* ;



public class Main {
    public static void main(String[] args) throws IOException { 
        cleanFile("./main/destino.txt");
        cleanFile("./main/intermedio.txt");

        AnalizadorLexico lex = new AnalizadorLexico();
        Analizador analizar = new Analizador(lex);
        analizar.programa(); 

        System.out.println();
        System.out.println("Código fuente: fuente.txt");
        System.out.println("Código intermedio: intermedio.txt");
        System.out.println("Código destino: destino.txt" + "\n");
    }

    static void cleanFile(String dir){
        try {
            FileWriter fw = new FileWriter(dir);
            fw.write("");
            fw.close();
        } catch (Exception e) {
            //File not found.
        }
    }
}