package Main;

import util.ByteToHex;

public class ASN_VIEW_Main {
	// Encoding : BER 
	public static void main(String[] args) {
		byte[] byteArray = { /* (byte) 0x00,(byte) 0xff, */ (byte) 0x00 , (byte) 0x00,  (byte) 0xff,  (byte) 0x12, (byte) 0xa2 , (byte) 0xff };
//		byte[] byteArray = { (byte) 0x00,  (byte) 0x12, (byte) 0xa2 , (byte) 0xff };
		
		String packetStr = "305E800101815530538000810101820100A300A447A245800"
				+ "C544553545F434C49454E5400810C544553545"
				+ "F534552564552008209746573745F757365728309746573745"
				+ "F70617373A4040602510185012886010A8701018802100082028E80";
		
		byteArray = new byte[packetStr.length() / 2];
		
		String[] tmp = new String[ packetStr.length() / 2 ];
		
		if( (packetStr.length() / 2 ) % 2 != 0 ) {
			System.out.println("받은 헥스코드 문자열이 2로 나누어지지 않음. 현재 문자열 길이 : " + packetStr.length());
		}
		
		StringBuilder sb = new StringBuilder();
		for(int i=0; i < packetStr.length() / 2 ; i++ ) {
			sb.append( packetStr.charAt( i * 2 ) );
			sb.append( packetStr.charAt( (i * 2) + 1 ) );
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
		TLV2 tlv2 = new TLV2( byteArray , 0 , byteArray.length , 0);
		tlv2.doIt();
		
		System.out.println( tlv2.getString() ); 
//		new TLV( byteArray , 0 , 0 );
	}
	
}
