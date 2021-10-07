package Main;

import util.ByteToHex;

public class TLV {
	
	public byte tag;
	public byte length;
	public int lengthValue;
	public byte[] Value;
	
	public int grade;
	
	public byte[] receivedBytes;
	
	private int index = 0;
	
//	필수 요소값은 Tag, Length 두개.
//	
//	Length가 0이라면 Value는 null;

//	구현방법.
//	전달받은 바이트배열의 길이가 2보다 작다면 그냥 Value에 삽입하거나 에러 등 따로 처리할것.
//	2보다 크다면 
//	첫번째 위치의 바이트를 Tag에 삽입.
//	두번째 위치의 바이트를 Length에 삽입.
//	
//	Length가 0x7f 이하라면 그대로 int형으로 변환한뒤,
//	Tag의 값이 구조체를 표현하는 태그값이 아닌 경우에
//	나머지의 값을 Value에 삽입.
//	
//	Length가 0x80 이상이라면 0x80 으로 빼준 뒤 나온값만큼 추가로 읽고,
//	추가로 읽은 만큼 바이트를 int혹은 long으로 변환,
//	Tag의 값이 구조체를 표현하는 태그값이 아닌 경우에
//	나머지의 값을 Value에 삽입.
//	
//	만약에 태그값이 구조체라면 새로운 구조체를 생성한다.
	
	public TLV( byte[] receivedBytes, int grade ) {
		this.grade = grade;
		
		if(receivedBytes.length < 2) {
			this.Value = receivedBytes;
			return;
		}
		
		setTag( receivedBytes , index );
		setLength( receivedBytes , index );
		
		lengthValue = checkLength(length);
		
	}
	
	//Tag 입력, 내부인덱스 증가.
	private void setTag( byte[] received , int index ) {
		this.tag = received[index];
		increaseIndex();
	}
	
	//Length 입력, 내부인덱스 증가.
	private void setLength( byte[] received , int index ) {
		this.length = received[index];
		increaseIndex();
		
		// length 바이트의 크기가 1보다 클경우
		// longForm에 맞게 변경해줘야함.
		int isLongForm = checkLength(this.length);
		if ( isLongForm > 2 ) 
		{
			byte[] lengthValue = new byte[isLongForm];
			increaseIndex();
			for( int i = 0; i < isLongForm;  i++) {
				lengthValue[i] = received[index];
				increaseIndex();
			}
		}
			
	}
	
	private void increaseIndex() {
		this.index += 1;
	}
	
	@SuppressWarnings("unused")
	private void increaseIndex(int value) {
		this.index += value;
	}
	
	public int getLengthInt() {
		return lengthValue;
	}

	public byte getTag() {
		return this.tag;
	}
	
	public String getString() {
		String returnVal = "";
		StringBuilder sb = new StringBuilder();
		
		sb.append( setGradeStr() );
		sb.append( tag );
		sb.append( " " );
		sb.append( length );
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
	
	public int checkLength(byte length) {
		if ( length <= 0x80 ) {
			return 1;
		}
		
		if ( length > 0x80 ) {
			return length - 0x80;
		}
		
		return 0;
	}
	

}
