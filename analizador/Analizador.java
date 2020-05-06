package analizador;

import java.io.*;
import analizadorLexico.*;
import simbolos.*;
import inter.*;

public class Analizador {
    private AnalizadorLexico lex;
    private Token busca;
    Ent sup = null;
    int usado = 0;

    public Analizador(AnalizadorLexico l) throws IOException {
        lex = l;
        mover();
    }

    void mover() throws IOException {
        busca = lex.escanear();
    }

    void error(String s) {
        throw new Error("cerca de linea " + lex.linea + ": " + s);
    }

    void coincidir(int t) throws IOException {
        if(busca.etiqueta == t) mover();
        else error("error de sintaxis");
    }

    public void programa() throws IOException {
        Instr s = bloque();
        int inicio = s.nuevaEtiqueta() ; int despues = s.nuevaEtiqueta(); 
        s.emitirEtiqueta(inicio); s.gen(inicio, despues); s.emitirEtiqueta(despues);
    }

    Instr bloque() throws IOException {
        coincidir('{');
        Ent entGuardado = sup;
        sup = new Ent(sup);

        decls();
        Instr s = instrs();
        coincidir('}');
        sup = entGuardado;
        return s;
    }

    void decls() throws IOException {
        while(busca.etiqueta == Etiqueta.BASIC) {
            Tipo p = tipo();
            Token tok = busca;
            coincidir(Etiqueta.ID);
            coincidir(';');
            Id id = new Id((Palabra)tok, p, usado);
            sup.put(tok, id);
            usado = usado + p.anchura;
        }
    }

    Tipo tipo() throws IOException {
        Tipo p = (Tipo)busca;
        coincidir(Etiqueta.BASIC);
        if(busca.etiqueta != '[') return p;
        else return dims(p);
    }

    Tipo dims(Tipo p) throws IOException {
        coincidir('[');
        Token tok = busca;
        coincidir(Etiqueta.NUM);
        coincidir(']');
        if(busca.etiqueta == '[') p = dims(p);
        return new Arreglo(((Num)tok).valor, p);
    }

}