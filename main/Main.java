//Version 0.0.3


package main;

import java.io .* ; 
import analizadorLexico.* ; 
import analizador.* ;



public class Main {
    public static void main(String[] args) throws IOException { 

        cleanFile("./main/destino.txt");
        cleanFile("./main/intermedio.txt");

        System.out.println();
        System.out.println("Ingresa el codigo y al terminar ingresa DONE: ");
        AnalizadorLexico lex = new AnalizadorLexico();
        Analizador analizar = new Analizador(lex);
        analizar.programa(); 
        System.out.write('\n');
        System.out.println("Código intermedio: intermedio.txt");
        System.out.println("Código destino: destino.txt");
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