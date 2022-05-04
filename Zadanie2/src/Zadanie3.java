import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class Zadanie3 {

	public static void main(String[] args) {
		
		Random r = new Random();
		ArrayList<Tasks<Integer>> aL = new ArrayList<Tasks<Integer>>();
		ExecutorService exec = Executors.newFixedThreadPool(4);
		
		for (int i = 0; i < 10; i++) {
			aL.add(new Tasks<Integer>(new TimerCallable("Zadanie " + i, r.nextInt(100))));
		}
		
		for (Tasks<Integer> fT : aL) {
			exec.execute(fT);
		}
		exec.shutdown();
	}
}

class TimerCallable implements Callable<Integer>{

	String name; 
	int value = 0; 
	public TimerCallable(String name, int value) {
		this.name=name;
		this.value=value;
	}
	
	@Override
	public String toString() {
		return "TimerCallable [name=" + name + ", value=" + value + "]";
	}

	@Override
	public Integer call() throws Exception { 
		Integer k = 0;
		
		for(int i=0; i<4; i++) {
			Thread.sleep(200);
			k += value * i;	
		}
		return k;
	}
}

class Tasks<T> extends FutureTask<T>{
	String name;
	
	public Tasks(Callable<T> c) {
		super(c);
		name = "" + c;
	}
	protected void done() {
		T result = null;
		try {
			if(Thread.currentThread().isInterrupted()){
				System.out.println(this + " interrupted!");
				return;
			}
			if(isCancelled()){
				System.out.println(this + " cancelled!");
				return;
			}
			result = this.get();
			System.out.println("Zadanie " + this + " -> wykonane: " + result);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
	public String toString() {
		return "Tasks [" + name + "]";
	}	
}
