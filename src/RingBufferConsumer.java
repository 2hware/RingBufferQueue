
/**
 * Created by Hussein on 2016-07-03.
 */
public class RingBufferConsumer implements Runnable {

    RingQueue ringQueue;
    int terminationCode;
    int delay;

    public RingBufferConsumer(RingQueue ringQueue, int terminationCode, int delay) {
        this.ringQueue = ringQueue;
        this.terminationCode = terminationCode;
        this.delay = delay;
    }

    @Override
    public void run() {
        String terminationCode = "";
        while (!("" + (this.terminationCode - 1)).equals("" + terminationCode)) {
            try {
                Thread.sleep(delay);
                Object o = this.ringQueue.remove();
                if (o != null) {
                    terminationCode = o.toString();
                    this.ringQueue.printActionSummery("Consumed", o.toString());
                }
            } catch (InterruptedException e) {
            }

        }


    }
}
