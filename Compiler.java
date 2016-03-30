//import AST.*;
import java.util.ArrayList;
import java.util.Hashtable;

public class Compiler {

	//Program ::= Decl
    //public Program compile( char []p_input ) {
	public void compile( char []p_input ) {
        input = p_input;
        tokenPos = 0;
        
		// Atribui o primeiro char a token
        nextToken();
        
		Decl();
		
        //Program e = Decl();
        //if (tokenPos != input.length)
          //error("Fim de codigo esperado!");
          
        //return e;
    }
	
	//Decl ::= ‘v’ ‘m’ ‘(’ ‘)’ StmtBlock
	public void Decl() {
		
		// Verifica se o linguagem comeca com vm()
		validarToken('v');
		validarToken('m');
		validarToken('(');
		validarToken(')');
		
		StmtBlock();
	}
	
	//StmtBlock ::= ‘{’ { VariableDecl } { Stmt } ‘}’
	public void StmtBlock() {
		
		validarToken('{');
		
		// Verifica se ainda ha variaveis sendo declaradas
		while(token == 'i' || token == 'd' || token == 'c') {
			VariableDecl();
		}
		
		// Verificar o que colocar aqui
		while(token != '}') {
			Stmt();
		}
		validarToken('}');
		
	}
	
	/*----------------------------------------*/
	
	//VariableDecl ::= Variable ‘;’
	//Variable ::= Type Ident
	//Type ::= StdType | ArrayType
	//StdType ::= ‘i’ | ‘d’ | ‘c’
	//ArrayType ::= StdType ‘[’ ‘]’
	public void VariableDecl() {
		
		// Next Token pq nao precisamos diferenciar um identificador da variavel para outra
		// Logo podemos continuar com as verificacoes e metodos
		// No futuro temos que substituir isso por um switch para cada tipo de identificador, removendo assim o nextToken
		nextToken();
		
		// Verificacao se eh uma Array
		// No futuro temos que substituir isso por um metodo para aplicar a array
		if(token == '[') {
			nextToken();
			validarToken(']');
		}
		
		//////////////////////////////////////
		// Verifica o nome da Variavel
		// Obrigatoriamente tem que ter uma letra, seguido por uma repeticao de letras ou numeros
		// Ex: c, cc, c1c, cccc1231ccc, etc
		
		// No futuro criar uma string para salvar o nome da variavel
		
		// Indent
		Ident();
		
		///////////////////////////////////////
		
		// Salvar a variavel no futuro
		
		validarToken(';');
		
	}
	
	/*----------------------------------------*/
	
	//Stmt ::= Expr ‘;’ | ifStmt | WhileStmt | BreakStmt | PrintStmt
	public void Stmt() {
		
		switch(token) {
			case 'f':
				nextToken();
				IfStmt();
				break;
			case 'w':
				nextToken();
				WhileStmt();
				break;
			case 'b':
				nextToken();
				BreakStmt();
				break;
			case 'p':
				nextToken();
				PrintStmt();
				break;
			default:
				System.out.println("Inserir Expr Aqui");
		}
		
	}
	
	//IfStmt ::= ‘f’ ‘(’ Expr ‘)’ ‘{’ { Stmt } ‘}’ [ ‘e’ ‘{’ { Stmt } ‘}’ ]
	public void IfStmt() {
		
		validarToken('(');
		Expr();
		validarToken(')');
		
		// Primeiro bloco do If
		validarToken('{');
		while(token != '}') {
			Stmt();
		}
		validarToken('}');
		
		// Bloco do Else
		if(token == 'e') {
			nextToken();
			validarToken('{');
			while(token != '}') {
				Stmt();
			}
			validarToken('}');
		}
		
	}
	
	//WhileStmt ::= ‘w’ ‘(’ Expr ‘)’ ‘{’ { Stmt } ‘}’
	public void WhileStmt() {
		
		validarToken('(');
		Expr();
		validarToken(')');
		
		validarToken('{');
		while(token != '}') {
			Stmt();
		}
		validarToken('}');
		
	}
	
	//BreakStmt ::= ‘b’ ‘;’
	public void BreakStmt() {
		validarToken(';');
	}
	
	//PrintStmt ::= ‘p’ ‘(’ Expr { ‘,’ Expr } ‘)’
	public void PrintStmt() {
		
		validarToken('(');
		Expr();
		while(token != ')') {
			validarToken(',');
			Expr();
		}
		validarToken(')');
		
	}
	
	//Expr ::= SimExpr [ RelOp Expr]
	public void Expr() {
		SimExpr();
		
		// Verifica se tem RelOp
		if(token == '=' || token == '#' || token == '<' || token == '>' ) {
			nextToken();
			Expr();
		}
	}
	
	//SimExpr ::= [Unary] Term { AddOp Term }
	public void SimExpr() {
		
		/* NOTA: Remover NextToken quando implementar a arvore */
		
		// Verifica se tem Unary
		if(token == '+' || token == '-' || token == '!') {
			nextToken(); // Unary
		}
		
		// Chamar termo
		Term();
		
		// Verifica se tem AddOp
		while(token == '+' || token == '-') {
			nextToken(); // AddOp
			Term();
		}
	}
	
	//Term ::= Factor { MulOp Factor }
	public void Term() {
		Factor();
		
		// Verifica se tem MulOp
		while(token == '*' || token == '/' || token == '%') {
			nextToken(); // MulOp
			Factor();
		}
		
	}
	
	//Factor ::= LValue ‘=’ Expr | LValue | ‘(’ Expr ‘)’ | ‘r’ ‘(’ ‘)’ | ‘s’ ‘(’ ‘)’ | ‘t’ ‘(’ ‘)’
	public void Factor() {
		
		switch(token) {
			case '(':
				nextToken();
				Expr();
				validarToken(')');
				break;
			case 'r':
				nextToken();
				validarToken('(');
				validarToken(')');
				break;
			case 's':
				nextToken();
				validarToken('(');
				validarToken(')');
				break;
			case 't':
				nextToken();
				validarToken('(');
				validarToken(')');
				break;
			default:
				LValue();
				if(token == '=') {
					nextToken();
					Expr();
				}
		}
		
	}
	
	//LValue ::= Ident | Ident ‘[’ Expr ‘]’
	public void LValue() {
		Ident();
		if(token == '[') {
			nextToken();
			Expr();
			validarToken(']');
		}
	}
	
	//Ident ::= Letter { Letter | Digit }
	public void Ident() {
		if(Character.isLetter(token)) { // Verificou se tem uma letra
			nextToken();
			while(Character.isLetter(token) || Character.isDigit(token)) {	// Verifica se é uma letra ou numero
				nextToken();
			}
		} else {
			error("Letra esperada!");
		}
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
