package AST;
import java.util.ArrayList;

//IfStmt ::= ‘f’ ‘(’ Expr ‘)’ ‘{’ { Stmt } ‘}’ [ ‘e’ ‘{’ { Stmt } ‘}’ ]
public class IfStmt extends Stmt {
	private Expr expr;
	private ArrayList<Stmt> stmtIf;
	private ArrayList<Stmt> stmtElse;
    
    public IfStmt (Expr expr, ArrayList<Stmt> stmtIf, ArrayList<Stmt> stmtElse) {
    	this.expr = expr;
    	this.stmtIf = stmtIf;
    	this.stmtElse = stmtElse;
    }

    //Gets e Sets

}
