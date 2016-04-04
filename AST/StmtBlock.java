package AST;
import java.util.ArrayList;

public class StmtBlock {
	private ArrayList<Variable> variableDecl;
	
	public StmtBlock(ArrayList<Variable> variableDecl) {
		this.variableDecl = variableDecl;
	}
	
}