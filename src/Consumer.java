import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by badbug on 27.10.2014.
 */
public class Consumer implements Runnable  {

    BlockingQueue<Integer> queue;

    public int getVal() {
        return val;
    }



    int val;
    int id;
    int timeout;


     public Consumer(BlockingQueue<Integer> queue, int timeout, int id){
        this.queue = queue;
        this.id = id;
        this.timeout = timeout;

    }

    @Override
    public void run() {

        try {
            while (!Thread.interrupted()) {
                Latcher latcher = null;
                synchronized (queue){
                    val = queue.take();
                   latcher  = Latcher.getLatcher();
                }


                Thread.sleep(timeout);

                if (latcher.parent != null){
                    latcher.parent.await();
                }

                System.out.println("******* CONSUMER ID=" + id + " CONSUMED VALUE (" +timeout +")=" + val);

                latcher.child.countDown();




            }
        } catch(Exception e){
            e.printStackTrace();
        }

    }
}
