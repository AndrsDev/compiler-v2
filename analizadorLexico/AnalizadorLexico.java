package analizadorLexico;

import java.io.*;
import java.util.*;
import simbolos.*;

public class AnalizadorLexico {
    public static int linea = 1;
    char preanalisis = ' ';
    Hashtable<String, Palabra> palabras = new Hashtable<String, Palabra>();
    String fuente = " ";
    int cursor = 0;

    void reservar(Palabra w) {
        palabras.put(w.lexema, w);
    }

    void readFile() {
        try {
            File file = new File("./main/fuente.txt");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                fuente+= reader.nextLine();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public AnalizadorLexico() {
        reservar(new Palabra("if", Etiqueta.IF));
        reservar(new Palabra("else", Etiqueta.ELSE));
        reservar(new Palabra("while", Etiqueta.WHILE));
        reservar(new Palabra("do", Etiqueta.DO));
        reservar(new Palabra("break", Etiqueta.BREAK));
        reservar(Palabra.True);
        reservar(Palabra.False);
        reservar(Tipo.Int);
        reservar(Tipo.Char);
        reservar(Tipo.Bool);
        reservar(Tipo.Float);
        readFile();
    }

    void readch() throws IOException {
        if(cursor < fuente.length()){
            preanalisis = (char) fuente.charAt(cursor);
            cursor++;
        } else {
            preanalisis = (char) -1;
        }
    }

    boolean readch(char c) throws IOException {
        readch();
        if (preanalisis != c)
            return false;
        preanalisis = ' ';
        return true;
    }

    public Token escanear() throws IOException {

        for (;; readch()) {
            if (preanalisis == ' ' || preanalisis == '\t')
                continue;
            else if (preanalisis == '\n')
                linea = linea + 1;
            else
                break;
        }

        switch (preanalisis) {
            case '&':
                if (readch('&'))
                    return Palabra.and;
                else
                    return new Token('&');
            case '|':
                if (readch('|'))
                    return Palabra.or;
                else
                    return new Token('|');
            case '=':
                if (readch('='))
                    return Palabra.eq;
                else
                    return new Token('=');
            case '!':
                if (readch('='))
                    return Palabra.ne;
                else
                    return new Token('!');
            case '<':
                if (readch('='))
                    return Palabra.le;
                else
                    return new Token('<');
            case '>':
                if (readch('='))
                    return Palabra.ge;
                else
                    return new Token('>');
        }

        if (Character.isDigit(preanalisis)) {
            // Returns an Num Token
            int v = 0;
            do {
                v = 10 * v + Character.digit(preanalisis, 10);
                readch();
            } while (Character.isDigit(preanalisis));
            if (preanalisis != '.')
                return new Num(v);

            // Returns a Real Token if it is not a NUM Token.
            float x = v;
            float d = 10;

            for (;;) {
                readch();
                if (!Character.isDigit(preanalisis))
                    break;
                x = x + Character.digit(preanalisis, 10) / d;
                d = d * 10;
            }

            return new Real(x);
        }

        if (Character.isLetter(preanalisis)) {
            StringBuffer b = new StringBuffer();
            do {
                b.append(preanalisis);
                readch();
            } while (Character.isLetterOrDigit(preanalisis));

            String s = b.toString();
            Palabra w = (Palabra) palabras.get(s);
            if (w != null)
                return w;
            w = new Palabra(s, Etiqueta.ID);
            palabras.put(s, w);
            return w;
        }

        Token t = new Token(preanalisis);
        preanalisis = ' ';
        return t;
    }
}