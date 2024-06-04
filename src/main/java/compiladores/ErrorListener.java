package compiladores;

import org.antlr.v4.runtime.*;

public class ErrorListener extends BaseErrorListener {

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        String sourceName = recognizer.getInputStream().getSourceName();
        if (!sourceName.isEmpty()) {
            sourceName = String.format("%s:%d:%d: ", sourceName, line, charPositionInLine);
        }
        
        if (msg.contains("no viable alternative") || msg.contains("mismatched input") || msg.contains("extraneous input")) {
            Token offendingToken = (Token) offendingSymbol;
            Parser parser = (Parser) recognizer;
            String expectedTokens = parser.getExpectedTokens().toString(parser.getVocabulary());
            
            if (expectedTokens.contains("';'")) {
                System.err.println(sourceName + "Error de sintaxis en línea " + line + ":" + charPositionInLine + ". Posible falta de punto y coma.");
            } else {
                System.err.println(sourceName + "Error de sintaxis en línea " + line + ":" + charPositionInLine + ". " + msg);
            }
        } else {
            System.err.println(sourceName + "Error en línea " + line + ":" + charPositionInLine + ". " + msg);
        }
    }
}