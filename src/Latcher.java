import java.util.concurrent.CountDownLatch;

/**
 * Created by badbug on 27.10.2014.
 */
public class Latcher {

    static CountDownLatch current;

    CountDownLatch parent;
    CountDownLatch child;

    public Latcher( CountDownLatch parent, CountDownLatch current){
        this.parent = parent;
        this.child = current;
    }

    public static Latcher getLatcher(){


        CountDownLatch newLatch = new CountDownLatch(1);
        Latcher res = null;
        if (current == null){

            res = new Latcher(null, newLatch);

        } else {

            res = new Latcher(current, newLatch);
        }

        current = newLatch;

        return res;
    }
}
