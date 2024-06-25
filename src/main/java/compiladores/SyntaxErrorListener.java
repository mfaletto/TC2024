package compiladores;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;

public class SyntaxErrorListener extends BaseErrorListener {

    public SyntaxErrorListener() {}

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) throws ParseCancellationException {
        String error = "Error sintáctico en línea " + line + ":" + charPositionInLine + " - " + msg;

        if (msg.contains("missing ';' at")) {
            System.out.println("Error sintáctico: Falta punto y coma. Línea " + line + ": " + charPositionInLine);
        } else if (msg.contains("missing ')' at")) {
            System.out.println("Error sintáctico: Falta cierre de paréntesis. Línea " + line + ": " + charPositionInLine);
        } else if (msg.contains("missing '(' at")) {
            System.out.println("Error sintáctico: Falta apertura de paréntesis. Línea " + line + ": " + charPositionInLine);
        } else if (msg.contains("missing '}' at")) {
            System.out.println("Error sintáctico: Falta cierre de llave. Línea " + line + ": " + charPositionInLine);
        } else if (msg.contains("missing '{' at")) {
            System.out.println("Error sintáctico: Falta apertura de llave. Línea " + line + ": " + charPositionInLine);
        } else if (msg.contains("no viable alternative at input")) {
            System.out.println("Error sintáctico: Entrada no identificada cerca de '" + recognizer.getVocabulary().getDisplayName(charPositionInLine) + "' en línea " + line + ": " + charPositionInLine);
        } else {
            System.out.println(error);
        }
    }
}