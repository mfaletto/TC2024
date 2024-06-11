package compiladores;

import java.util.HashMap;
import java.util.Map;

public class tablaSimbolos {
    private Map<String, Symbol> symbols;

    public tablaSimbolos() {
        symbols = new HashMap<>();
    }

    public void addSymbol(String name, String type, boolean initialized) throws Exception {
        if (symbols.containsKey(name)) {
            throw new Exception("Error semántico: Doble declaración del identificador " + name);
        }
        symbols.put(name, new Symbol(name, type, initialized));
    }

    public Symbol getSymbol(String name) {
        return symbols.get(name);
    }

    public boolean isDeclared(String name) {
        return symbols.containsKey(name);
    }

    public boolean isInitialized(String name) {
        Symbol symbol = symbols.get(name);
        return symbol != null && symbol.isInitialized();
    }

    public void initializeSymbol(String name) throws Exception {
        if (!symbols.containsKey(name)) {
            throw new Exception("Error semántico: Uso de un identificador no declarado " + name);
        }
        symbols.get(name).setInitialized(true);
    }

    public Map<String, Symbol> getSymbols() {
        return symbols;
    }
}

class Symbol {
    private String name;
    private String type;
    private boolean initialized;

    public Symbol(String name, String type, boolean initialized) {
        this.name = name;
        this.type = type;
        this.initialized = initialized;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }
}