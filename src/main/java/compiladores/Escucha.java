package compiladores;

import java.util.Map;
import org.antlr.v4.runtime.Token;

public class Escucha extends compiladoresBaseListener {
    
    private tablaSimbolos tablaSimbolos = new tablaSimbolos();

    @Override
    public void enterBloque (compiladoresParser.BloqueContext ctx){
        tablaSimbolos.addContexto();

    }

    @Override
    public void exitBloque (compiladoresParser.BloqueContext ctx){
        tablaSimbolos.delContexto();

        // Verificar que las llaves de apertura y cierre están presentes
        Token startToken = ctx.getStart();
        Token stopToken = ctx.getStop();

        if (startToken.getType() != compiladoresParser.LLA) {
            System.out.println("Error sintáctico: Falta llave de apertura '{' en línea " + startToken.getLine() + ":" + startToken.getCharPositionInLine());
        }

        if (stopToken == null || stopToken.getType() != compiladoresParser.LLC) {
            System.out.println("Error sintáctico: Falta llave de cierre '}' en línea " + startToken.getLine() + ":" + startToken.getCharPositionInLine());
        }
    }
    
    @Override
    public void exitAsignacion (compiladoresParser.AsignacionContext ctx){ //INICIALIZAR

        String name = (ctx.getChild(0).getText());
        String type = ctx.getChild(1).getText();

        
        Symbol symbol = new Symbol(name, type);
        
        if(tablaSimbolos.findSymbol(symbol)!=null){
            if(ctx.getChild(2).getText().equals("=")){
                symbol.setUsed(true);
            }
        }else{
            System.out.println("Error semántico: Uso de un identificador no declarado : Variable : " + name + " , Linea: " + ctx.start.getLine() );
        }
    }

    @Override
    public void exitDeclaracion (compiladoresParser.DeclaracionContext ctx){ //DOBLE DECLARACION Y INICIALIZAR? //ERROR 1

        String type = ctx.getChild(0) != null ? ctx.getChild(0).getText() : "tipo_desconocido";
        String name = ctx.getChild(1) != null ? ctx.getChild(1).getText() : "nombre_desconocido";

        Symbol symbol = new Symbol(name, type);

        if(tablaSimbolos.findSymbol(symbol)!=null){
            System.out.println("Error semántico: Doble declaración : Variable : " + name + " , Linea: " + ctx.start.getLine() );
        }else{
            tablaSimbolos.addSymbol(symbol);
            symbol.setInitialized(true);
        }
        String declarationText = ctx.getText();
        if(declarationText.contains(",,")){
            System.err.println("Error sintáctico: Formato incorrecto en la lista de declaración de variables: " + declarationText);
        }
    }

    @Override
    public void exitExpression (compiladoresParser.ExpressionContext ctx){ //VERIFICAR SI ESTA INICIALIZADO y DECLARADO //ERROR 2 y 3
        String name = ctx.getChild(0).getText();
        String type = "";
        Symbol symbol = new Symbol(name, type);
        
        if (!tablaSimbolos.isInitialized(symbol)) {

            System.out.println("Error semántico: Uso de un identificador no inicializado : Variable : " + name + " , Linea: " + ctx.start.getLine() );
        }
    }

    @Override
    public void exitPrograma (compiladoresParser.ProgramaContext ctx){ //VERIFICAR SI ESTA UTILIZADA //ERROR 4
       
         for (Contexto contexto : tablaSimbolos.getContextos()) {
            for (Map.Entry<String, Symbol> entry : contexto.getSymbols().entrySet()) {
                Symbol symbol = entry.getValue();
                if (!symbol.isUsed()) {
                    System.out.println("Advertencia: Variable declarada pero no usada : Variable : " + symbol.getName());
                }
            }
        }
    }
}
