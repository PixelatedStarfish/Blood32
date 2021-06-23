import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 *This is a program that loads help documentation into the interpreter and selects sections of the doc to print based on user input.
 *This class does a linear search for the input, and if it is found in the table of contents that section is printed.
 *Sections are separated by an asterisk (with exceptions as needed).
 */

public class Help {
	
private static String[] getItems(int c) throws FileNotFoundException {
	String[] items = new String[c];
	
	String output = "";
    File f = new File("Help.txt");
    Scanner myReader = new Scanner(f);
    
    int i = 0;
    while (myReader.hasNextLine()) {
      String foo  = myReader.nextLine();
      output += foo + "\n";
      
      if (foo.contains("*")) {
    	  items[i] = output;
    	  i++;
    	  output = "";
    	  
      }}
    
    myReader.close();
    return items;
   
}
//Gets the number of asterisks in the help doc
private static int getCount() throws FileNotFoundException {

	
	File f = new File("Help.txt");
    Scanner myReader = new Scanner(f);
    
    int i = 0;
    while (myReader.hasNextLine()) {
      String foo  = myReader.nextLine();
      
      if (foo.contains("*")) {
    	  i++;
    	  
      }}
    
    myReader.close();
    return i;
   
}


private static String[] getTitles(int c) throws FileNotFoundException {
    File f = new File("Help.txt");
    
    @SuppressWarnings("resource")
	Scanner myReader = new Scanner(f);
	
    String[] titles = new String[c];
	  int i = 0;
	  while (myReader.hasNextLine()) {
	      String foo  = myReader.nextLine();
	      
	      if (foo.contains("-") && (!foo.contains("dec") && !foo.contains("Con")) && !foo.contains("(l)")) {
	    	  foo = foo.replace("-", "");
	    	  titles[i] = foo.trim();
	    	  i++;

	    	  
	      }}
	return titles;
}

//:text: Converts text into binary with the ASCII table.
public static void textGen(Scanner Conway) {
	while (true) {
		 System.out.println("Type something to convert it to binary\nType nothing to close this program.\n");
			String s = getInput(Conway);
			
			if (s.trim().equals("")) {
				return;
			}
			String binary = "";
			
			for (int i = 0; i < s.length(); i++) {
			    binary += Integer.toBinaryString((int) s.charAt(i)) + " ";
					   }
			
			System.out.println("\nBinary:");
			System.out.println(binary);
		
			String tape = "";
			
			for (int i = binary.length() -1; i > -1; i--) { 
				
				if (binary.charAt(i) == ' ') {
					tape += 'B';
				}
				
				else {
				
				  tape += binary.charAt(i); }}
			
			System.out.println("\nOn the tape:");
			System.out.println(tape);
		
			Main.exit(0);		
	}}
//:help: Prints documentation.
public static void helpRead(Scanner Conway) throws FileNotFoundException {
	int c = getCount();
	String[] items = getItems(c);;
	String[] titles = getTitles(c);
	
	while (true) {
	 System.out.println("-Language Doc Contents-\n");
	 for (int i = 0; i < titles.length; i++) {
	 	 if (titles[i] != null) {
		    System.out.println(titles[i]); }}
	
	  System.out.println("\n\nType a Title to read that section. Type 'Exit' to exit. ");
	  String s = getInput(Conway);
		
		if (s.contains("Exit")) {
			return;
		}
		boolean isACommand = false;
		
		for (int i = 0; i < titles.length; i++) {
			if (titles[i] != null) {
		     if (titles[i].contains(s)) {
			  isACommand = true;
			}}}
		
		if (!isACommand) {
			System.out.println("\"" + s + "\"" + " is not a command.");
			continue;
		}
		
		for (int i = 0; i < items.length; i++) {
			if (items[i].contains(s)) {
				System.out.print(items[i]);
			}}}}

public static String getInput(Scanner Conway) {
	String s = "";
	s += Conway.nextLine();
	

	return s.trim();

}}
