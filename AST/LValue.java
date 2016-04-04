package AST;

public class LValue {
	private Expr expr;
	private Ident ident;
    
    public LValue (Ident ident, Expr expr) {
    	this.expr = expr;
    	this.ident = ident;
    }

    //Gets e Sets

}
