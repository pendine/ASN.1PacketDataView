package Main;


public class Main {
	
	public static void main(String[] args) {
		InputString test = new InputString();

//		String TestStr = "30 45 80 01 01 81 3C 30 3A 80 01 03 81 03 01 F8 AF "
//				+ "82 01 01 A3 28 81 00 83 00 85 00 A7 20 80 02 07 E2 81 01 "
//				+ "01 82 01 02 83 01 00 84 01 00 85 01 09 A6 03 82 01 00 A7 "
//				+ "06 80 01 F7 81 01 00 A4 03 82 01 00 82 02 A6 C5";
		
		
		String TestStr = "3045800101813C303A800103810301F8AF820101A328810083008500"
				+ "A720800207E2810101820102830100840100850109A603820100"
				+ "A7068001F7810100A4038201008202A6C5";
		
		System.out.println(" input Str : " + TestStr);
		test.init(TestStr);
		
		
		System.out.println("확인용 내뱉기 시작");
		System.out.println( InputString.grandmom.toString() );
		System.out.println("확인용 내뱉기 끝");
		
	}
	
	
}
