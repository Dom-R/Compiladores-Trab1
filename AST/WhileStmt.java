package AST;

public class WhileStmt {
	private Expr expr;
	private ArrayList<Stmt> stmt;
	
	public WhileStmt(Expr expr, ArrayList<Stmt> stmt) {
		this.expr = expr;
		this.stmt = stmt;
	}
	
}