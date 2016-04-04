package AST;
import java.util.ArrayList;

public class WhileStmt extends Stmt {
	private Expr expr;
	private ArrayList<Stmt> stmt;
	
	public WhileStmt(Expr expr, ArrayList<Stmt> stmt) {
		this.expr = expr;
		this.stmt = stmt;
	}
	
}