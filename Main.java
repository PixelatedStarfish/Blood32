import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;


/*
 * BLOOD32 Interpreter
 * @author Andrew Vella
 * 
 * This class takes input from the user to determine which program to run.
 * Programs are located in the input directory.
 * It can also run a program to convert text into binary via the ASCII table (":text:")
 * A help documentation program can be run with a keyword (":help:")
 * 
 * 
 */

public class Main {
	static Reader reader = new Reader();
	static Data data = new Data();
	//static Pointer executor = new Pointer();
	static Scanner Conway = new Scanner (System.in);
	

	public static void main(String args[]) throws FileNotFoundException {
		InputDir();
		String s = "";
	try {
	    s = args[0];
	 }

	catch (ArrayIndexOutOfBoundsException e) {	
	
		 System.out.println("\nWelcome to the Interpreter for BLOOD_32. Type ':help:' for documentation. Type :text: to convert text into binary.  Enter a .bld32 file to run it here.\nEnter nothing to close this program.");
		
		 s = getInput(Conway);
		
		
	}
		if (s.contains(":help:")) {
		    try {
					Help.helpRead(Conway);
				
						} 
				catch (FileNotFoundException e) {
					System.out.println("The Help file is missing. Please give it back!");
					
					return;
					

						}
	}
		if (s.trim().equals("")) {
			System.out.println("Interpreter Closed");
			return;
				
				}
		
		if (!reader.getExtension(s.trim()).equals(".bld32")) {
			System.out.println("The file needs to have the .bld32 extension.");
			
			
		}
		
		if (s.contains(":text:")) { 
			Help.textGen(Conway);
			
		}
		
		
		else {
			if (!s.contains(".")) {
				s += ".bld32";
			}
	
		      File f = new File(getCurrentDir() + "/Input/" + s.trim());
		      if (!f.exists()) {
		    	  System.out.println("Cannot Find " + f + "\nPlease include it in the Input directory (folder).");
		    	  System.out.println("The programs in the Input directory are listed here:\n");
		    	  File d = new File(getCurrentDir() + "/Input/");
		    	  
		    	  String[] t = d.list();
		    	  Arrays.sort(t);
		    	  
		    	  for (int i = 0; i < t.length; i++) {
		    		  if (t[i].endsWith(".bld32")) {
		    		     System.out.println(t[i]); 
		    		  }}
		    	  
		    	  System.out.println("The programs in the Test directory are listed here:\n");
                  File dd = new File(getCurrentDir() + "/Input/Test");
		    	  
		    	  t = dd.list();
		    	  Arrays.sort(t);
		    	  
		    	  for (int i = 0; i < t.length; i++) {
		    		  if (t[i].endsWith(".bld32")) {
		    		     System.out.println("Test/" + t[i]); 
		    		  }}
		    	  
		    	  exit(0);
		    	  return;
		    	  }
		      Interpret(f);
		}}	

	private static String getInput(Scanner Conway) {
			String s = Conway.nextLine();
			return s.trim();
		
	}
	private static void InputDir() {
		File f = new File(getCurrentDir() + "/Input");
		if (!f.exists()) {
			System.out.print("Created an Input directory.\n");
		}
		f.mkdir();
		System.out.println("Input Directory at " + getCurrentDir() + "/Input\n");
		
		
	}

	private static String getCurrentDir() {
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		return s;
	}
	
	private static void Interpret(File f) throws FileNotFoundException  {
		
		try {
			
		System.out.println("\nRunning " + "\"" + f + "\"\n");
		String source;
		source = reader.read(f.getAbsolutePath());
		String[] dat = data.fetch(source);
		
		
		data.decode(dat);
		Pointer executor = new Pointer(data, Conway, source);
		executor.run();
		exit(0); 
		
		}
		catch (NullPointerException npe) {
			Error.NotACellError(Pointer.pc);
		}
		
		catch (ArrayIndexOutOfBoundsException atoobe) {
			Error.NotACellError(Pointer.pc);
		}
		
		catch (NumberFormatException nfe) {
			Error.ParamError(Pointer.pc);
		}
		
		 catch (Exception e) {
			Error.error();
			exit(1);
			
		}}
	
	public static void exit(int c) {
		
		if (c > 0) {
			System.err.println("Error Code: " + c);
		}
		
		System.out.println("\nEnd of Program");
		System.out.println("Interpreter Closed");
		System.exit(c);
	}}
