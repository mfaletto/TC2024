package compiladores;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

import compiladores.compiladoresParser.DeclaracionContext;
import compiladores.compiladoresParser.InstruccionContext;
import compiladores.compiladoresParser.ProgramaContext;

public class Escucha extends compiladoresBaseListener {
    private Integer nodos = 0;
    private Integer hojas = 0;

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        // TODO Auto-generated method stub
        nodos++;
        // System.out.println("Entro a NODO");
    }

    @Override
    public void enterInstruccion(InstruccionContext ctx) {
        // TODO Auto-generated method stub
        // System.out.println("Entro a INSTRUCCION");
    }

    @Override
    public void enterPrograma(ProgramaContext ctx) {
        // TODO Auto-generated method stub
        System.out.println("Comienza la compilacion");
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        // TODO Auto-generated method stub
        // System.out.println("Salgo de NODO");
    }

    @Override
    public void exitPrograma(ProgramaContext ctx) {
        // TODO Auto-generated method stub
        System.out.println("Fin de la compilacion");
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        // TODO Auto-generated method stub
        hojas++;
        System.out.println("Estoy en una HOJA --> " + node.getText());
    }
    
    @Override
    public void enterDeclaracion(DeclaracionContext ctx) {
        System.out.println(" inicio : " + ctx.getText());
    }

    @Override
    public void exitDeclaracion(DeclaracionContext ctx) {
        System.out.println("  fin   : " + ctx.getText());
    }

    @Override
    public String toString() {
        return "Escucha [nodos=" + nodos + ", hojas=" + hojas + "]";
    }
    
}
