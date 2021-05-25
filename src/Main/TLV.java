package Main;

import util.ByteToHex;

public class TLV {
	
	public String Tag;
	public String Length;
	public String[] Value;
	
	public int grade;
	public int startPoint;
	public int endPoint;
	
	public String[] motherStr;
	
	public TLV( String[] motherStr, int grade, int startPoint ) {
		Tag = motherStr[startPoint];
		this.grade = grade;
		Length = motherStr[ (startPoint + 1) ];
		startPoint = startPoint + 2;

		
		if( ByteToHex.hexToInt(Length)>=128 ) {
			StringBuilder sb = new StringBuilder();
			sb.append(motherStr[ (startPoint + 1) ] );
			sb.append(motherStr[ (startPoint + 2) ] );
			Length = sb.toString();
			startPoint = startPoint + 1;
			System.out.println("여기 탓나?");
		}
		
		int length = ByteToHex.hexToInt(Length);
		System.out.println( "이 부분에게 설정된 길이 : " + length );
		
		Value = new String[length];
		
		int valIdx = startPoint;
		for(int i = 0 ; i  < length; i++) {
			Value [ i ] = motherStr[ i + valIdx  ];
		}
		
		InputString.grandmom.append(this.getString());
		
		if(Value.length > 3) {
//			System.out.println("길이 3 초과. 배열에서 시작 위치 : " + startPoint + " 종료되는 위치 : " + (getLengthInt() + startPoint ));
//			TLV tmp = new TLV(motherStr, grade+1, startPoint);
//			ValueDecode.decoding(motherStr, grade+1, startPoint, tmp.getLengthInt() + startPoint );
			ValueDecode.decoding(motherStr, grade+1, startPoint, getLengthInt() + startPoint );
		}
		
	}
	
	public int getLengthInt() {
		return ByteToHex.hexToInt( this.Length );
	}

	public String getLengthStr() {
		return this.Length;
	}

	public int getPacketLength() {
		int val = 0;
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
		for(int i=0; i < Value.length; ++i) {
			sb.append(Value[i]);
			sb.append(" ");
		}
		
		return sb.toString();
	}

}
