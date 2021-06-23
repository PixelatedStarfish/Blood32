
	/**
	* Methods for converting between binary and decimal.
	*
	* @author Andrew Vella
	*/
	//NOTE that the return values are placeholders
	//NOTE binary is represented in two's comp
	//NOTE least sig bit is b[0]


	public class Binary {

	  
	  private static int IntDiv(long a, int b) {
	    return ((int) (a /b));
	  }

	private static boolean[] RecursiveConverterDectoBin(long dividend, int i, boolean[] boo) {
	  int q = IntDiv(dividend, 2);
	  //base case
	  if (q == 0) {
		boo[i] = (dividend % 2 != 0); //true 0, false 1;
	    return boo;
	  }
	  else {
	    boo[i] = (dividend % 2 != 0); // 0 or 1
	    return RecursiveConverterDectoBin(q, i + 1, boo);
	  }
	}



	private static void ArgException(boolean b[]) {
	  if(b.length > MAX_LENGTH) {
	      throw new IllegalArgumentException("parameter array is longer than " + MAX_LENGTH + " bits.");
	  }
	}

	private static boolean IsNeg(boolean b[]) {
	  return (b[MAX_LENGTH -1] == false);
	}

	public static boolean[] Inverter(boolean b[]) {
	  //Look for least sig '1'
	 int IndexLeastSigOne = 0;
	  for(int i = 0; i < MAX_LENGTH; i++) {
	    if (b[i] == true) {
	      IndexLeastSigOne = i;
	    break;
	  } }
	  for(int i = IndexLeastSigOne + 1; i < MAX_LENGTH; i++) {
	    b[i] = (! (b[i])); }
	  return b;
	}


	    /** Constant defines the maximum length of binary numbers. */
	    private static final int MAX_LENGTH = 32;

	    /**
	    * Converts a two's complement binary number to signed decimal
	    *
	    * @param b The two's complement binary number
	    * @return The equivalent decimal value
	    * @exception IllegalArgumentException Parameter array length is longer than MAX_LENGTH.
	    */
	    public static long binToSDec(boolean[] b) {
	      ArgException(b);
	      boolean Sign = true; //b is passed by reference! so the sign must be taken before conversion!
	    
	      if (IsNeg(b)) {
	    	  Sign = false; }
	      long result = binToUDec(b);

	      if (!Sign) {
	    	  
	        result = result * -1;
	      }

	        return result;

	    }

	    /**
	    * Converts an unsigned binary number to unsigned decimal
	    *
	    * @param b The unsigned binary number
	    * @return The equivalent decimal value
	    * @exception IllegalArgumentException Parameter array length is longer than MAX_LENGTH.
	    */
	    public static long binToUDec(boolean[] b) { //Positive Result Always
	            ArgException(b);
	            boolean[] c = b;
	            long result = 0;
	            if (IsNeg(b)) {
	              c = Inverter(c);
	            }

	        for (int i =0; i < c.length; i++) {
	          if (c[i] == false) {
	            result += Math.pow(2, i); 
	           // System.out.print(result +  " ");
	            }
	        }

	        return result;
	    }

	    /**
	    * Converts a signed decimal number to two's complement binary
	    *
	    * @param d The decimal value
	    * @param bits The number of bits to use for the binary number.
	    * @return The equivalent two's complement binary representation.
	    * @exception IllegalArgumentException Parameter is outside valid range that can be represented with the given number of bits.
	    */
	    public static boolean[] sDecToBin(long d, int bits) {


	      boolean[] b = uDecToBin(Math.abs(d), bits); //abs of d
	      if (d < 0) {
	        b = Inverter(b);
	      }


	        return b;
	    }

	    /**
	    * Converts an unsigned decimal nubmer to binary
	    *
	    * @param d The decimal value
	    * @param bits The number of bits to use for the binary number.
	    * @return The equivalent binary representation.
	    * @exception IllegalArgumentException Parameter is outside valid range that can be represented with the given number of bits.
	    */
	    public static boolean[] uDecToBin(long d, int bits) {
	      boolean[] word  = new boolean[bits];
	      
	      if (d == 0) { //edge case
	    	  for (int i =0; i < word.length; i++) {
	    		word[i] = true;  
	    	  }
	      }
	      else {
	    	  
	      word = RecursiveConverterDectoBin(d - 1, 0, new boolean[MAX_LENGTH]);  //corrects an off by one
	     
	      //System.out.println(toString(word));
	      //The recursive method is the source of the error, not the Inverter
	      word = Inverter(word); }

	        return word;
	    }

	    /**
	    * Returns a string representation of the binary number. Uses an underscore
	    * to separate each group of 4 bits.
	    *
	    * @param b The binary number
	    * @return The string representation of the binary number.
	    */
	    public static String toString(boolean[] b) {

	      String s = "";

	      for (int i = (b.length - 1); i > -1; i--) {
	        if (b[i] == true) {
	          s += "1";
	        }
	        else {
	          s += "0";
	        }
	        if ((i != 0) && (i % 4 == 0)) {
	        	s += '_';
	        }
	        }
	        return s;
	    }
	    



	    /**
	    * Returns a hexadecimal representation of the unsigned binary number. Uses
	    * an underscore to separate each group of 4 characters.
	    *
	    * @param b The binary number
	    * @return The hexadecimal representation of the binary number.
	    */
	    public static String toHexString(boolean[] b) {
	      char[] Hexes = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	    long dec = binToUDec(b);

	      int StringLength = (int) Math.round(((Math.log(dec) / Math.log(16)))); //log base 16 rounded

	       int i = 1;
	       char[] Digits = new char[StringLength];

	      while (dec > 16 || i < StringLength + 1) {
	        Digits[StringLength - i] = Hexes[(int) (dec % 16) + 1]; //correct of by 1 error
	        dec = IntDiv(dec, 16);
	        i++; }



	      String s = "";
	      for (int j = 0; j < Digits.length; j++) {
	    	  
	    	  if ((j != 0) && (j % 4 == 0)) {
		        	s += '_';
		        }
	    	  
	        s += Digits[j];
	        
	       
	      }
	      return s;
	     }
	   }




