package Main;

import asn_enum.AsnEnum;
import util.ByteToHex;

public class TLV2 {
	byte tag;
	byte length;
	byte[] lengthArray;
	
	public int lengthVal;
	
	private int index = 0;
	
	byte[] receive;
	byte[] value;
	
	public TLV2( byte[] received ) {
		receive = received;
		
		setTag();
		setLength();
		setValue();
		
	}
	
	public void doIt() {

		System.out.println("index : " + index );
		
			if(AsnEnum.forValue(tag) == AsnEnum.SEQUENCE
				|| AsnEnum.forValue(tag) == AsnEnum.SET
				)
			{
				TLV2 inner = new TLV2( value );

				int innerLength = inner.lengthVal;
				System.out.println("inner Length : " + innerLength );
				index += innerLength;
				System.out.println("now Index : " + index);
				if( index >= receive.length ) {
					System.out.println("끝");
//					break;
				}
				else {
					inner.doIt();
				}
				
				
			}
			
		
	}
	
	private void setTag(){
		tag = receive[index];
		System.out.println( "set Tag : " + ByteToHex.byteToHex(tag) + " Tag Name : " + AsnEnum.forValue(tag) );
		index ++;
	}
	
	private void setLength(){
		length = receive[index];
		index ++;
		

		int isLongForm = checkLength(this.length);
		if ( isLongForm > 1 ) 
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

		System.out.println("set length : " + ByteToHex.byteToHex(length) + " length Value : " + length);
		
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
		System.out.println(" Byte Array copy | target index : " + index
						+ " copy Array Start point : "+ 0
						+ " copy Array Last point : " + lengthVal
						);
		System.out.println(" Target Array : " + ByteToHex.bytesToHex(receive) );
		System.out.println(" copy result  : " + ByteToHex.bytesToHex(value)   );
	}
	
	public int getIndex() {
		return index;
	}
	
	
	
	
}
