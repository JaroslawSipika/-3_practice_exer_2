import java.rmi.Naming;

import javax.naming.Context;
import javax.naming.InitialContext;

public class MojaUslugaKlient {

	public static void main(String[] args) {
		new MojaUslugaKlient().doRoboty();

	}

	public void doRoboty() {
		try {
			
			
			Context context = new InitialContext();
			MojaUsluga usluga = (MojaUsluga) context.lookup("rmi://localhost/NotacjaONP");
			String s = usluga.ZamienNaONP("(2+4)*(3-8)^2");
			System.out.println("R�wnanie w postaci ONP: " + s);
			
			double wynik = usluga.ObliczONP(s);
			System.out.println("Wynikiem r�wnania jest: " + wynik);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
