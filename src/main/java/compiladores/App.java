package compiladores;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, Compilador!!!");

        CharStream input = CharStreams.fromFileName("input/programa.txt");

        compiladoresLexer lexer = new compiladoresLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        compiladoresParser parser = new compiladoresParser(tokens);

        // create Listener
        Escucha escucha = new Escucha();

        parser.removeErrorListeners();  // Remover los listeners de errores por defecto

        // Conecto el objeto con Listeners al parser
        parser.addParseListener(escucha);

        
        parser.addErrorListener(new SyntaxErrorListener());  // Agregar nuestro listener de errores

        parser.programa();
    }
}