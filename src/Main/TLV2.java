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
	
	public TLV2( byte[] received , int receivedIndex ) {
		receive = received;
		index = receivedIndex; 
		
		setTag();
		setLength();
		System.out.println("check length : " + lengthVal );
		setValue();
		
		doIt();
	}
	
	public void doIt() {
		System.out.println("index : " + index );
		
		if(AsnEnum.forValue(tag) == AsnEnum.SEQUENCE
			|| AsnEnum.forValue(tag) == AsnEnum.SET
			)
		{
			while( index < receive.length ) {
				TLV2 inner = new TLV2( receive , index);

				int innerLength = inner.getTotalLength();
				System.out.println("inner Length : " + innerLength );
				index += innerLength;
				System.out.println("now Index : " + index);
				if( index >= receive.length ) {
					System.out.println("끝");
					break;
				}
				else {
					inner.doIt();
				}
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
		if ( isLongForm >= 1 ) 
		{
			byte[] lengthArray = new byte[isLongForm];
			index++;
			
			for( int i = 0; i < isLongForm;  i++) {
				lengthArray[i] = receive[index];
				index++;
			}
			
			lengthVal = ByteToHex.bytesToUnsignedInt(lengthArray);
		}
		else 
		{
			lengthVal = ByteToHex.byteToUnsignedInt(length);
		}

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
		value = new byte[lengthVal];
		
		System.arraycopy(receive, index, value, 0, lengthVal);
	}
	
	public int getIndex() {
		return index;
	}
	
	public int getTotalLength() {
//		인덱스 값 + 길이 바이트 + 태그 길이
		return index + 1 + ( (lengthArray == null )? 1 : lengthArray.length );
	}
	
	
}
