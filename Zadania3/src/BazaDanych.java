import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BazaDanych {
	public BazaDanych()
	{
		if(BazaDanych.checkDriver())
		{
			BazaDanych.tworzBaze();
		}
	}
	
	private static int executeUpdate(Statement s, String sql) {
		try {
			return s.executeUpdate(sql);
		} catch (SQLException e) {
			
		}
		return -1;
	}
	
	public static boolean checkDriver() {
		System.out.println("Zaladuj");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Poprawny");
			return true;
		} catch (Exception e) {
			System.out.println("Blad!");
			return false;
		}
	}	
	
	public static void tworzBaze()
	{
		try {
		Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/","root","");
		Statement statement=connection.createStatement();
		String nazwaBazy="TestStolic";
		String dodajBaze ="CREATE DATABASE " + nazwaBazy + ";";
		
		if(executeUpdate(statement, "USE " + nazwaBazy +";") == 0)
		{
			System.out.println("Baza istnieje");
		}
		else
		{
			statement.executeUpdate(dodajBaze);
			System.out.println("Baza utworzona");
			statement.executeUpdate("USE " + nazwaBazy + ";");

			String tabelaPytania="CREATE TABLE pytania ( id INT NOT NULL AUTO_INCREMENT , pytanie VARCHAR(100) NOT NULL , a VARCHAR(100) NOT NULL , b VARCHAR(100) NOT NULL , c VARCHAR(100) NOT NULL , d VARCHAR(100) NOT NULL , poprawnaOdp CHAR NOT NULL , PRIMARY KEY (id));";
			String tabelaWyniki="CREATE TABLE wyniki ( id INT NOT NULL AUTO_INCREMENT , student VARCHAR(100) NOT NULL , wynik VARCHAR(100) NOT NULL , PRIMARY KEY (id));";
			String tabelaOdpowiedzi="CREATE TABLE odpowiedzi ( id INT NOT NULL AUTO_INCREMENT , student VARCHAR(100) NOT NULL , pytanie1 VARCHAR(100) NOT NULL , pytanie2 VARCHAR(100) NOT NULL , pytanie3 VARCHAR(100) NOT NULL , pytanie4 VARCHAR(100) NOT NULL , PRIMARY KEY (id)) ;";
			statement.executeUpdate(tabelaPytania);
			statement.executeUpdate(tabelaWyniki);
			statement.executeUpdate(tabelaOdpowiedzi);
		 	
			String pytanie1="INSERT INTO pytania (pytanie, a, b, c, d, poprawnaOdp) VALUE ( 'Co jest stolic¹ Tajladnii?', 'a) Bangkok', 'b) Berlin', 'c) Pjongczang', 'd) Pekin', 'a' );";
			String pytanie2="INSERT INTO pytania (pytanie, a, b, c, d, poprawnaOdp) VALUE ( 'Co jest stolic¹ Niemiec?', 'a) Berlin', 'b) Warszawa', 'c) Budapeszt', 'd) Tokio', 'a' );";
			String pytanie3="INSERT INTO pytania (pytanie, a, b, c, d, poprawnaOdp) VALUE ( 'Co jest stolic¹ Japonii?', 'a) Seul', 'b) Warszawa', 'c) Tokio', 'd) Pary¿', 'c' );";
			String pytanie4="INSERT INTO pytania (pytanie, a, b, c, d, poprawnaOdp) VALUE ( 'Co jest stolic¹ Finlandii?', 'a) Oslo', 'b) Warszawa', 'c) Helsinki', 'd) Pary¿', 'c' );";
			statement.executeUpdate(pytanie1);
			statement.executeUpdate(pytanie2);
			statement.executeUpdate(pytanie3);
			statement.executeUpdate(pytanie4);
		}
		connection.close();
		statement.close();
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
			System.out.println(e.getErrorCode());
		}	
	}
	
	public void dodajWynik(String user,String wynik)
	{
		try {
		Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/","root","");
		Statement statement=connection.createStatement();
		statement.executeUpdate("USE TestStolic;");
		String dodajwynik="INSERT INTO wyniki(student,wynik) VALUE ('"+ user + "'," + wynik+");";
		statement.executeUpdate(dodajwynik);
		connection.close();
		statement.close();
		}catch(SQLException e){
			System.out.println(e.getMessage());
			System.out.println(e.getErrorCode());
		}		
	}

	public void dodajOdpowiedz(String student,String pytanie1,String pytanie2,String pytanie3,String pytanie4)
	{
	try {
	Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/","root","");
	Statement statement=connection.createStatement();
	statement.executeUpdate("USE TestStolic;");
	String dodajodpowiedz = "INSERT INTO odpowiedzi(student, pytanie1, pytanie2, pytanie3, pytanie4) VALUE ('"+student+"','"+pytanie1+"','"+pytanie2+"','"+pytanie3+"','"+pytanie4+"');"; 
	statement.executeUpdate(dodajodpowiedz);
	connection.close();
	statement.close();
	}
	  catch(SQLException e)
		{	
			System.out.println(e.getMessage());
			System.out.println(e.getErrorCode());
		}		
	}
	
	public String zwrocDane(String rodzaj, int id)
	{
		try {
			Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/","root","");
			Statement statement=connection.createStatement();
			statement.executeUpdate("USE TestStolic;");
			String pytanie="SELECT " + rodzaj + " FROM pytania WHERE id="+id + ";";
			ResultSet resultset=statement.executeQuery(pytanie);
			resultset.next();
			String wynik = resultset.getString(rodzaj);
			return wynik;
		}
			catch(SQLException e)
			{
				System.out.println(e.getMessage());
				System.out.println(e.getErrorCode());
				return "error";
			}		
	}
}



