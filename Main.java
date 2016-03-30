// comp 7

//import AST.*;

public class Main {
    public static void main( String []args ) {
        char []input = "vm(){ i x; d[] y341afas; c z23; }".toCharArray();
        
        Compiler compiler = new Compiler();
        
        //Program program  = compiler.compile(input);
		compiler.compile(input);
        //program.genC();
        //System.out.println("Resultado da expressao " +  program.eval());
    }
}