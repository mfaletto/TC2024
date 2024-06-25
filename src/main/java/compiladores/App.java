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
        compiladoresBaseListener escucha = new Escucha();

        // Conecto el objeto con Listeners al parser
        parser.addParseListener(escucha);
        
        parser.removeErrorListeners();
        parser.addErrorListener(new SyntaxErrorListener());

        parser.programa();
    }
}