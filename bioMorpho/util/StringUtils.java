package bioMorpho.util;

/**
 * @author Brighton Ancelin
 *
 */
public class StringUtils {
	
	/*
	 * WARNING: must contain no other chars than decimal numerals (0-9) and/or periods and commas (both considered decimal points)
	 * WARNING: Only has around 4 digits of complete accuracy beyond the decimal point. Anything smaller than that will not be
	 * 100% accurate
	 */
	public static float stringToFloat(String string) {
		String text;
		int posOrNeg;
		boolean inDecimal = false;
		byte decimalIndex = -1;
		float f = 0;
		
		if(string.isEmpty()) return 0;
		
		if(string.charAt(0) == 45) {
			text = string.substring(1);
			posOrNeg = -1;
		}
		else {
			text = string;
			posOrNeg = 1;
		}
		for(int i = 0; i < text.length(); i++) {
			char curChar = text.charAt(i);
			// If curChar is a numeral
			if(curChar >= 48 && curChar <= 57) {
				int val = curChar - 48;
				if(!inDecimal) {
					f = (f * 10) + val;
				}
				else {
					f = f + (val / (float)Math.pow(10, decimalIndex));
					decimalIndex++;
				}
			}
			// If curChar is a decimal point
			else if(curChar == 46 || curChar == 44) {
				inDecimal = true;
				decimalIndex = 1;
			}
			// If curChar is unknown
			else {
				System.out.println("Unknown character in StringUtils:stringToFloat with String: '" + text + "' at index: " + i);
				return -1;
			}
		}
		f *= posOrNeg;
		return f;
	}
	
	/*
	 * WARNING: Will only create an int until it hits a char that is NOT a numeral (then it will return the constructed int)
	 */
	public static int stringToInt(String string) {
		String text;
		int posOrNeg;
		int integer = 0;
		
		if(string.isEmpty()) return 0;
		
		if(string.charAt(0) == 45) {
			text = string.substring(1);
			posOrNeg = -1;
		}
		else {
			text = string;
			posOrNeg = 1;
		}
		for(int i = 0; i < text.length(); i++) {
			char curChar = text.charAt(i);
			// If curChar is a numeral
			if(curChar >= 48 && curChar <= 57) {
				int val = curChar - 48;
				integer = (integer * 10) + val;
			}
			// If curChar is unknown
			else {
				System.out.println("Unknown character in StringUtils:stringToInt with String: '" + text + "' at index: " + i);
				return -1;
			}
		}
		integer *= posOrNeg;
		return integer;
	}
	
	/*
	 * Will return a string representing the float value with only the necessary amount of characters.
	 * AKA, if the value is 3 it will return "3" instead of "3.0".
	 * Will return in a hundredths resolution.
	 */
	public static String numToStringSpecial(double input) {
		// These first 3 lines of code transform the number into a string that is "XXX.XX" at finest resolution.
		// This creates the hundredth resolution as stated above this method 
		double value = Math.floor((double)((input*100)+0.5));
		value /= 100;
		String text = value+"";
		CharSequence comma = ",";
		CharSequence period = ".";
		
		// Remove extra 0s and periods/commas from the end of the string
		while(true) {
			if(text.charAt(text.length()-1) == '0') {
				if(text.contains(comma) || text.contains(period)) {
					text = text.substring(0, text.length()-1);
				}
				else break;
			}
			else if(text.charAt(text.length()-1) == ',' || text.charAt(text.length()-1) == '.') {
				text = text.substring(0, text.length()-1);
				break;
			}
			else break;
		}
		
		return text;
	}
}
