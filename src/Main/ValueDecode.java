package Main;

import util.ByteToHex;

public class ValueDecode {
	
	public static void decoding(String[] strArr, int grade , int startPoint , int endPoint) {
/*
		while(true) {
			System.out.println("디코딩에서 새로운 TLV 객체 생성시 깊이 : " + grade + " 배열 총 길이 : "+ strArr.length +" 시작위치 : " + startPoint);
			int length = ByteToHex.hexToInt( strArr[ startPoint+1] );
			TLV tlv = new TLV(strArr, grade, startPoint );
			
			String Vall = "";
			if(tlv.getTag() != null || tlv.getValue() != null ) {
				Vall = "Value length : " + (tlv.getValue().length()/3) +" Value : " + tlv.getValue() ; 
			}
			System.out.println("start point : " + startPoint + " TLV tag : " + tlv.getTag()  +" \n"
							+ "Length Get Str(int) : " + tlv.getLengthStr() + "(" + tlv.getLengthInt() + ") "
							+ "Value total string Length : " + tlv.getPacketLength() +" \n\n"
							+ Vall 
							);
			
			System.out.println( tlv.getString() );
//			InputString.grandmom.append(tlv.getString());
			//다음 시작지점이 마지막 부분보다 작은지 확인.
			
			startPoint = startPoint + tlv.getPacketLength();
			if( tlv.getPacketLength() == 0 )
				startPoint = startPoint + 1;
			System.out.println("시작지점 재설정 :" + startPoint );
//			if(tlv.getLengthInt() + start + 1 + (tlv.getLengthStr().length()/2) >= endPoint )
			if( startPoint >= endPoint )
			{
				
				break;
			}
			
			
		}
		
		*/
		
	}

}
