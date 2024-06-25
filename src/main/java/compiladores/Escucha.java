package compiladores;

public class Escucha extends compiladoresBaseListener {
    
    private tablaSimbolos tablaSimbolos = new tablaSimbolos();

    @Override
    public void enterBloque (compiladoresParser.BloqueContext ctx){
        tablaSimbolos.addContexto();
    }

    @Override
    public void exitBloque (compiladoresParser.BloqueContext ctx){
        tablaSimbolos.delContexto();
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
        
        String type = (ctx.getChild(0).getText());
        String name = ctx.getChild(1).getText();

        
        Symbol symbol = new Symbol(name, type);
        
        if(tablaSimbolos.findSymbol(symbol)!=null){
            System.out.println("Error semántico: Doble declaración : Variable : " + name + " , Linea: " + ctx.start.getLine() );
        }else{
            tablaSimbolos.addSymbol(symbol);
            if(ctx.getChild(2).getText().equals("=")){
                symbol.setInitialized(true);
            }
        }
        String declarationText = ctx.getText();
        if(declarationText.contains(",,")){
            System.err.println("Error sintáctico: Formato incorrecto en la lista de declaración de variables: " + declarationText);
        }
    }

    @Override
    public void exitExpression (compiladoresParser.ExpressionContext ctx){ //VERIFICAR SI ESTA INICIALIZADO y DECLARADO //ERROR 2 y 3
        String name = ctx.getChild(0).getText();
        String type = ctx.getChild(1).getText();
        Symbol symbol = new Symbol(name, type);

        if (!tablaSimbolos.isInitialized(symbol)) {
            System.out.println("Error semántico: Uso de un identificador no inicializado : Variable : " + name + " , Linea: " + ctx.start.getLine() );
        }
    }

    @Override
    public void exitPrograma (compiladoresParser.ProgramaContext ctx){ //VERIFICAR SI ESTA UTILIZADA //ERROR 4
        //Symbol id = new Symbol (ctx.ID().toString())
        // obtener el tipo y el nombre, buscar si existe en la tabla de simbolos, sino agregarlo

        /*tablaSimbolos symbolTable = this.getSymbolTable();
        for (Map.Entry<String, Symbol> entry : symbolTable.getSymbols().entrySet()) {
            Symbol symbol = entry.getValue();
            if(!symbol.isInitialized() && symbol.isDeclared()){
                errors.add("Error semántico: Identificador declarado pero no usado " + symbol.getName());
            }
        }*/
    }

    
    @Override
    public void exitLlamadaFuncion (compiladoresParser.LlamadaFuncionContext ctx){ //VERIFICAR SI LA FUNCION ES UN ID (REVISAR EL DIAGRAMA DEL PROFE PARA LA TABLA SIMBOLOS)
        //Symbol id = new Symbol (ctx.ID().toString())
        // obtener el tipo y el nombre, buscar si existe en la tabla de simbolos, sino agregarlo
    }

    //VERICAR DECLARADO PERO NO USADO
}
