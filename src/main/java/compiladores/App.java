package compiladores;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

// Las diferentes entradas se explicaran oportunamente
public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, Compilador!!!");
        // create a CharStream that reads from file
        CharStream input = CharStreams.fromFileName("input/programa.txt");

        // create a lexer that feeds off of input CharStream
        compiladoresLexer lexer = new compiladoresLexer(input);
        
        // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        
        // create a parser that feeds off the tokens buffer
        compiladoresParser parser = new compiladoresParser(tokens);
                
        // create Listener
        compiladoresBaseListener escucha = new Escucha();                     // Para ver todos los pasos del nodo

        //Conecto el objeto con Listeners al parser 
        parser.addParseListener(escucha);                                     //abrir el escucha

        // Solicito al parser que comience indicando una regla gramatical
        // En este caso la regla es el simbolo inicial
        parser.programa();
        ParseTree tree =  parser.programa();                                   //Sale el arbol sintactico

        // Conectamos el visitor
         //Caminante visitor = new Caminante();
         //visitor.visit(tree);
         //System.out.println(visitor);
         //System.out.println(visitor.getErrorNodes());

        // Imprime el arbol obtenido
        //System.out.println(tree);
        //System.out.println(tree.toStringTree(parser));                         //Sale el arbol sintactico parte2
        //System.out.println(escucha);
        
    }
}
