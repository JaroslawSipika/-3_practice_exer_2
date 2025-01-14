import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.*;



public class AplikacjaTest3 {
	
	public JTextArea pytanie;
	public JTextArea odpowiedz;
	public DataOutputStream dos;
	public Socket s;

	public void tworzGrafike() {
		JFrame ramka = new JFrame("Aplikacja do przeprowadzenia testu");
		ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		Font czcionka = new Font("sanserif" , Font.BOLD, 24);
		
		pytanie = new JTextArea(6, 20);
		pytanie.setLineWrap(true);
		pytanie.setWrapStyleWord(true);
		pytanie.setFont(czcionka);
		
		JScrollPane przewijaniePyt = new JScrollPane(pytanie);
		przewijaniePyt.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		przewijaniePyt.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		
		odpowiedz = new JTextArea(1, 20);
		odpowiedz.setLineWrap(true);
		odpowiedz.setWrapStyleWord(true);
		odpowiedz.setFont(czcionka);
		

		JScrollPane przewijanieOdp = new JScrollPane(odpowiedz);
		przewijanieOdp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		przewijanieOdp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JLabel etykietaPyt = new JLabel ("Pytanie:");
		JLabel etykietaOdp = new JLabel ("Odpowiedz:");
		
		JButton przyciskWyslij = new JButton("Wy�lij odpowiedz");
	//	JButton przyciskStart = new JButton("Zacznij test");
		
		panel.add(etykietaPyt);
		panel.add(przewijaniePyt);
		panel.add(etykietaOdp);
		panel.add(przewijanieOdp);
	//	panel.add(przyciskStart);
		panel.add(przyciskWyslij);
		
		
	//	przyciskStart.addActionListener(new PrzyciskStartListener());
		przyciskWyslij.addActionListener(new PrzyciskWyslijListener());
		
		ramka.getContentPane().add(BorderLayout.CENTER, panel);
		ramka.setSize(500, 400);
		ramka.setVisible(true);	
	}
	
	
	public class PrzyciskStartListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			}
		}

	public void komunikacja() {
			try {
				
				Scanner scn = new Scanner(System.in);
				InetAddress ip = InetAddress.getByName("localhost");
				
				s = new Socket("localhost",5055);
				DataInputStream dis = new DataInputStream(s.getInputStream());
				dos = new DataOutputStream(s.getOutputStream());
				tworzGrafike();
				
				
				while (true)
				{
					
					String k = dis.readUTF();
					
					System.out.println(k);
					
					String[]b = k.split("/");
					for (int s1 = 0; s1<5; s1++) {
							pytanie.append(b[s1] + '\n' );
					}
					
	                	String received = dis.readUTF(); 
		                System.out.println(received); 

	            }
			} catch(Exception e1) {
				e1.printStackTrace();
			
		}
	}
	public class PrzyciskWyslijListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {	
			String k = odpowiedz.getText();
			odpowiedz.setText("");
			pytanie.setText("");
			try {
				dos.writeUTF(k);
			
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
	
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		
		AplikacjaTest3 aplikacja = new AplikacjaTest3();
		aplikacja.komunikacja();
			
	}
}
