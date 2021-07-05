import java.util.ArrayList;
import java.util.Scanner;

/*
 *This runs pointer operations. Pointer P runs the fetch execute cycle here.
 * 
 */

public class Pointer {
	static int pc = 0; //program counter
	
	//Note that if tapeLoc is greater than -1 it takes precedence over CellLoc.
 	static int[] CellLoc = new int[2];
 	static int tapeLoc = -1;
 	static int lastGoto = pc;
 	
 	static Data data;
 	Scanner scan;
 	String source;
 	ArrayList<int[]> Labels = new ArrayList<int[]>(); //Note that int 0 is the id, and 1 is the location in the instruction sequence
		
	public Pointer(Data d, Scanner s, String srce){
		data = d;
		scan = s;
		source = srce;
		
	}
	
	public void run()   {
		String[] fetched = fetch(source);
		int[][] instructs = decode(fetched);
		executeLoop(instructs);
		
	}

	private String[] fetch(String s) {
		boolean b = false;
		String r = "";
		
		for (int i = 0; i < s.length(); i++) {
			
			if(b) {
				r += s.charAt(i);
			}
			
			if (s.charAt(i) == '{') {
				b = true;
			}}
		return r.split("\n");
		
	}
	private int[][] decode(String[] s) {
		int[][] ops = new int[s.length][3]; 
		
		for (int i = 0; i < s.length; i ++ ) {
			String cheese = removeTheNotImportant(s[i]);

			ops[i] = convertToVals(cheese);
			
		}
		
		for (int i = 0; i < ops.length; i++) {
			//Adds all labels
			int[] l = new int[2];
			if (ops[i][0] == 15) { //a label
				l[0] = ops[i][1];
				l[1] = i;
				Labels.add(l);
			}}
		
		return ops;
	}
	

	//part of decoding, removes text that is not needed for execution.
	private String removeTheNotImportant(String s) {
		//These symbols are hard to justify... do the improve readability or are they extra keystrokes?
		s = s.replace(" ", "");
		s = s.replace(",", " ");
		
		s = s.replace("G()", "G(-180339)");
		s = s.replace("$()", "G(-180339)");

		
		s = s.replace("T(+)", "T(-1)");
		s = s.replace("T(-)", "T(-2)");
		s = s.replace("%(+)", "T(-1)");
		s = s.replace("%(-)", "T(-2)");
		
		s = s.replace("J(+,+)", "J(-1,-1)");
		s = s.replace("J(-,-)", "J(-2,-2)");
		s = s.replace("J(+,-)", "J(-1,-2)");
		s = s.replace("J(-,+)", "J(-2,-1)");
		
		s = s.replace("^(+,+)", "J(-1,-1)");
		s = s.replace("^(-,-)", "J(-2,-2)");
		s = s.replace("^(+,-)", "J(-1,-2)");
		s = s.replace("^(-,+)", "J(-2,-1)");
	
		
		s = s.replace("(", " ");
		s = s.replace(")", " ");
		
	
	
		
		return s;	
	}
	//Part of decoding. Converts an operation into integers for execution.
	private int[] convertToVals(String s) {
		int[] vals = new int[3];
		
		vals[0] = -1;
		vals[1] = -1;
		vals[2] = -1;
		
		String[] eles = s.split(" ");
		
		
		String keys = "DEACNBHORGTWJYZLXS_";
		String alts = "FQI@MVKP#$%&^U*?:~!";
		
		for (int i = 0; i < keys.length(); i++) {
			char keyAt = keys.charAt(i);
			char altAt = alts.charAt(i);
			if(eles[0].equals(keyAt + "") || eles[0].equals(altAt + "")) {
				vals[0] = i;
			}}
		
		for (int i = 1; i < eles.length; i++) {
			
		  try {
			  vals[i] = Integer.parseInt(eles[i]); 
		  } 
		  catch(NumberFormatException nfe) {
			  vals[i] = -1;
		  }}
	
		return vals;
		
		
	}
	
	private void executeLoop(int[][] code)   {
    	//init pointer
    	CellLoc[0] = 0;
    	CellLoc[1] = 0;
    	pc = 0;
 
    	while (pc < code.length) {
    		switchBoard(code[pc]); 
    		pc++;
    		}}
   
	//Executes an operation as indicated by the integer c[0]. c[1] and c[2] are parameters for the operation. 	
	private void switchBoard(int[] c)   {
		
		switch(c[0]) {
		
		case 0: {
			ranDom();
			return;
		}
		
        case 1: {
			Exit(); 
			return;
		}
        
        case 2: {
			Ask(scan);
			return;
		}
        
        case 3: {
			outputChar();
			return;
		}
        
        case 4: {
			outputNumber(true);
			return;
		}
        
        case 5: {
			outputBinary(true);
			return;
		}
        
        case 6: {
     		outputHex();
    		return;
     		}
        
        case 7: {
 			Output();
			return;
 		}
     		
        case 8: {
 			Read(c[1]);
 			return;
 		}
        
        case 9: {
        	if (c[1] != -180339) {
 			    Goto(c[1]);
 			return;
 		}
        	Goto();
        	return;
        }
        
        case 10: {
 			Tape(c[1]);
 			return;
 		}
        
        case 11: {
 			Write(c[1]);
 			return;
 		}
        
        case 12: {
 			Jump(c[1], c[2]);
 			return;
 		}
        
        case 13: {
        	YBranch(c[1]);
        	return;
        	
        	
 		}
        case 14: {
        	ZBranch(c[1]);
 			return;
        	
 		}
        
        case 15: {
 			//Label(); //This does NOTHING!!!!! *lizard leaps across screen* its just a marker for goto.
 		}
        case 16: {
     			resetTape();
     			return;
     		}
        case 17: {
 			showGrid();
 			return;
 		}
        
        case 18: {
       
     	    Delay(c[1]);
 			return;
 		}
     	
   	 default: {
   		 if (c[0] != -1 ) {
   		Error.OperationUndefinedError(Pointer.pc); 
   			 }
     }}}
	
private void Delay(int millis) {
	//inelegant, but serviceable
	try {
		Thread.sleep(millis);
	} catch (InterruptedException e) {
	
		Main.exit(0);
	}
	catch (IllegalArgumentException ae) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

			Main.exit(0);
		}
	}
}
 

private void showGrid() {
	int[][] g = data.Grid;
	System.out.println(g.length + " by " + g[0].length + " Grid:\n");
	
	for (int i = g.length - 1; i > -1; i--) {
		for (int j = 0; j < g[i].length; j++) {
			if (g[i][j] == 0) {
				System.out.print("0");
			}
			
			if (g[i][j] == 1) {
				System.out.print("1");
			}
			
			if (g[i][j] == -1) {
				System.out.print("B");
			}
			System.out.print(" ");
		}
		System.out.print("\n");
		
	}
	System.out.print("\n");

}
private void Read(int i) {
	int r  = data.readGrid(CellLoc[0], CellLoc[1]);
	data.Tape.set(i, r);
	
	 }

private void Write(int i) {
	if (tapeLoc == -1) {
		data.writeGrid(CellLoc[0], CellLoc[1], i);
	}
	else {
		data.writeTape(tapeLoc, i);
	}}
	

	
private void YBranch(int l) {
	lastGoto = pc + 1;
	int r  = data.readGrid(CellLoc[0], CellLoc[1]);
	
	if (r != 0) {
		Goto(l);
	}
	
}



private void ZBranch(int l) {
	lastGoto = pc + 1; 
	int r  = data.readGrid(CellLoc[0], CellLoc[1]);
	
	if (r == 0) {
		Goto(l);
	}
	
}





 private void Jump(int x, int y) {
	 
if ( x > -1 && y > -1) {
	CellLoc[0] = x;
	CellLoc[1] = y;
	tapeLoc = -1;
}
else {

if (tapeLoc != -1) {
	Error.NotACellError(pc);
}

try {
if (x == -1) {
	CellLoc[0]++;
}
if (y == -1) {
	CellLoc[1]++;
}
if (x == -2) {
	CellLoc[0]--;
	
}
if (y == -2) {
	CellLoc[1]--;
	
}} catch (ArrayIndexOutOfBoundsException e) {
	Error.NotACellError(pc);
}


}
 }
 
 private void Tape(int i) {
	 
	 if (i > -1) {
		 
	 tapeLoc = i; 
	 }
	 
	 if (i == -1) {
		 tapeLoc++;
	 }
	 if (i== -2) {
		 
		 if (tapeLoc > 0) {
			 tapeLoc--;
		 }
		 else {
			 Error.NotACellError(pc);
		 }
	


	 }
	 
 }
 private void Goto(int id) {
	 lastGoto = pc;
	 
	 for (int i = 0; i < Labels.size(); i++) {
		 if (Labels.get(i)[0] == id) {
			 pc = Labels.get(i)[1];
			 return;
		 }}
	 pc = 0;
 }
 
 private void Goto() {
	// System.out.println(pc);
	 pc = lastGoto;
	 //System.out.println(lastGoto);
 }
 private void resetTape() {
	 data.resetTape();
 }
 private double getRandomDouble(int min, int max) {
		return (Math.random()*(max-min+1)+min);
		
	}
 
	private int getRandomInt(int min, int max) {
		return (int) getRandomDouble(min, max);
		
	}
 
 private void ranDom() {
	int i = getRandomInt(0, 1);
	
	if (tapeLoc == -1) {
		data.writeGrid(CellLoc[0], CellLoc[1], i);
	}
	else {
		data.writeTape(tapeLoc, i);
	}}
 
 private void Exit()   {
	 Main.exit(0);
 }
 
 private static void Ask(Scanner Conway) {
		System.out.println("Type whitespace or nothing for a 0. Type anything else for 1");
		String s = Conway.nextLine();
		int i = 0;
		
		
		if (!s.trim().isEmpty()) {
			i = 1;
		}
		
		if (tapeLoc == -1) {
			data.writeGrid(CellLoc[0], CellLoc[1], i);
		}
		else {
			data.writeTape(tapeLoc, i);
		}}
 
 private static String Output() {
	 data.pruneTape();
	 String binny = "";

	 for (int i = 0; i < data.Tape.size(); i++) {
		 if ( data.Tape.get(i) == 1) {
			 binny += '1';
		 }
		 if ( data.Tape.get(i) == 0) {
			 binny += '0';
		 }
		 if ( data.Tape.get(i) == -1) {
			 binny += 'B';
		 }}
	 
	 System.out.println("T: " + binny);
	 return binny;
 }
 
 private static String outputBinary(boolean on) {
	
	 data.pruneTape();
	 String binny = "";

	 for (int i = data.Tape.size() - 1; i > -1; i--) {
		 if ( data.Tape.get(i) == 1) {
			 binny += '1';
		 }
		 if ( data.Tape.get(i) == 0) {
			 binny += '0';
		 }
		 if ( data.Tape.get(i) == -1) {
			 binny += " ";
			
		 }}
	 
	 if (on) {
	     System.out.println(binny);
	     }
	 
	 return binny;
 }
 
 private static String outputNumber(boolean on) {
	 
	 data.pruneTape();
	 String binny = outputBinary(false);
	 
	 String[] s = binny.split(" ");
	 
	 String d = "";
	 
	 for (int i = 0; i < s.length; i++) {
		 d += binToDec(s[i]) + " ";
	 }
	 if (on) {
	   System.out.println(d); 
	}
	return d;
}
 
 private static String outputHex() {
	 data.pruneTape();
	 String binny = outputBinary(false);
     
	 String[] s = binny.split(" ");
	 String h = "";
	 
	 for (int i = 0; i < s.length; i++) {
		 h += Integer.toHexString(Integer.parseInt(binToDec(s[i]))) + " ";
	 }
	 
	 System.out.println(h);   
	 return h;
}
 
 private static String outputChar() {
	 data.pruneTape();
	 String n = outputNumber(false);
	 
     String[] s = n.split(" ");
	 String d = "";
	 
	 for (int i = 0; i < s.length; i++) {
		 d += (char) Integer.parseInt(s[i]) + "";
	 }

	 System.out.println(d);   
	 return "";
}

 //Modified from  https://www.javatpoint.com/java-binary-to-decimal
 public static String binToDec(String b){  
	 String binaryString= b;  
	 int decimal=Integer.parseInt(binaryString,2);  
	 return (decimal + "");  
	 }}

//Compiler Language With No Pronounceable Acronym

//COmpiler Language WIth No Pronounceable Acronym
// INTERCAL => COLWINPA 
