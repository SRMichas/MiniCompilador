package main;

public class asd {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s1 = "\".*\"";
		String s3 = "[0-9]{1,3}\\.[0-9]{1,3}f";
		String s2 = "\"";
		double var = 1.;
		System.out.println(s2.matches(s1));
		System.out.println(Float.parseFloat("1.23"));
		
		String num = "1.";
		System.out.println("Float "+num+" correcto? "+Float.parseFloat(num));
		float f = 1f;
		System.out.println(num.contains(".f"));
	}

}
