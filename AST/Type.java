package AST;
import java.util.ArrayList;

public class Type {

    private Boolean arrayBool; //Saber se é array (true) ou não (false)
    private char tipo; //tipo = 'i', 'd' ou 'c'

    public Type(Boolean arrayBool, char tipo) {
        this.arrayBool = arrayBool;
        this.tipo = tipo;
    }

    //Gets e Sets
    public void setArrayBool (Boolean arrayBool) {
    	this.arrayBool = arrayBool;
    }

    public void setTipo (char tipo) {
    	this.tipo = tipo;
    }

    public Boolean getArrayBool () {
        return this.arrayBool;
    }

    public char getTipo () {
        return this.tipo;
    }

}
