// comp 7

import AST.*;

public class Main {
    public static void main( String []args ) {
        char []input = "vm(){i xx;d[] yy;c zz;f( r() ) { b;} e { f( !ab3 * s() - cd = t() ) { p( ab3 ) }}}".toCharArray();
        
        Compiler compiler = new Compiler();
        
        Program program  = compiler.compile(input);
		//compiler.compile(input);
        //program.genC();
        //System.out.println("Resultado da expressao " +  program.eval());
    }
}