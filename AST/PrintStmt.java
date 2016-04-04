package AST;
import java.util.ArrayList;

public class PrintStmt extends Stmt {
	private Expr expr;
	private ArrayList<Expr> arrayExpr;
	
	public PrintStmt(Expr expr, ArrayList<Expr> arrayExpr) {
		this.expr = expr;
		this.arrayExpr = arrayExpr;
	}
}