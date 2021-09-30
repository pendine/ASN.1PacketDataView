package util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

public abstract class TypeHelper {
	
	private static int BYTE_MASKED_ARRAY[] = { 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80 };

	public static int unsignedByteToInt(byte srcValue) {
		return srcValue & 0xFF;
	}

	public static int unsignedShortToInt(short srcValue) {
		return srcValue & 0xFFFF;
	}

	public static long intToUnsignedInt(int srcValue) {
		return srcValue & 0xFFFFFFFFL;
	}

	public static int UnisignedLongToInt(long srcValue) {
		return (int)(srcValue & 0xFFFFFFFFL);
	}
	
	public static int extractBits(byte srcValue, int beginBitDigit, int endBitDigit) {
		int maskedValue = 0;
		for (int idx = beginBitDigit ; idx <= endBitDigit ; ++idx) { maskedValue += BYTE_MASKED_ARRAY[idx]; }
		
		return (srcValue & maskedValue) >> beginBitDigit;
	}
	
	public static int extractBit(byte srcValue, int bitDigit) {
		return ((srcValue & BYTE_MASKED_ARRAY[bitDigit]) == BYTE_MASKED_ARRAY[bitDigit]) ? 1 : 0;
	}
	
	public static int compareMaskedValue(byte srcValue, int maskValue, int compareValue) {
		int resultValue = unsignedByteToInt(srcValue) & maskValue;
		return (resultValue > compareValue) ? 1 : ((resultValue == compareValue) ? 0 : -1);
	}
	

	public static byte[] hexToByteArray(String hex) {
	    if (hex == null || hex.length() == 0) {
	        return null;
	    }

	    byte[] ba = new byte[hex.length() / 2];
	    for (int i = 0; i < ba.length; i++) {
	        ba[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
	    }
	    return ba;
	}

	public static String byteArrayToHex(byte[] ba) {
		return byteArrayToHex(ba, null);
	}
	
	/**
	 * byte 배열을 seperator로 구분된 16진수 문자열로 변환한다. 16진수 영문은 대문자로 표기됨
	 * @param ba : byte 배열
	 * @param seperator : byte 배열 각 항목 사이의 구분자
	 * @return seperator로 구분된 16진수 문자열
	 */
	public static String byteArrayToHex(byte[] ba, String seperator) {
	    if (ba == null || ba.length == 0) {
	        return null;
	    }

	    StringBuffer sb = new StringBuffer(ba.length * 2);
	    String hexNumber;
	    for (int x = 0; x < ba.length; x++) {
	        hexNumber = "0" + Integer.toHexString(0xff & ba[x]).toUpperCase();

	        sb.append(hexNumber.substring(hexNumber.length() - 2));
	        if (seperator != null && !seperator.isEmpty()) { sb.append(seperator); }
	    }
	    return sb.toString();
	}
	
	final static char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

	public static String bytesToHex(byte[] bytes, int bundleSize /*[bytes]*/, char seperator) {
	        char[] hexChars = new char[(bytes.length * 2) + (bytes.length / bundleSize)];
	        for (int j = 0, k = 1; j < bytes.length; j++, k++) {
	                int v = bytes[j] & 0xFF;
	                int start = (j * 2) + j/bundleSize;

	                hexChars[start] = HEX_ARRAY[v >>> 4];
	                hexChars[start + 1] = HEX_ARRAY[v & 0x0F];

	                if ((k % bundleSize) == 0) {
	                        hexChars[start + 2] = seperator;
	                }   
	        }   
	        return new String(hexChars).trim();    
	}
	
	public static String bytesToHex(byte[] bytes, int bundleSize /*[bytes]*/) {
        return bytesToHex(bytes, bundleSize, ' ');    
	}
	
	/**
	 * byte 값을 16진수 문자열로 변환. 영문은 대문자로 표기
	 * @param b : byte 값
	 * @return 16진수 문자열
	 * @author 한호연
	 */
	public static String byteToHex(byte b) {
        String hexNumber = "0" + Integer.toHexString(0xff & b).toUpperCase();
        return hexNumber.substring(hexNumber.length() - 2);
	}

	public static int convertNotNullStringToInt(String data) {
		if (data != null && !data.isEmpty()) {
			return Long.valueOf(data).intValue();
		}
		return 0;
	}

	public static double convertNotNullStringToDouble(String data) {
		if (data != null && !data.isEmpty()) {
			return Double.valueOf(data);
		}
		return 0;
	}

//	public static long checkCurrentTimeDiff(String checkTime) throws ParseException {
//		return ((new Date().getTime() -
//				(DateHelper.dateParse(checkTime).getTime())) / 1000);
//	}



	public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
	    for (Entry<T, E> entry : map.entrySet()) {
	        if (value.equals(entry.getValue())) {
	            return entry.getKey();
	        }
	    }
	    return null;
	}
	
	
	public static final int BIT_1 = 0x00000001;
    public static final int BIT_2 = 0x00000003;
    public static final int BIT_3 = 0x00000007;
    public static final int BIT_4 = 0x0000000F;
    public static final int BIT_5 = 0x0000001F;
    public static final int BIT_6 = 0x0000003F;
    public static final int BIT_7 = 0x0000007F;
    public static final int BIT_8 = 0x000000FF;
    public static final int BIT_9=0x000001FF;
    public static final int BIT_10=0x000003FF;
    public static final int BIT_11=0x000007FF;
    public static final int BIT_12=0x00000FFF;
    public static final int BIT_13=0x00001FFF;
    public static final int BIT_14=0x00003FFF;
    public static final int BIT_15=0x00007FFF;
    public static final int BIT_16=0x0000FFFF;
    
    public static int getBitField(byte b, int field, int count) {
    	
		if(count == 1) count = BIT_1;
		else if(count == 2) count = BIT_2;
		else if(count == 3) count = BIT_3;
		else if(count == 4) count = BIT_4;
		else if(count == 5) count = BIT_5;
		else if(count == 6) count = BIT_6;
		else if(count == 7) count = BIT_7;
		else if(count == 8) count = BIT_8;
		else if(count == 9) count = BIT_9;
		else if(count == 10) count = BIT_10;
		else if(count == 11) count = BIT_11;
		else if(count == 12) count = BIT_12;
		else if(count == 13) count = BIT_13;
		else if(count == 14) count = BIT_14;
		else if(count == 15) count = BIT_15;
		else if(count == 16) count = BIT_16;
		
    	return (b>>field)&count;
    }

    
    /**
     * 바이트 변수 b의 field번째 비트부터 count개수만큼의 비트 값을 value값으로 설정한다.
     * @param b : 바이트 변수
     * @param field : LSB부터 비트 번호 (0~7)
     * @param count : field부터 비트 개수 (LSB->MSB)
     * @param value : 설정하려는 값
     * @return 비트 값이 적용된 바이트 변수. 비트개수(count)에 대한 최대값을 넘지 않아야 함.
     * @author 한호연
     * @ 수정함, 2017.10.23
     */
    
    
/*  2017.10.23 원본  public static byte setBitField(byte b, int field, int count, int value) {
        if (field + count <= 8 && value < Math.pow(2, count))
           b = (byte) (b | (value << field));
        return b;
     }*/
    public static byte setBitField(byte b, int field, int count, int value) {
    	if (field + count <= 8 && value < Math.pow(2, count)) 
    		if(count == 1) count = BIT_1;
    		else if(count == 2) count = BIT_2;
    		else if(count == 3) count = BIT_3;
    		else if(count == 4) count = BIT_4;
    		else if(count == 5) count = BIT_5;
    		else if(count == 6) count = BIT_6;
    		else if(count == 7) count = BIT_7;
    		else if(count == 8) count = BIT_8;

		   b &= ~(count << field); 
		   b |= ((value&count)<<field);

    	return b;
    }   
   
    
    public static <T> List<T> copylist(Collection<T> org ){		
		ArrayList<T> temp = new ArrayList<T>(org.size());		
		temp.addAll(org);
		
		return temp;
	}
}