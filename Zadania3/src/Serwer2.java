import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;




public class Serwer2 {
	
		static int liczbaKlientów = 0;
		protected static BazaDanych jdbc = new BazaDanych();
		
		public static void main(String[] args) throws IOException  
	    { 
	        ServerSocket ss = new ServerSocket(5055); 
	          
	        while (true)  
	        { 
	        	
	            Socket s = null; 
	              
	            try 
	            { 
		                s = ss.accept(); 
	           
	                System.out.println("A new client is connected : " + s); 
	                  
	 
	                DataInputStream dis = new DataInputStream(s.getInputStream()); 
	                DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
	                  
	                System.out.println("Assigning new thread for this client"); 
	  
	            
	                if (liczbaKlientów < 250) {
	                liczbaKlientów++;
	                Thread t = new ClientHandler(s, dis, dos); 
	                t.start(); 
	                liczbaKlientów--;
	                
	                } else {
	                	System.out.println("Serwer Przepe³niony");
	                	s.close();
	                }
	                
	            } 
	            catch (Exception e){ 
	                s.close(); 
	                e.printStackTrace(); 
	            } 
	        }  
	    } 
	} 
	  

	class ClientHandler extends Thread  
	{ 
	   
	    final DataInputStream dis; 
	    final DataOutputStream dos; 
	    final Socket s; 
	    ArrayList<Pytanie> listaPytan;
		int m = 0;
		int t = 0;
		int c = 0;
		int liczbaPytan = 0;
	
	    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos)  
	    { 
	    	wczytajPytania();
	    	this.s = s; 
	        this.dis = dis; 
	        this.dos = dos;
	    } 
	    
		public class Pytanie{
			
			public String wiersz1;
			
			public Pytanie(String a, String b, String c, String d, String e) {
				{
					wiersz1 = a+"/" + b+"/" + c+"/" + d+"/" + e+"/";
				}
			
		}
		}
	
		public void wczytajPytania() {
			
			
			
			listaPytan = new ArrayList<Pytanie>();
			try {
				
				String pytanie1 = null;
				String odpowiedz1 = null;
				String odpowiedz2 = null;
				String odpowiedz3 = null;
				String odpowiedz4 = null;
				
				
				for(int i = 0; i<4; i++) 
				{	
					pytanie1   = Serwer2.jdbc.zwrocDane("pytanie", i+1);
					odpowiedz1 = Serwer2.jdbc.zwrocDane("a", i+1);
					odpowiedz2 = Serwer2.jdbc.zwrocDane("b", i+1);
					odpowiedz3 = Serwer2.jdbc.zwrocDane("c", i+1);
					odpowiedz4 = Serwer2.jdbc.zwrocDane("d", i+1);	
					Pytanie pytanko = new Pytanie(pytanie1, odpowiedz1, odpowiedz2, odpowiedz3, odpowiedz4);
					listaPytan.add(pytanko);
					
				}
					
				
				liczbaPytan = listaPytan.size();
				

			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	    
		
	    @Override
	    public void run()  
	    { 
	        String received; 
	        String toreturn; 
	        String[] odpowiedzi;
	        ArrayList listaOdpowiedzi = new ArrayList();
			
	        
	        while (true)  
	        { 
	            try { 
	            
	            		dos.writeUTF(listaPytan.get(m).wiersz1);
	        			m++;
		            	received = dis.readUTF(); 
	               
	
	            
	                if(received.equals("Exit")) 
	                {  
	                    System.out.println("Client " + this.s + " sends exit..."); 
	                    System.out.println("Closing this connection."); 
	                    this.s.close(); 
	                    System.out.println("Connection closed"); 
	                    break; 
	                } 
	                  
	               listaOdpowiedzi.add(received); 
	               
	               if(t == 0 && received.equals("a"))
	               {
	            	   c++;
	            	    
	               }
	               if(t == 1 && received.equals("a"))
	               {

	            	   c++;
	               }
	               if(t == 2 && received.equals("c")) 
	               {

	            	   c++;
	               }
	               if(t == 3 && received.equals("c"))
	               {
	            	   c++;
	               }
	           

	               	String punkty = Integer.toString(c);
	               	t++;
	                
	             
	                switch (received) { 
	                  
	                    case "a" : 
	                        toreturn = "Zapisane i sprawdzone. Masz " + punkty + " punktów"; 
	                        dos.writeUTF(toreturn); 
	                        break; 
	                          
	                   case "b" : 
	                        toreturn = "Zapisane i sprawdzone. Masz " + punkty + " punktów"; 
	                        dos.writeUTF(toreturn); 
	                        break; 
	                   
	                   case "c" : 
	                        toreturn = "Zapisane i sprawdzone. Masz " + punkty + " punktów"; 
	                        dos.writeUTF(toreturn); 
	                        break;
	                        
	                   case "d" : 
	                        toreturn = "Zapisane i sprawdzone. Masz " + punkty + " punktów";  
	                        dos.writeUTF(toreturn); 
	                        break;  
	                        
	                               
	                    default: 
	                        dos.writeUTF("Invalid input. Masz: " + punkty + " punktów"); 
	                        break; 
	                } 
	                
	                
	                
	                if(t==liczbaPytan) {
	                dos.writeUTF("Podaj imie i nazwisko, Twoje punkty to: " + punkty); 
	                String imie = dis.readUTF();
	                Serwer2.jdbc.dodajOdpowiedz(imie, (String)listaOdpowiedzi.get(0), (String)listaOdpowiedzi.get(1), (String)listaOdpowiedzi.get(2), (String)listaOdpowiedzi.get(3));
	                Serwer2.jdbc.dodajWynik(imie, punkty);
	                }

	             
	            } catch (IOException e) { 
	                e.printStackTrace(); 
	            }
	            
	        }
	       
	        try
	        { 
	            
	            this.dis.close(); 
	            this.dos.close(); 
	            this.s.close();
	              
	        }catch(IOException e){ 
	            e.printStackTrace(); 
	            
	        } 
	       
	    }
}

