import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MojaUsluga extends Remote{
	public String ZamienNaONP(String a) throws RemoteException;
	public double ObliczONP(String b) throws RemoteException;
}
