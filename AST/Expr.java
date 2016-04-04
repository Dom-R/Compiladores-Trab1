package AST;

public class Expr extends Stmt {
	
	private SimExpr simexpr;
	private char relOp;
	private Expr expr;
	
	public Expr(SimExpr simexpr, char relOp, Expr expr) {
		this.simexpr = simexpr;
		this.relOp = relOp;
		this.expr = expr;
	}
	
}