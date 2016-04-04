import AST.*;
import java.util.ArrayList;
import java.util.Hashtable;

public class Compiler {

	//Program ::= Decl
	public Program compile( char []p_input ) {
        input = p_input;
        tokenPos = 0;
        
		// Atribui o primeiro char a token
        nextToken();
		
        Program program = new Program(Decl());
        //if (tokenPos != input.length)
          //error("Fim de codigo esperado!");
          
        return program;
    }
	
	//Decl ::= ‘v’ ‘m’ ‘(’ ‘)’ StmtBlock
	public Decl Decl() {
		
		// Verifica se o linguagem comeca com vm()
		validarToken('v');
		validarToken('m');
		validarToken('(');
		validarToken(')');
		
		return new Decl(StmtBlock());
	}
	
	//StmtBlock ::= ‘{’ { VariableDecl } { Stmt } ‘}’
	public StmtBlock StmtBlock() {
		ArrayList<Variable> variableDecl = new ArrayList<Variable>();
		ArrayList<Stmt> stmt = new ArrayList<Stmt>(); // Stmt
		
		validarToken('{');
		
		// Verifica se ainda ha variaveis sendo declaradas
		while(token == 'i' || token == 'd' || token == 'c') {
			variableDecl.add(VariableDecl());
		}
		
		// Verificar o que colocar aqui
		while(token != '}') {
			stmt.add(Stmt());
		}
		
		validarToken('}');
		
		return new StmtBlock(variableDecl);
	}
	
	/*----------------------------------------*/
	
	//VariableDecl ::= Variable ‘;’
	//Variable ::= Type Ident
	//Type ::= StdType | ArrayType
	//StdType ::= ‘i’ | ‘d’ | ‘c’
	//ArrayType ::= StdType ‘[’ ‘]’
	public Variable VariableDecl() {
		char tipo;
		boolean flagArray = false;
		
		// Next Token pq nao precisamos diferenciar um identificador da variavel para outra
		// Logo podemos continuar com as verificacoes e metodos
		// No futuro temos que substituir isso por um switch para cada tipo de identificador, removendo assim o nextToken
		tipo = token;
		nextToken();
		
		// Verificacao se eh uma Array
		// No futuro temos que substituir isso por um metodo para aplicar a array
		if(token == '[') {
			nextToken();
			validarToken(']');
			flagArray = true;
		}
		
		//////////////////////////////////////
		// Verifica o nome da Variavel
		// Obrigatoriamente tem que ter uma letra, seguido por uma repeticao de letras ou numeros
		// Ex: c, cc, c1c, cccc1231ccc, etc
		
		// No futuro criar uma string para salvar o nome da variavel
		
		// Indent
		Variable variable = new Variable(new Type(tipo, flagArray),Ident());
		
		///////////////////////////////////////
		
		// Salvar a variavel no futuro
		
		validarToken(';');
		
		return variable;
	}
	
	/*----------------------------------------*/
	
	//Stmt ::= Expr ‘;’ | ifStmt | WhileStmt | BreakStmt | PrintStmt
	public Stmt Stmt() {
		
		switch(token) {
			case 'f':
				nextToken();
				return IfStmt();
				//break;
			case 'w':
				nextToken();
				return WhileStmt();
				//break;
			case 'b':
				nextToken();
				return BreakStmt();
				//break;
			case 'p':
				nextToken();
				return PrintStmt();
				//break;
			default:
				return Expr();
		}
		
	}
	
	//IfStmt ::= ‘f’ ‘(’ Expr ‘)’ ‘{’ { Stmt } ‘}’ [ ‘e’ ‘{’ { Stmt } ‘}’ ]
	public IfStmt IfStmt() {
		Expr expr;
		ArrayList<Stmt> stmtif = new ArrayList<Stmt>();
		ArrayList<Stmt> stmtelse = new ArrayList<Stmt>();
		
		validarToken('(');
		expr = Expr();
		validarToken(')');
		
		// Primeiro bloco do If
		validarToken('{');
		while(token != '}') {
			stmtif.add(Stmt());
		}
		validarToken('}');
		
		// Bloco do Else
		if(token == 'e') {
			nextToken();
			validarToken('{');
			while(token != '}') {
				stmtelse.add(Stmt());
			}
			validarToken('}');
		} else {
			stmtelse = null;
		}
		
		return new IfStmt(expr, stmtif, stmtelse);
		
	}
	
	//WhileStmt ::= ‘w’ ‘(’ Expr ‘)’ ‘{’ { Stmt } ‘}’
	public WhileStmt WhileStmt() {
		Expr expr;
		ArrayList<Stmt> stmt = new ArrayList<Stmt>();
		
		validarToken('(');
		expr = Expr();
		validarToken(')');
		
		validarToken('{');
		while(token != '}') {
			stmt.add(Stmt());
		}
		validarToken('}');
		
		return new WhileStmt(expr, stmt);
		
	}
	
	//BreakStmt ::= ‘b’ ‘;’
	public BreakStmt BreakStmt() {
		validarToken(';');
		
		return new BreakStmt();
	}
	
	//PrintStmt ::= ‘p’ ‘(’ Expr { ‘,’ Expr } ‘)’
	public PrintStmt PrintStmt() {
		Expr expr;
		ArrayList<Expr> arrayExpr = new ArrayList<Expr>();
		
		validarToken('(');
		expr = Expr();
		while(token != ')') {
			validarToken(',');
			arrayExpr.add(Expr());
		}
		validarToken(')');
		
		return new PrintStmt(expr, arrayExpr);
		
	}
	
	//Expr ::= SimExpr [ RelOp Expr]
	public Expr Expr() {
		SimExpr simexpr;
		char relOp = '\0';
		Expr expr = null;
		
		simexpr = SimExpr();
		
		// Verifica se tem RelOp
		if(token == '=' || token == '#' || token == '<' || token == '>' ) {
			relOp = token;
			nextToken();
			expr = Expr();
		}
		
		return new Expr(simexpr, relOp, expr);
	}
	
	//SimExpr ::= [Unary] Term { AddOp Term }
	public SimExpr SimExpr() {
		char unary = '\0';
		Term term;
		
		/* NOTA: Remover NextToken quando implementar a arvore */
		
		// Verifica se tem Unary
		if(token == '+' || token == '-' || token == '!') {
			unary = token;
			nextToken(); // Unary
		}
		
		// Chamar termo
		term = Term();
		
		SimExpr simexpr = new SimExpr(unary, term);
		
		// Verifica se tem AddOp
		while(token == '+' || token == '-') {
			char addOp = token;
			nextToken(); // AddOp
			simexpr.add(addOp,Term());
		}
		
		return simexpr;
	}
	
	//Term ::= Factor { MulOp Factor }
	public Term Term() {
		Term term = new Term(Factor());
		
		// Verifica se tem MulOp
		while(token == '*' || token == '/' || token == '%') {
			char mulOp = token;
			nextToken(); // MulOp
			term.add(mulOp,Factor());
		}
		
		return term;
	}
	
	//Factor ::= LValue ‘:’ Expr | LValue | ‘(’ Expr ‘)’ | ‘r’ ‘(’ ‘)’ | ‘s’ ‘(’ ‘)’ | ‘t’ ‘(’ ‘)’
	public Factor Factor() {
		
		switch(token) {
			case '(':
				nextToken();
				Factor factor = new Factor(null,Expr(),"");
				validarToken(')');
				return factor;
				//break;
			case 'r':
				nextToken();
				validarToken('(');
				validarToken(')');
				return new Factor(null,null,"r()");
				//break;
			case 's':
				nextToken();
				validarToken('(');
				validarToken(')');
				return new Factor(null,null,"s()");
				//break;
			case 't':
				nextToken();
				validarToken('(');
				validarToken(')');
				return new Factor(null,null,"t()");
				//break;
			default:
				LValue lvalue = LValue();
				Expr expr = null;
				if(token == ':') {
					nextToken();
					expr = Expr();
				}
				return new Factor(lvalue, expr, "");
		}
		
	}
	
	//LValue ::= Ident | Ident ‘[’ Expr ‘]’
	public LValue LValue() {
		Ident ident = Ident();
		Expr expr = null;
		
		if(token == '[') {
			nextToken();
			expr = Expr();
			validarToken(']');
		}
		
		return new LValue(ident, expr);
	}
	
	//Ident ::= Letter { Letter | Digit }
	public Ident Ident() {
		String identificador = new String();
		
		if(Character.isLetter(token)) { // Verificou se tem uma letra
			identificador += token;
			nextToken();
			while(Character.isLetter(token) || Character.isDigit(token)) {	// Verifica se é uma letra ou numero
				identificador += token;
				nextToken();
			}
		} else {
			error("Letra esperada!");
		}
		
		return new Ident(identificador);
	}
	
	//RelOp ::= ‘=’ | ‘#’ | ‘<’ | ‘>’
	public void RelOp() {}
	
	//AddOp ::= ‘+’ | ‘-’
	public void AddOp() {}
	
	//MulOp ::= ‘*’ | ‘/’ | ‘%’
	public void MulOp() {}
	
	//Unary ::= ‘+’ | ‘-’ | ‘!’
	public void Unary() {}
	
	//Digit ::= ‘0’ | ‘1’ | ... | ‘9’
	public void Digit() {}
	
	//Letter ::= ‘A’ | ‘B’ | ... | ‘Z’ | ‘a’ | ‘b’ | ... | ‘z’
    public void Letter() {}
	
	// Valida Caracter com Token
	public void validarToken(char tokenProcurado) {
		if(token != tokenProcurado) {
			error( tokenProcurado + " esperado!");
		} else {
			nextToken();
		}
	}
	
	// NextToken
	public void nextToken() {

		while(tokenPos < input.length && ( input[tokenPos] == ' ' || input[tokenPos] == '\n' ) ){
			tokenPos++;
		}
		
		if(tokenPos >= input.length)
			token = '\0';
		else {
			token = input[tokenPos];
			System.out.print(" " + token + " ");
			tokenPos++;
		}
	
	}
	
	// Error
    private void error(String errorMsg) {
        if ( tokenPos == 0 ) 
          tokenPos = 1; 
        else 
          if ( tokenPos >= input.length )
            tokenPos = input.length;
        
        String strInput = new String( input, tokenPos - 1, input.length - tokenPos + 1 );
        String strError = "Error at \"" + strInput + "\"";
        System.out.println( strError );
        System.out.println( errorMsg );
        throw new RuntimeException(strError);
    }
	
    private char token;
    private int  tokenPos;
    private char []input;
    //private Hashtable<Character, Variable> symbolTable;     
}
