import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class Sluchacze {

public static void main(String[] args) {
Callable c1 = new WatekCallable (2);
Callable c2 = new WatekCallable (7);
Callable c3 = new WatekCallable (15);

FutureTask t1 = new FutureTask (c1) {
			public void done() {
			try {
			System.out.println("Wynik w�tku 1: " + get());
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
			}
			};


FutureTask t2 = new FutureTask (c2){
			public void done() {
			try {
			System.out.println("Wynik w�tku 2: " + get());
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
			}
};

FutureTask t3 = new FutureTask (c3){
			public void done() {
			try {
			System.out.println("Wynik w�tku 3: " + get());
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
			}
			};


ExecutorService exec = Executors.newFixedThreadPool(3);

exec.execute(t1);
exec.execute(t2);
exec.execute(t3);

}

}


