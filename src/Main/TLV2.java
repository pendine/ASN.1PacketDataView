package Main;

import java.util.ArrayList;

import asn_enum.AsnEnum;
import util.ByteToHex;

public class TLV2 {
	byte tag;
	byte length;
	byte[] lengthArray;
	
	public boolean TLVmake = true;
	
	public int lengthVal;
	
	private int index = 0;
	
	ArrayList<TLV2> innerTLVList = new ArrayList<>();
	
	byte[] receive;
	byte[] value;
	
	byte valueFirst;
	
	int grade;
	int limit;
	
	public TLV2( byte[] received , int receivedIndex , int limit , int grade ) {
//		System.out.println(" receive index : " + receivedIndex);
//		System.out.println(" receive array length : " + received.length );
		receive = received;
		index = receivedIndex;
		this.grade = grade;
		this.limit = limit;
//		setTag();
//		setLength();
//		System.out.println("check length : " + lengthVal );
//		setValue();
//		
//		doIt();
	}
	
	public void doIt() {

		setTag();
		setLength();
//		System.out.println("length Byte : " + );;
//		System.out.println("check length : " + lengthVal );
		if(lengthVal + 1 + ((lengthArray==null)? 0 : lengthArray.length) > ((receive == null)? -1 : receive.length) ) {
			System.out.println("확인한 길이가 받은 길이보다 긺. 취소 | 확인된 길이 : " + lengthVal 
					+ " 받은 배열 길이 : " + receive.length );
			TLVmake = false;
			return;
		}
		setValue();
		
//		System.out.println("index : " + index );
		
		System.out.println(" | grade : " + grade //+ " | \n" 
						 + " | TAG : " + ByteToHex.byteToHex(tag) //+ " | \n" 
						 + " | Length = Hex : " + ByteToHex.byteToHex(length) + " real Length : " + lengthVal + " | \n" 
						 + " | index : " + index + " | \n"
						 + " | Value : " + ( ( value  == null ) ? "null" : ByteToHex.bytesToHex(value) ) + " | \n"
						 + " | total Length : " + getTotalLength()
						 
				);
		int innerTLVStartPoint = 0;

		if( innerTLVStartPoint < (( value == null ) ? -1 : value.length) ) {
			System.out.println(" inner TLV 생성 가능 ");
		}else {
			System.out.println(" inner TLV 생성 불가능 ");
		}
		
		
		if(
			(
			AsnEnum.forValue(tag) == AsnEnum.SEQUENCE
			|| AsnEnum.forValue(tag) == AsnEnum.SET
			|| isAxTag(tag)
			|| AsnEnum.forValue(valueFirst) == AsnEnum.SEQUENCE
			|| AsnEnum.forValue(valueFirst) == AsnEnum.SET
			|| isAxTag(valueFirst)
			)
//			&& grade < 1
			)
		{
			while( innerTLVStartPoint < value.length ) {
				System.out.println("inner TLV 한테 전달한 Value 길이는 : " + value.length );
				if(value.length < 3) {
					System.out.println("inner TLV 한테 전달한 Value 길이는 " + value.length + " 이므로 중단 ");
					break;
				}
				TLV2 inner = new TLV2( value , innerTLVStartPoint , value.length, grade + 1);
				innerTLVList.add(inner);
				
				inner.doIt();
				if(!inner.TLVmake) {
					break;
				}
//				int innerLength = inner.getTotalLength();
////				System.out.println("inner Length : " + innerLength );
//				index += innerLength;
				innerTLVStartPoint += inner.getTotalLength();
				System.out.println("inner totalLength : " + inner.getTotalLength());
				System.out.println("next inner start index : " + innerTLVStartPoint );
				System.out.println("value : " + value.length );
				
				if( innerTLVStartPoint >= receive.length ) {
					System.out.println("끝");
					break;
				}
//				else {
////					inner.doIt();
//				}
			}
			
		}
		
	}
	
	private boolean isAxTag(byte tag) {
//		byte a = (byte) (tag );
		int a = ByteToHex.byteToUnsignedInt(tag);
//		System.out.println(" A : " + a);
		if( a >= 160 )
		{
			return true;
		}
		if( a == 30 || a ==31)
		{
			return true;
		}
		
		return false;
	}
	
	private void setTag(){
		tag = receive[index];
		System.out.println(" -------------------------- " );
		System.out.println(" index : " + index + " tag hex : " + ByteToHex.byteToHex(tag));
		System.out.println(" -------------------------- " );
		
		index ++;
	}
	
	private void setLength(){
		length = receive[index];
		System.out.println(" -------------------------- " );
		System.out.println(" index : " + index 
				+ " length hex : " + ByteToHex.byteToHex(length) 
				+ " int value : " + ByteToHex.byteToUnsignedInt(length));
		System.out.println(" -------------------------- " );
		
		index ++;
		
		boolean isLongForm = checkForm(length);

		int LongFormLength = 0;
		if(isLongForm) {
			LongFormLength = checkLength(length);
		}
		if ( isLongForm ) 
		{
			lengthArray = new byte[ LongFormLength ];
			
			for( int i = 0; i < LongFormLength;  i++) {
				lengthArray[i] = receive[index];
				index++;
			}
			System.out.println( " LongForm | length check : " + ByteToHex.bytesToUnsignedInt(lengthArray) 
							+ " | HEX : " + ByteToHex.bytesToHex(lengthArray) 
							+ " length Array : " + lengthArray.length );
			lengthVal = ByteToHex.bytesToUnsignedInt(lengthArray);
		}
		else 
		{
			System.out.println(" ShortForm | length check : " + ByteToHex.byteToUnsignedInt(length)
							+ " | HEX : " + ByteToHex.byteToHex(length)  );
//			this.lengthVal = length; 
			lengthVal = ByteToHex.byteToUnsignedInt(length);
		}
		System.out.println(" real Length Set : " + lengthVal);

//		내부 TLV 에 전달할 내용에 3030인것들이 있음. 이럴때 구조체와 같은 값으로 인해 오류가 발생해서
//		길이를 확인했을때 받은것배열보다, 확인된 길이값이 길때 취소해야함.  
//		헤더 + 길이 
//		if(lengthVal + 1 + ((lengthArray==null)? 0 : lengthArray.length)> receive.length ) {
//			System.out.println("확인한 길이가 받은 길이보다 긺. 취소 | 확인된 길이 : " + lengthVal 
//					+ " 받은 배열 길이 : " + receive.length );
//			return;
//		}

	}
	
	public boolean checkForm(byte length) {
		if ( ByteToHex.byteToUnsignedInt(length) <= ByteToHex.byteToUnsignedInt((byte)0x80) ) {
//			System.out.println("short form");
			return false;
		}
		if ( ByteToHex.byteToUnsignedInt(length) > ByteToHex.byteToUnsignedInt((byte)0x80) ) {
//			System.out.println("long form");
			return true;
		}
		return false;
	}

	public int checkLength(byte length) {
		if ( ByteToHex.byteToUnsignedInt(length) <= ByteToHex.byteToUnsignedInt((byte)0x80) ) {
//			System.out.println("short form");
			return 1;
		}
		if ( ByteToHex.byteToUnsignedInt(length) > ByteToHex.byteToUnsignedInt((byte)0x80) ) {
//			System.out.println("long form");
			return ByteToHex.byteToUnsignedInt(length) - ByteToHex.byteToUnsignedInt((byte)0x80);
		}
		return -1;
	}
	
	private void setValue(){
		if(lengthVal > 0) 
		{
			value = new byte[lengthVal];
			System.arraycopy(receive, index, value, 0, lengthVal);
			valueFirst = value[0];
			System.out.println(" -------------------------- " );
			System.out.println(" index : " + index + " Value hex : " + ByteToHex.bytesToHex(value));
			System.out.println(" -------------------------- " );
			index += lengthVal;
		}
		else
		{
			value = null;
			TLVmake = false;
		}
	}
	
	public int getIndex() {
		return index;
	}
	
	public int getTotalLength() {
//		인덱스 값 + 길이 바이트 + 태그 길이
//		길이 반환 = ( 기본 바이트 태그, 길이 -> T,L = 1 기본 고정)
//		가변 가능성 = ( 롱폼에 따른 L의 길이와, V의 길이 )
		int returnVal = 2 
				+ ( (lengthArray == null )? 0 : lengthArray.length ) 
				+ ((value==null)? 0 : value.length);
		System.out.println(" tag byte : " + 1 + " length byte : " + 1 
				+ " length Array size : " + ( (lengthArray == null )? 0 : lengthArray.length ) 
				+ " value Array size : " + ((value==null)? 0 : value.length)
				+ " totalLength : " + returnVal);
		return returnVal;
	}
	
	public String getString() {
		StringBuilder sb = new StringBuilder();
		sb.append( "\n");
		for(int i =0 ; i < grade; i ++) {
			sb.append("\t");
			}
		sb.append(ByteToHex.byteToHex( tag ) + " " + ByteToHex.byteToHex( length) );
		sb.append("\n");
		for(int i =0 ; i < grade + 1 ; i ++) {
		sb.append("\t");
		}
		sb.append( ((value == null ) ? "" : ByteToHex.bytesToHex(value)) );
		
		if(innerTLVList != null)
		{
			for(int i = 0; i < innerTLVList.size(); i ++) {
				sb.append( innerTLVList.get(i).getString() );
			}
		}
		
		return sb.toString();
	}
	
	public boolean isATag(byte tag) {
		
		if( tag > 10) {
			return true;
		}
		
		return false;
		
	}
	
	
}
