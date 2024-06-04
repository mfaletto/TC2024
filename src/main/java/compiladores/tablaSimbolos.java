package compiladores;

import java.util.*;

// Enum para los tipos de datos
enum TipoDato {
    INT,
    CHAR,
    DOUBLE
}

// Clase Identificador
class Identificador {
    protected String nombre;
    protected TipoDato tipoDato;
    protected boolean inicializada;
    protected boolean utilizada;

    public Identificador(String nombre, TipoDato tipoDato) {
        this.nombre = nombre;
        this.tipoDato = tipoDato;
        this.inicializada = false;
        this.utilizada = false;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoDato getTipoDato() {
        return tipoDato;
    }

    public void setInicializada(boolean inicializada) {
        this.inicializada = inicializada;
    }

    public void setUtilizada(boolean utilizada) {
        this.utilizada = utilizada;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Tipo: " + tipoDato + ", Inicializada: " + inicializada + ", Utilizada: " + utilizada;
    }
}

// Clase Variable que hereda de Identificador
class Variable extends Identificador {
    public Variable(String nombre, TipoDato tipoDato) {
        super(nombre, tipoDato);
    }
}

// Clase Funcion que hereda de Identificador
class Funcion extends Identificador {
    private List<TipoDato> argumentos;

    public Funcion(String nombre, TipoDato tipoDato, List<TipoDato> argumentos) {
        super(nombre, tipoDato);
        this.argumentos = argumentos;
    }

    public List<TipoDato> getArgumentos() {
        return argumentos;
    }

    @Override
    public String toString() {
        return super.toString() + ", Argumentos: " + argumentos;
    }
}

// Clase Contexto
class Contexto {
    private Map<String, Identificador> identificadores;

    public Contexto() {
        this.identificadores = new HashMap<>();
    }

    public void addIdentificador(Identificador identificador) {
        identificadores.put(identificador.getNombre(), identificador);
    }

    public Identificador buscarIdentificador(String nombre) {
        return identificadores.get(nombre);
    }

    public Map<String, Identificador> getIdentificadores() {
        return identificadores;
    }
}

// Clase TablaSimbolos
class tablaSimbolos {
    private List<Contexto> contextos;

    public tablaSimbolos() {
        this.contextos = new ArrayList<>();
        this.contextos.add(new Contexto());
    }

    public void addContexto() {
        contextos.add(new Contexto());
    }

    public void delContexto() {
        if (contextos.size() > 1) {
            contextos.remove(contextos.size() - 1);
        }
    }

    public Identificador buscarIdentificador(String nombre) {
        for (int i = contextos.size() - 1; i >= 0; i--) {
            Identificador identificador = contextos.get(i).buscarIdentificador(nombre);
            if (identificador != null) {
                return identificador;
            }
        }
        return null;
    }

    public Identificador buscarIdentificadorLocal(String nombre) {
        return contextos.get(contextos.size() - 1).buscarIdentificador(nombre);
    }

    public void addIdentificador(Identificador identificador) {
        contextos.get(contextos.size() - 1).addIdentificador(identificador);
    }

    public void print() {
        for (Contexto contexto : contextos) {
            for (Map.Entry<String, Identificador> entry : contexto.getIdentificadores().entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }
    }
}