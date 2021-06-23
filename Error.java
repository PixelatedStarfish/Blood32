	/*
	 *This class prints errors and error codes.
	 * 
	 */

@SuppressWarnings("serial")
public class Error extends Exception {
	

	
	public Error() {
		
	}
	
	public static void error() {
		System.err.println("\nInterpreter Error");
		Main.exit(1);
		
	}
	
	public static void SyntaxError(int LineReference) {
		System.err.println("\nOp " + LineReference + " SyntaxError: Syntax Error");
		Main.exit(2);
		
	}
	
	public static void RuntimeError(int LineReference) {
		System.err.println("\nOp " + LineReference + "\nRuntimeError: Runtime Error");
		Main.exit(3);
		
	}
	
	
	public static void NotACellError(int LineReference) {
		System.err.println("\nOp " + LineReference + "\nNotACellError: Cell location not defined");
		Main.exit(4);
		
	}
	
	public static void OperationUndefinedError(int LineReference) {
		System.err.println("\nOp " + LineReference + "\nOperationUndefinedError: An operation is source is not defined");
		Main.exit(5);
		
	}
	
	public static void ParamError(int LineReference) {
		System.err.println("\nOp " + LineReference + "\nParamError: Illegal param for operation. Try removing spaces at a parameter. [1,1] instead of [1, 1]. ");
		Main.exit(5);
		
	}


}
