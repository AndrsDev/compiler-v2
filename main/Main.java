//Version 0.0.2


package main;

import java.io .* ; 
import analizadorLexico.* ; 
import analizador.* ;



public class Main {
    public static void main(String[] args) throws IOException { 

        System.out.println();
        System.out.println("Ingresa el codigo y al terminar ingresa DONE: ");
        AnalizadorLexico lex = new AnalizadorLexico();
        Analizador analizar = new Analizador(lex);
        analizar.programa(); 
        System.out.write('\n');
    }
}