package asn_enum;

import java.util.HashMap;
import java.util.Map;

import util.TypeHelper;


public enum AsnEnum {

	BIT_STRING(0x03, "BIT STRING")
	, BOOLEAN(0x01, "BOOLEAN")
	, INTEGER(0x02, "INTEGER")
	, NULL(0x05, "NULL")
	, OBJECT_IDENTIFIER(0x06, "OBJECT IDENTIFIER")
	, OCTET_STRING(0x04, "OCTET STRING")
	, BMPString(0x1E, "BMPString")
	, IA5String(0x16, "IA5String")
	, PrintableString(0x13, "PrintableString")
	, TeletexString(0x14, "TeletexString")
	, UTF8String(0x0C, "UTF8String")
	, SEQUENCE(0x30, "SEQUENCE (OF)")
	, SET(0x31, "SET (OF)")
	, A1(0xa1, "0xA1")
	, A2(0xa2, "0xA2")
	, A3(0xa3, "0xA3")
	, A4(0xa4, "0xA4")
	, A5(0xa5, "0xA5")
	, A6(0xa6, "0xA6")
	, A7(0xa7, "0xA7")
	, A8(0xa8, "0xA8")
	;

	private final int value;
	private final String strValue;

	private final static Map<Integer, AsnEnum> map = new HashMap<Integer, AsnEnum>();
	static {
		for (AsnEnum e : values())
			map.put(e.value, e);
	}

	private AsnEnum(int value, String strValue) {
		this.value = value;
		this.strValue = strValue;
	}

	public static AsnEnum forValue(int value) {
		return map.get(value);
	}

	public static AsnEnum forValue(byte value) {
		return map.get(TypeHelper.unsignedByteToInt(value));
	}
	
	public int getValue() {
		return this.value;
	}
	
	public byte[] getByteArrayValue() {
		byte[] b = new byte[1];
		b[0] = (byte)this.value;
		return b;
	}

	@Override
	public String toString() {
		return this.strValue;
	}
	
	
}

