import java.util.concurrent.CyclicBarrier;

/**
 * Created by badbug on 27.10.2014.
 */
public class Producer implements Runnable {
    private Generator gen;

    public int getVal() {
        return val;
    }


    private int val;
    private int timeout;
    private int id;
    CyclicBarrier b;

    public Producer(Generator gen, int timeout, int id, CyclicBarrier b ){
        this.gen = gen;
        this.timeout = timeout;
        this.id = id;
        this.b = b;
    }

    @Override
    public void run() {

        try {
            while (!Thread.interrupted()) {
                val = gen.getValue();
                System.out.println("ID=" + id + " got value=" + val);
                Thread.sleep(timeout);
                System.out.println("ID=" + id + " finished processing(" +timeout +") value = " + val);
                b.await();
            }
        } catch(Exception e){
            e.printStackTrace();
        }

    }
}
