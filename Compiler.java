import AST.*;
import java.util.ArrayList;
import java.util.Hashtable;

public class Compiler {

    public Program compile( char []p_input ) {
        input = p_input;
        tokenPos = 0;
        
        nextToken();   
        
        Program e = program();
        if (tokenPos != input.length)
          error("Fim de codigo esperado!");
          
        return e;
    }
    
    private char token;
    private int  tokenPos;
    private char []input;
    //private Hashtable<Character, Variable> symbolTable;     
}
