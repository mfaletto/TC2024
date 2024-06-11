package compiladores;

import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.*;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, Compilador!!!");

        CharStream input = CharStreams.fromFileName("input/programa.txt");

        compiladoresLexer lexer = new compiladoresLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        compiladoresParser parser = new compiladoresParser(tokens);

        // Crear la lista de errores sintácticos
        //List<String> syntaxErrors = new ArrayList<>();
        
        // Agregar el SyntaxErrorListener
        //parser.removeErrorListeners();
        //parser.addErrorListener(new SyntaxErrorListener(syntaxErrors));
        
        // Iniciar la compilación
        ParseTree tree = parser.programa();
        
        // Crear los listeners
        Escucha escucha = new Escucha();
        ErrorListener customListener = new ErrorListener();
        
        // Recorrer el árbol de derivación
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(escucha, tree);
        walker.walk(customListener, tree);

        // Agregar errores sintácticos al customListener
        //customListener.addSyntaxErrors(syntaxErrors);
        
        // Imprimir la tabla de símbolos y errores semánticos si hay
        System.out.println("Tabla de Símbolos:");
        tablaSimbolos symbolTable = customListener.getSymbolTable();
        for (Map.Entry<String, Symbol> entry : symbolTable.getSymbols().entrySet()) {
            Symbol symbol = entry.getValue();
            System.out.printf("Identificador: %s, Tipo: %s, Inicializado: %s\n",
                    symbol.getName(), symbol.getType(), symbol.isInitialized());
        }

        List<String> errors = customListener.getErrors();
        
        if (!errors.isEmpty()) {
            System.out.println("Errores:");
            for (String error : errors) {
                System.out.println(error);
            }
        } else {
            System.out.println("No se encontraron errores.");
        }
    }
}