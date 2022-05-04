import java.util.concurrent.Callable;

public class WatekCallable implements Callable{

	int value = 0;
	public WatekCallable(int value) {
	this.value = value;
}

@Override
public Integer call() throws Exception {

if(Thread.currentThread().isInterrupted()) {
System.out.println(this + " interrupted!");
return null;
}

try {
Thread.sleep(300);
}catch(InterruptedException exc) {
System.out.println(this + " sleep() interrupted!");
return null;
}
return value;

}

}