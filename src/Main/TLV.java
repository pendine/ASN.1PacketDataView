package Main;

import util.ByteToHex;

public class TLV {
	
	public String Tag;
	public String Length;
	public String[] Value;
//	public TLV[] innerTLV;
	
	public int grade;
	public int startPoint;
	public int endPoint;
	
	public String[] motherStr;
	
	public TLV( String[] motherStr, int grade, int startPoint ) {
		Tag = motherStr[startPoint];
		this.grade = grade;
		startPoint += 1;
		Length = motherStr[ ( startPoint ) ];
		startPoint += 1;

		int checkLengthResult = checkLength( Length );
		
		int length = 0;
		
		if(checkLengthResult > 0) {
			StringBuilder tmpSb = new StringBuilder();
			StringBuilder lengStrSb = new StringBuilder();
			lengStrSb.append(Length);
			for(int i=0; i<checkLengthResult; i++) 
			{
				tmpSb.append( motherStr[startPoint] );
				lengStrSb.append( motherStr[startPoint] );
				startPoint += 1;
			}
			
			length = ByteToHex.hexToInt( tmpSb.toString() );
		}
		else if (checkLengthResult == 0) {
			length = ByteToHex.hexToInt(Length);
		}
		else if (checkLengthResult == -1 ) {
			length = ByteToHex.hexToInt(Length);
		}
		
		System.out.println( "TLV 확인중 L값 : " + length );
		if( (startPoint + length) > motherStr.length ) {
			System.out.println(" V의 시작위치 : " + startPoint 
					+ " 부터 길이값 : " + length 
					+ "를 합하니 배열의 길이" + motherStr.length 
					+ " 를 초과 (" + (startPoint + length)
					+  ")해서 중지함.");
			return;
		}
		
		Value = new String[length];
		
		int valIdx = startPoint;
		for(int i = 0 ; i  < length; i++) {
			Value [ i ] = motherStr[ i + valIdx  ];
		}
		
//		InputString.grandmom.append(this.getString());
//		
//		if(Value.length > 3) {
////			System.out.println("길이 3 초과. 배열에서 시작 위치 : " + startPoint + " 종료되는 위치 : " + (getLengthInt() + startPoint ));
////			TLV tmp = new TLV(motherStr, grade+1, startPoint);
////			ValueDecode.decoding(motherStr, grade+1, startPoint, tmp.getLengthInt() + startPoint );
//			ValueDecode.decoding(motherStr, grade+1, startPoint, getLengthInt() + startPoint );
//		}
		
	}
	
//	public TLV( String[] motherStr, int grade, int startPoint , int length) {
//		if(length + startPoint >= motherStr.length ) {
//			new TLV(motherStr, grade, startPoint);
//		}
//		else { 
//			new TLV(motherStr, grade, startPoint);
//		}
//	}
	
	public int getLengthInt() {
		return ByteToHex.hexToInt( this.Length );
	}

	public String getLengthStr() {
		return this.Length;
	}

	public int getPacketLength() {
		int val = 0;
		if(getTag() == null ) return 0;
		val += ( getTag().length() / 2 );
		val += ( getLengthStr().length() / 2 );
		val += ( Value.length );
		return val;
	}

	public String getTag() {
		return this.Tag;
	}
	
	public String getString() {
		String returnVal = "";
		StringBuilder sb = new StringBuilder();
		
		sb.append( setGradeStr() );
		sb.append( Tag );
		sb.append( " " );
		sb.append( Length );
		sb.append( "\n" );
		sb.append( setGradeStr() );
		sb.append( "\t" );
		sb.append( getValue() );
		sb.append( "\n" );
		
		returnVal = sb.toString();
		
		return returnVal;
	}
	
	public String setGradeStr() {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i <= this.grade; ++i) {
			sb.append("\t");
		}
		
		return sb.toString();
	}
	
	public String getValue() {
		StringBuilder sb = new StringBuilder();
		if(Value == null) {
			return null;
		}
		
		for(int i=0; i < Value.length; ++i) {
			sb.append(Value[i]);
			sb.append(" ");
		}
		
		return sb.toString();
	}
	
	public int checkLength(String Length) {
		if ( ByteToHex.hexToInt(Length) == 0x80 ) {
			return -1;
		}
		
		if ( ByteToHex.hexToInt(Length) > 0x80 ) {
			return ByteToHex.hexToInt(Length) - 0x80;
		}
		
		return 0;
	}
	
	//길이 값에 맞는지 확인할 용도였는데
	//127이 넘어가는 값이 있다면 문제 발생.
	//먼저 주석처리.
//	public boolean isLength(String Length) {
//		int tmp = ByteToHex.hexToInt(Length);
//		if ( tmp < 0x90) {
//			return ByteToHex.hexToInt(Length) - 0x80;
//		}
//		
//		return 0;
//	}

}
