package util;

public class ByteToHex {
	
	public static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	
	public static byte hexToByteArray(String s) {	
	    return (byte) ((Character.digit(s.charAt(0), 16) << 4)
                + Character.digit(s.charAt(1), 16));
	}
	
	private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
	
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for (int j = 0; j < bytes.length; j++) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = HEX_ARRAY[v >>> 4];
	        hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	public static String byteToHex(byte bytes) {
	    int v = bytes & 0xFF;
	    char b = HEX_ARRAY[v>>>4];
	    char f = HEX_ARRAY[v&0x0f];
	    char[] hexChars = {b,f};
	    
	    return new String(hexChars);
	}
	
	public static String stringToHex(String s) {
	    String result = "";

	    for (int i = 0; i < s.length(); i++) {
	      result += String.format("%02X ", (int) s.charAt(i));
	    }
	    return result;
	}
	
	public static String stringToHex(char c) {
		String s = Character.toString(c);
	    String result = "";

	    for (int i = 0; i < s.length(); i++) {
	      result += String.format("%02X ", (int) s.charAt(i));
	    }
	    return result;
	}
	
	public static String byteToString(byte a) {
		char c = (char)a;
		return String.valueOf(c);
	}
	
	public static String bytesToString(byte[] a) {
		return new String( a );
	}
	

	public static int hexToInt(String hexbyte) {
		if( hexbyte == null) {return 0;}
		int returnVal = Integer.parseInt(hexbyte , 16);
		
		return returnVal;
	}
	
	public static int byteToUnsignedInt(byte bit) {
		int returnVal = (int) (0xff & bit);
		return returnVal;
	}
	
	public static long byteToUnsignedLong(byte bit) {
		long returnVal = (long) (0xff & bit);
		return returnVal;
	}
	
	public static byte[] intToByte(int integer) {
		byte[] byteArray = new byte[4];
		
		byteArray[0] = (byte)(integer >> 24 & 0xff);	// 3 byte move
		byteArray[1] = (byte)(integer >> 16 & 0xff);	// 2 byte move
		byteArray[2] = (byte)(integer >> 8 & 0xff);		// 1 byte move
		byteArray[3] = (byte)(integer & 0xff); 			// byte not move
		
		return byteArray;
	}
	
	public static String intToHex(int integer) {

		return Integer.toHexString( integer );
	}
	
	public static int bytesToUnsignedInt(byte[] bytes) {
		int returnVal = 0;
		
		if(bytes.length > 4 ) {
			System.out.println( "Byte Array Length is over than 4 | Check Bytes Array | Byte Array Length : " + bytes.length );
			return 0;
		}
		else if( bytes.length < 1 ) {
			System.out.println( "Byte Array Length is low than 1 | Check Bytes Array | Byte Array Length : " + bytes.length );
			return 0;
		}
		
		for( int i = bytes.length - 1 ; i >= 0 ; i-- ) 
		{
			int tmp = ( byteToUnsignedInt( bytes[i] )) << ( 8 * ( bytes.length - i )  - 8 ) ;
			returnVal = returnVal + ( tmp ) ;
			System.out.println( " bytes 배열 : " + i + "번째 요소 치환됨 => " + tmp + " 누적 결과 : " + returnVal );
			System.out.println( " 8 * ( bytes.length - i ) : " + 8 * ( bytes.length - i ) );
			System.out.println( " ( 8 * ( bytes.length - i )  - 8 ) " + ( 8 * ( bytes.length - i )  - 8 ) ); 
		}
		
        return returnVal;
	}
	
	public static long bytesToUnsignedLong(byte[] bytes) {
		long returnVal = 0;
		
		if(bytes.length > 8 ) {
			System.out.println( "Byte Array Length is over than 8 | Check Bytes Array | Byte Array Length : " + bytes.length );
			return 0;
		}
		else if( bytes.length < 1 ) {
			System.out.println( "Byte Array Length is low than 1 | Check Bytes Array | Byte Array Length : " + bytes.length );
			return 0;
		}
		
		for( int i = bytes.length - 1 ; i >= 0 ; i-- ) 
		{
			long tmp = ( byteToUnsignedLong( bytes[i] ) << ( 8 * ( bytes.length - i )  - 8 ) ) ;
			returnVal = returnVal + tmp;  
//			System.out.println( " bytes 배열 : " + i + "번째 요소 치환전 : "+ byteToUnsignedLong( bytes[i] ) + " 치환 후 :  " + tmp + " 누적 결과 : " + returnVal );
//			System.out.println( " 8 * ( bytes.length - i ) : " + 8 * ( bytes.length - i ) );
//			System.out.println( " ( 8 * ( bytes.length - i )  - 8 ) " + ( 8 * ( bytes.length - i )  - 8 ) );
		}
		
        return returnVal;
	}
	
}
