package compiladores;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;


public class SyntaxErrorListener extends BaseErrorListener {

    public SyntaxErrorListener() {}

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) throws ParseCancellationException {
        String error = "Error sintáctico en línea " + line + ":" + charPositionInLine + " - " + msg;

        if(msg.contains("';'")){
            System.out.println("Error sintáctico: Falta punto y coma. Linea " + line + ": " + charPositionInLine);
        }else if(msg.contains("')'")){
            System.out.println("Error sintáctico: Falta cierre de parentesis. Linea " + line + ": " + charPositionInLine);
        }else if(msg.contains("'('")){
            System.out.println("Error sintáctico: Falta apertura de parentesis. Linea " + line + ": " + charPositionInLine);
        }else if(msg.contains("'}'")){
            System.out.println("Error sintáctico: Falta cierre de llaves. Linea " + line + ": " + charPositionInLine);
        }else if(msg.contains("'{'")){
            System.out.println("Error sintáctico: Falta apertura de llaves. Linea " + line + ": " + charPositionInLine);
        }else if(msg.contains("sexo")){
            System.out.println("Error sintáctico: Entrada no identificada " + recognizer.getVocabulary().getDisplayName(charPositionInLine));
        }else{
            System.out.println(error);
        }
    }
}