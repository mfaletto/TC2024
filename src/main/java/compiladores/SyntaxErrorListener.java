package compiladores;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import java.util.ArrayList;
import java.util.List;

public class SyntaxErrorListener extends BaseErrorListener {

    private final List<String> syntaxErrors;

    public SyntaxErrorListener(List<String> syntaxErrors) {
        this.syntaxErrors = syntaxErrors;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) throws ParseCancellationException {
        String error = "Error sintáctico en línea " + line + ":" + charPositionInLine + " - " + msg;
        syntaxErrors.add(error);
    }
}