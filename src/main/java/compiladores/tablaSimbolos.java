package compiladores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
/*
enum Type{
    INT,
    DOUBLE,
    VOID
} */

class Symbol {
    private String name;
    private String type;
    private boolean initialized;
    private boolean used;

    public Symbol(String name, String type) {
        this.name = name;
        this.type = type;
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

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}



class Function extends Symbol { //argumentos
    private List<String> argumentos;

    public Function (String name, String type, List<String> argumentos){
        super(name, type);
        this.argumentos = argumentos;
    }

    public List<String> getArgumentos(){
        return argumentos;
    }
    //agregar get
}

class Variable extends Symbol {
    public Variable (String name, String type){
        super(name, type);
    }
}

class Contexto {

    private Map<String, Symbol> symbols; 

    public Contexto(){
        this.symbols = new HashMap<>();

    }

    public void addSymbol(Symbol symbol){
        symbols.put(symbol.getName(), symbol);
    }

    public Map<String, Symbol> getSymbols() {
        return symbols;
    }

    public Symbol findSymbol(Symbol symbol){
        for(Symbol symbol1 : symbols.values()){
            if(symbol1.getName().equals(symbol.getName()) ){
                return symbol;   
            }
        }
        return null;
    }
}




public class tablaSimbolos {

    private List<Contexto> contextos;

    public tablaSimbolos() {
        this.contextos = new ArrayList<>();
        this.contextos.add( new Contexto());
    }

    public void addContexto() {
        contextos.add(new Contexto());
    }

    public void delContexto() {
        if (contextos.size() > 1) {
            contextos.remove(contextos.size() - 1);
        }
    }

    public void addSymbol(Symbol symbol){
        contextos.get(contextos.size()-1).addSymbol(symbol);
    }

    public Symbol findSymbol(Symbol symbol){
        for(int i = contextos.size() - 1; i >= 0 ; i--){
            Symbol symbo1 = contextos.get(i).findSymbol(symbol);
            if(symbo1!=null){
                return symbo1;
            }
        }
        return null;
    }

    public boolean isDeclared(Symbol symbol) {
        if(findSymbol(symbol)!=null){
            if(symbol.isUsed()==true){
                return true;
            }
            return false;
        }else{
            return false;
        }
    }

    public boolean isInitialized(Symbol symbol) {

        if(findSymbol(symbol)!=null){
            if(symbol.isInitialized()==true){
                return true;
            }
            return false;
        }else{
            return false;
        }
    }

    public List<Contexto> getContextos() {
        return contextos;
    }
}


