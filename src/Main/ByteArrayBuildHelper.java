package Main;

import util.ByteToHex;

public class ByteArrayBuildHelper {

	public ByteArrayBuildHelper(){}
	
	
	public byte[] stringToByteArray(String str) {
		byte[] byteArray;
		
		byteArray = new byte[str.length() / 2];

		String[] tmp = new String[ str.length() / 2 ];
		
		if( str.length() % 2 != 0 ) {
			System.out.println("중지 | 받은 헥스코드 문자열이 2로 나누어지지 않음. 현재 문자열 길이 : " + str.length());
			return null;
		}
		
		StringBuilder sb = new StringBuilder();
		for(int i=0; i < str.length() / 2 ; i++ ) {
			sb.append( str.charAt( i * 2 ) );
			sb.append( str.charAt( (i * 2) + 1 ) );
			tmp[i] = sb.toString();
			sb.setLength(0);
		}
		
		for(int i=0; i < tmp.length ; i ++) {
			byteArray[i] = ByteToHex.hexToByteArray(tmp[i]);
		}
		
		System.out.println( "byteArray length : " + byteArray.length );
		
		for( int i =0 ; i < byteArray.length; i ++ ) {
			System.out.print( ByteToHex.byteToHex(byteArray[i]) + " ");
		}
		System.out.println("");
		System.out.println("----------------------------------------------------");

		return byteArray;
	}
	
}
