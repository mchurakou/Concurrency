import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by badbug on 27.10.2014.
 */
public class Runner {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("START");
        Random rand = new Random();
        Generator gen = new Generator();
        int numberProducers = 5;
        int numberConsumers = 10;
        final BlockingQueue<Integer> queue = new PriorityBlockingQueue<Integer>(10);
        final List<Producer> producers = new ArrayList<Producer>();
        final List<Consumer> consumers = new ArrayList<Consumer>();


        CyclicBarrier bProducer = new CyclicBarrier(numberProducers, new Runnable(){
            public void run(){
                System.out.println("barrier producer work");
                for (Producer p : producers){
                    queue.add(p.getVal());
                }
            }
        });



        ExecutorService exe = Executors.newCachedThreadPool();

        for (int i = 0 ; i < numberProducers; i++){
            Producer producer = new Producer(gen, (rand.nextInt(5) + 1) * 100, i + 1, bProducer);
            producers.add(producer);
            exe.submit(producer);

        }


        for (int i = 0 ; i < numberConsumers; i++){
            Consumer consumer = new Consumer(queue, (rand.nextInt(5) + 1) * 100, i + 1);
            consumers.add(consumer);
            exe.submit(consumer);
        }



        Thread.sleep(20 * 1000);
        exe.shutdownNow();


    }
}
