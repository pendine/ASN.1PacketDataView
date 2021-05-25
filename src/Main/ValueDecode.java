package Main;

public class ValueDecode {
	
	public static void decoding(String[] strArr, int grade , int startPoint , int endPoint) {

		while(true) {
			System.out.println("디코딩에서 새로운 TLV 객체 생성시 깊이 : " + grade + " 시작위치 : " + startPoint);
			TLV tlv = new TLV(strArr, grade, startPoint );
			
			
			System.out.println("start point : " + startPoint + " TLV tag : " + tlv.getTag()  +" \n"
							+ "Packet Length : " + tlv.getPacketLength() +" Packet body Length : " + tlv.getLengthInt() +" \n"
							+ "Value length : " + (tlv.getValue().length()/3) +" Value : " + tlv.getValue() );
			
			System.out.println( tlv.getString() );
//			InputString.grandmom.append(tlv.getString());
			//다음 시작지점이 마지막 부분보다 작은지 확인.
			startPoint = startPoint + tlv.getPacketLength();
			System.out.println("시작지점 재설정 :" + startPoint );
//			if(tlv.getLengthInt() + start + 1 + (tlv.getLengthStr().length()/2) >= endPoint )
			if( startPoint >= endPoint )
			{
				break;
			}
			
			
		}
		
	}

}
