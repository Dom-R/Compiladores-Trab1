package AST;

public class Variable {
	
	private Type type;
	private Ident ident;
	
	public Variable(Type type, Ident ident) {
		this.type = type;
		this.ident = ident;
	}
}