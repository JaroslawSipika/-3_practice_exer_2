import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;

import javax.naming.Context;
import javax.naming.InitialContext;




public class MojaUslugaImpl extends UnicastRemoteObject implements MojaUsluga
{

	public MojaUslugaImpl() throws RemoteException {}

	public String ZamienNaONP(String wyraz) throws RemoteException {
		
		String wyrazenie = wyraz;
		String onp = "";
		String nieLiczby="+-*/^()";
		
		Stack<String> stos = new Stack<String>();
		StringTokenizer st = new StringTokenizer(wyrazenie, "+-*/^()", true);
		while(st.hasMoreTokens())
			{
				String s = st.nextToken();
				if( s.equals("+") || s.equals("*") || s.equals("-") || s.equals("/") || s.equals("^"))
				{
					while(!stos.empty() && priorytet(stos.peek()) >= priorytet(s))
					{
						onp += stos.pop() + " ";
					}
					stos.push(s);
				}
				else if(s.equals("("))
				{
					stos.push(s);
				}
				else if(s.equals(")"))
				{
					while(!stos.peek().equals("("))
					{
						onp += stos.pop() + " ";
					}
					stos.pop();
				}	
				else { onp += s + " "; }
			}

		while(!stos.empty())
		{
			onp += stos.pop() + " ";
		}

		return onp;
		
	}
	

	public int priorytet(String operator)
	{
		if(operator.equals("+") || operator.equals("-")) {return 1;}
		else if(operator.equals("*") || operator.equals("/")) {return 2;}
		else if(operator.equals("^")){return 3;}
		else {return 0;}
	}

		
	public double ObliczONP(String wyrazenieONP) throws RemoteException {
		
		String wejscie = wyrazenieONP+" =";
		Stack<Double> stos = new Stack<Double>();
		double a=0;
		double b=0;
		double w=0;
		String buduj="";
		String spacja=" ";
		char sp=' ';
		int licznik=0;
		
			do{
					char czar=wejscie.charAt(licznik);
					if(czar=='+' || czar == '-' || czar == '*' || czar == '/' || czar == '^')
						{
							if(!stos.empty()){
									b=stos.pop();
									a=stos.pop();
									if(czar=='+'){w=a+b;}
									else if(czar=='-'){w=a-b;}
									else if(czar=='*'){w=a*b;}
									else if(czar=='/'){w=a/b;}
									else if(czar=='^')
									{
										if(b==0)
										{ w=1;
										} else {
											w=a;
											int licz=1;
											while(licz<(int)b)
											{
													w*=w;
													licz++;
											}
										}
									}
							stos.push(w);
							}
						}
					else if(czar == sp)
					{
						if(buduj.compareTo("")!=0){
							double tmp = Double.parseDouble(buduj);
							stos.push(tmp);
							buduj="";
						}

					}
					else if(czar=='=')
					{
						if(!stos.empty()){
						w=stos.pop();
						break;
						}
					}
					else if(czar>='0' && czar <='9')
					{
						buduj+=czar;
					}

				licznik++;
		}while(licznik<wejscie.length());
			
		return w;
		
	}
	

	public static void main(String[] args) {
		try {
			MojaUsluga usluga = new MojaUslugaImpl();
			Registry reg = LocateRegistry.createRegistry(1099);
			reg.bind("NotacjaONP", usluga);
 
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	

}
