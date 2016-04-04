package AST;
import java.util.ArrayList;

public class Term {
	
	class MulOpFactor {
		private char mulOp;
		private Factor factor;

		public MulOpFactor (char mulOp, Factor factor) {
			this.mulOp = mulOp;
			this.factor = factor;
		}
	}

	private Factor factor;
	private ArrayList<MulOpFactor> mulOpFactor;
    
    public Term (Factor factor) {
    	mulOpFactor = new ArrayList<MulOpFactor> ();
    	this.factor = factor;
    }

    public void add (char mulOp, Factor factor) {
    	mulOpFactor.add (new MulOpFactor (mulOp, factor));
    }

    //Gets e Sets

}
