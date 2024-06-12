package compiladores;

import org.antlr.v4.runtime.tree.ErrorNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ErrorListener extends compiladoresBaseListener{
    private tablaSimbolos tablaSimbolos;
    private List<String> errors;

    public ErrorListener() {
        tablaSimbolos = new tablaSimbolos();
        errors = new ArrayList<>();
    }

    public List<String> getErrors() {
        return errors;
    }
    
    public tablaSimbolos getSymbolTable() {
        return tablaSimbolos;
    }

    @Override
    public void enterDeclaracion(compiladoresParser.DeclaracionContext ctx) {
        
        String declarationText = ctx.getText();
        // Asegúrate de que la declaración termina con un punto y coma
        if (!declarationText.endsWith(";")) {
            System.err.println("Error sintáctico: Falta el punto y coma en la declaración: " + declarationText);
        }
        // Verifica que las variables estén separadas correctamente
        else if(declarationText.contains(",,")) {
            System.err.println("Error sintáctico: Formato incorrecto en la lista de declaración de variables: " + declarationText);
        }
        else{
            String type = ctx.getChild(0).getText();
            String name = ctx.getChild(1).getText();
            boolean initialized = ctx.getChild(2).getText().equals("=");
            
            try {
                tablaSimbolos.addSymbol(name, type, initialized);
                
            } catch (Exception e) {
                errors.add(e.getMessage());
            }
        }
        
        // Check for the presence of a semicolon
        /*if (ctx.getChildCount() < 3 || !ctx.getChild(2).getText().equals(";")) {
            errors.add("Error sintáctico: Se esperaba un punto y coma ';' después de la declaración de " + name + " en la línea " + ctx.start.getLine());
        }*/
    }

    @Override
    public void enterAsignacion(compiladoresParser.AsignacionContext ctx) {
        String name = ctx.getChild(0).getText();
        
        try {
            if (!tablaSimbolos.isDeclared(name)) {
                errors.add("Error semántico: Uso de un identificador no declarado " + name);
            } else {
                tablaSimbolos.initializeSymbol(name);
            }
        } catch (Exception e) {
            errors.add(e.getMessage());
        }
        // Check for the presence of a semicolon
       /*  if (ctx.getChildCount() < 5 || !ctx.getChild(4).getText().equals(";")) {
            errors.add("Error sintáctico: Se esperaba un punto y coma ';' después de la asignación a " + name + " en la línea " + ctx.start.getLine());
        }*/
    }

    @Override
    public void enterExpression(compiladoresParser.ExpressionContext ctx) {
        String name = ctx.getChild(0).getText();
        
        try {
            if (!tablaSimbolos.isInitialized(name)) {
                errors.add("Error semántico: Uso de un identificador no inicializado " + name);
            }
        } catch (Exception e) {
            errors.add(e.getMessage());
        }
    }

    /*@Override
    public void enterPuntocoma(compiladoresParser.PuntocomaContext ctx) {
        String text = ctx.getText(); // Obtener el texto del contexto

        if (!";".equals(text)) { // Verificar si el texto no es un punto y coma
            errors.add("Error sintáctico: Se esperaba un punto y coma, pero se encontró '" + text + "' en la línea " + ctx.start.getLine());
        }
    }*/

    @Override
    public void exitPrograma(compiladoresParser.ProgramaContext ctx) {
        tablaSimbolos symbolTable = this.getSymbolTable();
            for (Map.Entry<String, Symbol> entry : symbolTable.getSymbols().entrySet()) {
                Symbol symbol = entry.getValue();
                if(!symbol.isInitialized() && symbolTable.isDeclared(symbol.getName())){
                    errors.add("Error semántico: Identificador declarado pero no usado " + symbol.getName());
                }
            }
    }


    @Override
    public void visitErrorNode(ErrorNode node) {
        errors.add("Error sintáctico en: " + node.getText());
    }

    // Método para combinar errores de sintaxis
    //public void addSyntaxErrors(List<String> syntaxErrors) {
    //    errors.addAll(syntaxErrors);
    //}
}
