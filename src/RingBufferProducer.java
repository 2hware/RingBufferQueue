
/**
 * Created by Hussein on 2016-07-03.
 */
public class RingBufferProducer implements Runnable {
    RingQueue ringQueue;
    int runCount;
    int delay;

    public RingBufferProducer(RingQueue ringQueue,int runCount,int delay) {
        this.ringQueue = ringQueue;
        this.runCount=runCount;
        this.delay=delay;
    }


    @Override
    public void run() {

          for (int i=0;i<runCount;i++){
            try {
                Thread.sleep(delay);
                String item=""+i;
                if(this.ringQueue.add(item)) {
                    this.ringQueue.printActionSummery("Produced",item);
                }

            } catch (InterruptedException e) {

            }


        }
    }
}