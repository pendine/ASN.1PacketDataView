package Main;

import java.util.ArrayList;

import asn_enum.AsnEnum;
import util.ByteToHex;

public class TLV2 {
	byte tag;
	byte length;
	byte[] lengthArray;
	
	public int lengthVal;
	
	private int index = 0;
	
	ArrayList<TLV2> innerTLVList = new ArrayList<>();
	
	byte[] receive;
	byte[] value;
	
	byte valueFirst;
	
	int grade;
	
	public TLV2( byte[] received , int receivedIndex , int grade ) {
//		System.out.println(" receive index : " + receivedIndex);
		receive = received;
		index = receivedIndex;
		this.grade = grade; 
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
		setValue();
		
//		System.out.println("index : " + index );
		
		System.out.println(" grade : " + grade + " TAG : " + ByteToHex.byteToHex(tag) 
				+ " Length : " + ByteToHex.byteToHex(length) + "(" + lengthVal + ")" 
				+ " Value : " + ( ( value  == null ) ? "null" : ByteToHex.bytesToHex(value) ) 
				);
		
		if(AsnEnum.forValue(tag) == AsnEnum.SEQUENCE
			|| AsnEnum.forValue(tag) == AsnEnum.SET
			|| AsnEnum.forValue(tag) == AsnEnum.A4
			|| AsnEnum.forValue(tag) == AsnEnum.A2
			|| AsnEnum.forValue(valueFirst) == AsnEnum.SEQUENCE
			|| AsnEnum.forValue(valueFirst) == AsnEnum.SET
			|| AsnEnum.forValue(valueFirst) == AsnEnum.A4
			|| AsnEnum.forValue(valueFirst) == AsnEnum.A2
			)
		{
			while( index < receive.length ) {
				TLV2 inner = new TLV2( receive , index, grade + 1);
				innerTLVList.add(inner);
				inner.doIt();
				int innerLength = inner.getTotalLength();
//				System.out.println("inner Length : " + innerLength );
				index += innerLength;
				System.out.println("now Index : " + index);
				if( index >= receive.length ) {
					System.out.println("끝");
					break;
				}
//				else {
////					inner.doIt();
//				}
			}
			
		}
		
	}
	
	private void setTag(){
		tag = receive[index];
		index ++;
	}
	
	private void setLength(){
		length = receive[index];
		index ++;
		

		int isLongForm = checkLength(this.length);
		if ( isLongForm > 1 ) 
		{
//			System.out.println("LongForm");
			byte[] lengthArray = new byte[isLongForm];
			index++;
			
			for( int i = 0; i < isLongForm;  i++) {
				lengthArray[i] = receive[index];
				index++;
			}
			
			this.lengthVal = ByteToHex.bytesToUnsignedInt(lengthArray);
		}
		else 
		{
//			System.out.println("ShortForm");
//			this.lengthVal = length; 
			lengthVal = ByteToHex.byteToUnsignedInt(length);
		}
//		System.out.println("Length");

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
	
	private void setValue(){
		if(lengthVal > 0) 
		{
			value = new byte[lengthVal];
			System.arraycopy(receive, index, value, 0, lengthVal);
			valueFirst = value[0];
		}
		else
		{
			value = null;
		}
	}
	
	public int getIndex() {
		return index;
	}
	
	public int getTotalLength() {
//		인덱스 값 + 길이 바이트 + 태그 길이
		int returnVal = 1 + ( (lengthArray == null )? 1 : lengthArray.length ) + ((value==null)? 0 : value.length);
		System.out.println(" totalLength : " + returnVal);
		return returnVal;
	}
	
	
}
