package Main;

public class InputString {

	public static StringBuilder grandmom = new StringBuilder();
	
	public String[] hexStrArr = null;
	
	public void init(String input) {
		
		input = inputString(input);
		
		initStr(input, hexStrArr);
		
//		for( String str : hexStrArr) {
//			System.out.println("setting arr " + str);
//		}
		
//		TLV tlv = new TLV( hexStrArr , 0 , 0);
//		
//		System.out.println("asdfasdf");
//		
//		try 
//		{
//			System.out.println( tlv.getString() );
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
		
		ValueDecode.decoding( hexStrArr, 0, 0, hexStrArr.length-1);
		
	}
	
	private String inputString(String hexCode) {
		
		hexCode = hexCode.replace(" " , "");

		if(hexCode.length() % 2 != 0 ) {
			System.out.println("Hexcode length is can't divide to 2 ");
			return hexCode;
		}
		
		int length = hexCode.length() / 2;
		
		hexStrArr = new String[length];

		return hexCode;
		
	}
	
	private void initStr( String hexCode , String[] hexStrArr ) {
		
		for(int i = 0 ; i < hexCode.length()/2 ; i++ ) {
			int startPoint = i * 2; 
			hexStrArr[i] = new String ( hexCode.substring( startPoint , startPoint+2 ) );
		}
//		this.hexStrArr = hexStrArr;
		
	}
	
	
	
}
