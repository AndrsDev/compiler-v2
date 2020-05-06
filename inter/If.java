package inter;
import simbolos.*;

public class If extends Instr {
    Expr expr;
    Instr instr;

    public If(Expr x, Instr s) {
        expr = x;
        instr = s;

        if(expr.tipo != Tipo.Bool)
            expr.error("Se requiere una expresión booleana en if");
    }

    public void gen(int b, int a) {
        int etiqueta = nuevaEtiqueta();
        expr.salto(0, a);
        emitirEtiqueta(etiqueta);
        instr.gen(etiqueta, a);
    }
    
}