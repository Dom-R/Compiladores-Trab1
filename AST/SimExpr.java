package AST;
import java.util.ArrayList;

//SimExpr ::= [Unary] Term { AddOp Term }
public class SimExpr {
	
	class AddOpTerm {
		private char addOp;
		private Term term;

		public AddOpTerm (char addOp, Term term) {
			this.addOp = addOp;
			this.term = term;
		}
	}

	private char unary;
	private Term term;
	private ArrayList<AddOpTerm> addOpTerm;
    
    public SimExpr (char unary, Term term) {
    	addOpTerm = new ArrayList<AddOpTerm> ();
    	this.unary = unary;
    	this.term = term;
    }

    public void add (char addOp, Term term) {
    	addOpTerm.add (new AddOpTerm (addOp, term));
    }

    //Gets e Sets

}
