import java.util.ArrayList;

/*
 *This class reads data into the grid and the tape. Instruction pointer G runs here.
 * 
 */

public class Data {
	int[][] Grid;
	ArrayList<Integer> Tape = new ArrayList<Integer>();

	
	public Data() {
		
	}
	
	public String[] fetch(String source) {
		String f = "";
		int i = 0;
			while (source.charAt(i) != '{') {
				f += source.charAt(i);
				i++;
			}
			
			return f.split("\n");
			}
	
	public void decode(String[] fetched) {
		decodeGrid(fetched);
		decodeTape(fetched);
		
	}
	
	public void decodeGrid(String[] fetched) {

		int[] Cell;
		
		//set grid dimensions. Positions of 1 and 0 are swapped
		for (int i = 0; i < fetched.length; i ++) {
			if (fetched[i].contains("[") && fetched[i].contains("]")) {
				fetched[i].replace(" ", "");
				Grid = new int[getInts(fetched[i])[1]][getInts(fetched[i])[0]];
			}
			
			
			//set cells in grid as needed
			if (fetched[i].contains("(") && fetched[i].contains(")")) {
				Cell = getInts(fetched[i]);
				Grid[Cell[1]][Cell[0]] = Cell[2];
			}}}
	
	public void decodeTape(String[] fetched) {
		
		int t = 0;
		for (int i = 0; i < fetched.length; i ++) {
		  if (fetched[i].contains("T:")) {
			  fetched[i].replace(" ", "");
			  t = i;
			  break; //Egads!
			  
		}}
		
		for (int c = 0; c < fetched[t].length(); c++) {
			
			if (fetched[t].charAt(c) == '0') {
				Tape.add(0);
			}
			
			if (fetched[t].charAt(c) == '1') {
				Tape.add(1);
			}
			
			if (fetched[t].charAt(c) == 'B') {
				Tape.add(-1); //Rather than bothering with Null pointer exceptions, -1 is null on tape.
			}}
		
		for (int i = Tape.size(); i < 64; i++) {
			Tape.add(-1);
		}}
	 
	public int[] getInts(String s) { //for a set of ints in source, get the ints
		//s = s.replace(" ", "");
		s = s.replace("[", "");
		s = s.replace("]", "");
		s = s.replace("(", "");
		s = s.replace(")", "");
		s = s.replace("B", "-1");
		
		String[] cheese = s.split(",");
		int[] Ints = new int[cheese.length]; 
		
		for (int i = 0; i < cheese.length; i++) {
			Ints[i] = Integer.parseInt(cheese[i]);
		}
		
		return Ints;
	}
	
	public int readGrid(int a, int b) {
		return Grid[a][b];
		
	}
	
	public void writeGrid(int a, int b, int c) {
		Grid[a][b] = c;
		
	}
	
	public int readTape(int a) {
		
		if (Tape.size() < a + 1) {
			return -1;
		}
		
		return Tape.get(a);
		
	}
	
	public void writeTape(int a, int b) {
		
		while (Tape.size() < a + 1) {
			Tape.add(-1);
		}
		
		Tape.set(a, b);
		
	}
	//removes are the rightmost blanks. Perhaps it would be better named trimTape()
	public void pruneTape() {
		int i = Tape.size() -1;
		while (Tape.size() > 0 && Tape.get(i) == -1) {
			Tape.remove(i);
			i--;
			
		}}
	
	public void resetTape() {
	
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for (int j = 0; j < 64; j++) {
			temp.add(-1);
		}
		Tape = temp;
		
	}
	
}
