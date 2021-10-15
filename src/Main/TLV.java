package Main;

import asn_enum.AsnEnum;
import util.ByteToHex;

public class TLV {
	
	public byte tag;
	public byte length;
	public int lengthValue;
	public byte[] Value;
	
	public int grade;
	
	public byte[] received;
	
	private int index = 0;
	
//	참고했던 사이트
//	https://luca.ntop.org/Teaching/Appunti/asn1.html
	
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
	
	public TLV( byte[] receivedBytes, int indexValue , int gradeValue ) {
		index = indexValue;
		grade = gradeValue;
		
		System.out.println(" receivedBytes length : " + receivedBytes.length);
		if( receivedBytes == null || receivedBytes.length == 0) 
		{
			System.out.println("내부 생성 불가능");
			return;
		}
		else 
		{
			received = receivedBytes;
		}
	
		if(received.length < 3) {
			if(received != null) {
				Value = received;
				lengthValue = received.length; 
			}
			return;
		}
		
		setTag( received , index );
		
		setLength( received , index );
		
		setValue( received , lengthValue , index);
		
		System.out.println("Tag : " + ByteToHex.byteToHex(this.tag) 
							+ " Length : " + ByteToHex.byteToHex(length) 
							+ " ( " + getLengthInt() + " ) "
							+ " 받은 데이터 내용 : " + ByteToHex.bytesToHex(received)
						);
		
		if( lengthValue < 3) 
		{
			
		}
		
		
		if(AsnEnum.forValue(tag) != AsnEnum.SEQUENCE
			&& AsnEnum.forValue(tag) != AsnEnum.SET 
			&& lengthValue > 0)
		{
			System.out.println(
//					"구조체 확인 : " + AsnEnum.forValue(tag).name() 
					"구조체 태그 : " + ByteToHex.byteToHex(tag) 
					+ " now grade : " + grade 
					+ " now index : " + index 
					+ " 받은 데이터 길이 : " + received.length
					+ " 받은 데이터 내용 : " + ByteToHex.bytesToHex(received)
					);
		}
		else 
		{
			System.out.println("구조체 확인 : " + AsnEnum.forValue(tag).name() 
					+ " now grade : " + grade 
					+ " now index : " + index 
					+ " 받은 데이터 길이 : " + received.length
					+ " 받은 데이터 내용 : " + ByteToHex.bytesToHex(received)
					);

//				TAG 값이 구조체일때 안에 있는 바이트배열로 새로운 객체를 생성해줘야하고
//				새로 생성될때 생성된 객체의 길이가 구조체 범위를 벗어나지 않을때 반복하려고했는데
//				여기서 조건을 대충 생각해서 무한루프 문제가 발생한듯.
//				
//				새로운 객체를 만들고나서 바이트 배열 중 남은것들이 있을때만 반복해야함... 
				
				while ( index < received.length ) {
					TLV inner = new TLV( received , 0 , grade + 1 );
					index = index + inner.getLengthInt() + 2;
					System.out.println(" Inner TLV Grade : " + inner.grade 
							+ " Inner TLV length : " + inner.getLengthInt() 
							+ " This TLV Grade : " + grade
							+ " This TLV now index : " + index );
					if( inner.getLengthInt() >= getLengthInt() )
					{
						System.out.println(" inner Length : " + inner.getLengthInt() 
						+ " this Length : " + getLengthInt() );
					}
				
				}
			
		}
//		getString();
	}
	
	//Tag 입력, 내부인덱스 증가.
	private void setTag( byte[] received , int index ) {
		tag = received[index];
		System.out.println( "set Tag : " + ByteToHex.byteToHex(tag) + " Tag Name : " + AsnEnum.forValue(tag) );
		increaseIndex();
	}
	
	//Length 입력, 내부인덱스 증가.
	private void setLength( byte[] received , int index ) {
		this.length = received[index];
		increaseIndex();

		// length 바이트의 크기가 1보다 클경우
		// longForm에 맞게 변경해줘야함.
		int isLongForm = checkLength(this.length);
		if ( isLongForm > 1 ) 
		{
			byte[] lengthValue = new byte[isLongForm];
			increaseIndex();
			
			for( int i = 0; i < isLongForm;  i++) {
				lengthValue[i] = received[index];
				increaseIndex();
			}
			
			this.lengthValue = ByteToHex.bytesToUnsignedInt(lengthValue);
		}
		else 
		{
			this.lengthValue = ByteToHex.byteToUnsignedInt(length);
		}
		System.out.println("set length : " + ByteToHex.byteToHex(length) + " length Value : " + length);
		
	}
	
	private void setValue( byte[] receivedArray , int length , int index ) {
		byte[] tmp = new byte[getLengthInt()];
		
		System.arraycopy(receivedArray, index , tmp , 0 , getLengthInt() );
//		System.arraycopy(received , 0 , receivedArray, index, index+length);
		
		received = tmp;
//		for(int i=0; i < length ; i ++) 
//		{
//			received[i] = receivedArray[ i + index];
//		}
		
//		increaseIndex( lengthValue );
	}
	
	
	private void increaseIndex() {
		index += 1;
		System.out.println(" Index now : " + index );
	}
	
	private void increaseIndex(int value) {
		index += value;
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
		for(int i=0; i <= this.grade ; ++i) {
			sb.append("\t");
		}
		
		return sb.toString();
	}
	
	public String getValue() {
		StringBuilder sb = new StringBuilder();
		if(Value == null) {
			return null;
		}
		
//		for(int i=0; i < Value.length; ++i) {
//			sb.append(Value[i]);
//			sb.append(" ");
//		}
		
		sb.append( ByteToHex.bytesToHex(received) );
		
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
