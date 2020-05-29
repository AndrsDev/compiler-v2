package inter;

import analizadorLexico.*;
import java.io.*;

public class Nodo {
    int linealex = 0;
    static int etiquetas = 0;

    Nodo() {
        linealex = AnalizadorLexico.linea;
    }

    void error(String s) {
        throw new Error("cerca de la linea " + linealex + ": " + s);
    }

    String operandos(String s){
        String result = "";
        String[] parts = s.split("=");
        String operation = parts[1];
        String[] items = operation.split(" ");

        for (int i = 0; i < items.length; i++) {
            if(items[i].matches("^[_a-z]\\w*$")){
                result+= items[i] + ", ";
            }
        }
        
        return result;
    }

    void writeDestino(String s) {
        try {
            String line = "";
            if(s.contains("=")){

                if(s.contains("+")){
                    line+= "ADD " + operandos(s);
                } else if(s.contains("-")){
                    line+= "SUB " + operandos(s);
                } else if(s.contains("*")){
                    line+= "MUL " + operandos(s);
                } else if(s.contains("/")){
                    line+= "DIV " + operandos(s);
                }

            } else {
                line+= s;
            }

            FileWriter fw = new FileWriter("./main/destino.txt", true);
            fw.write(line + '\n');
            fw.close();
        } catch (Exception e) {
            //Didn't write to file
        }
    }

    void writeIntermedio(String s) {
        try {
            FileWriter fw = new FileWriter("./main/intermedio.txt", true);
            fw.write(s);
            fw.close();
        } catch (Exception e) {
            //Didn't write to file
        }
    }

    public int nuevaEtiqueta() {
        return ++etiquetas;
    }

    public void emitirEtiqueta(int i) {
        writeIntermedio("L" + i + ":");
    }

    public void emitir(String s) {
        writeIntermedio("\t" + s + "\n");
        writeDestino(s);
    }
}