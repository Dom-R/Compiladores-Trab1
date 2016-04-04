package AST;

public class LValue {
	private Expr expr;
	private Ident ident;
    
    public LValue (Expr expr, Ident ident) {
    	this.expr = expr;
    	this.ident = ident;
    }

    //Gets e Sets

}
