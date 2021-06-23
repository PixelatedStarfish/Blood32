import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

//Modified from https://www.w3schools.com/java/java_files_read.asp

/*
 *This class reads source into the interpreter for parsing.
 *It also removes comments and blank lines.
 * 
 */

public class Reader {
	
  public Reader() {
	  
  }
  
  
  public int findInFileContent(String target, String content) {
	  return content.indexOf(target);
	  }
	
  public String read(String p) throws FileNotFoundException {
	  String s = literalReader(p);
	  s = commentRemove(s);
	  s = BlankRemove(s);
	  
	  return s;
	  
  }

  private String BlankRemove(String s) throws FileNotFoundException {
	  
	  //Here is something inelegant but reliable
	  

	  String o = "";
	  String l = "";
	  
	  for (int i = 0; i < s.length(); i ++) {
		  if (!(s.charAt(i) == '\n')) {
			  l += s.charAt(i);
		  }
		  
		  if ((s.charAt(i) == '\n')) {
			  
			  if (!l.trim().isEmpty()) {
			   o += l.trim() + "\n"; }
			   l = "";
			  }}
	  
	
      return o;
  }
  
  private String commentRemove(String s) {
	  String o = "";
	  boolean com = false;
	  
	  
	  
	  for (int i = 0; i < s.length(); i++) {
		  if (s.charAt(i) == '<' ) {
			  com = true;
		  }
		  
		  if (s.charAt(i) == '>' ) {
			  com = false;
		  }
		  
		  if (!com) {
			  o += s.charAt(i);
		  }}
	  
	  o = o.replace("<", "");
	  o = o.replace(">", "");
	 
	return o;
  }
	 
  public String literalReader(String p) throws FileNotFoundException  {
   
     
      String output = "";
      File f = new File(p);
      Scanner myReader = new Scanner(f);
      
      while (myReader.hasNextLine()) {
        String foo  = myReader.nextLine();
        output += foo + "\n";
        }
      
      myReader.close();
      return output;
      
  }
  
String getExtension(String f) {
	//split refuses to cooperate
	
	String foo = "";
	
	for (int i = f.length() -1; i > -1; i--) {
		
		foo += f.charAt(i);
		if (f.charAt(i) == '.') {
			break;
			//I used break, get over it.
		}}
	String exten = "";
	
	for (int i = foo.length() -1; i > -1; i--) {
		exten += foo.charAt(i);
	}
	
	if (!exten.contains(".")) {
		exten = ".bld32";
	}
	
	return exten;		
}

public String read(File f) throws FileNotFoundException {

	return read(f.getName());
}}