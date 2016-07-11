import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.concurrent.Semaphore;

/**
 * Created by Hussein on 2016-07-02.
 */
public class RingQueue<E> {
    Semaphore producerSemaphore;
    Semaphore ConsumerSemaphore;
    private E[] buffer;
    private int bufferSize;
    private int headCursor;
    private int tailCursor;
    boolean canWrite = true;
    boolean canRead = false;

    public RingQueue(int bufferSize) {
        buffer = (E[]) new Object[bufferSize];
        this.bufferSize = bufferSize;
        this.headCursor = -1;
        this.tailCursor = -1;
        producerSemaphore = new Semaphore(1);
        ConsumerSemaphore = new Semaphore(1);
    }
    private boolean canMoveHeadCursor() {
        if (canWrite) {

            if (headCursor + 1 == bufferSize) {
                if (tailCursor == -1) {
                    canWrite = false;
                    return false;
                } else {
                    if (tailCursor == 0) canWrite = false;
                    headCursor = 0;
                    canRead = true;
                    return true;
                }
            } else {
                if (headCursor + 1 == tailCursor) canWrite = false;
                canRead = true;
                headCursor++;
                return true;
            }
        }
        return false;
    }
    private boolean canMoveTailCursor() {
        if (canRead) {

            if (tailCursor + 1 == bufferSize) {
                if (headCursor == 0) canRead = false;
                tailCursor = 0;
                canWrite = true;
                return true;

            } else {
                if (tailCursor + 1 == headCursor) canRead = false;
                canWrite = true;
                tailCursor++;
                return true;

            }
        }
        return false;
    }
    public boolean add(E e) {
        try {
            producerSemaphore.acquire();
            if (canMoveHeadCursor()) {
                this.buffer[headCursor] = e;
                producerSemaphore.release();
                ConsumerSemaphore.release();
                return true;
            }
        } catch (InterruptedException e1) {
            return false;
        }
        return false;

    }

    public E remove() {
        try {
            ConsumerSemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (canMoveTailCursor()) {

            E e = this.buffer[tailCursor];
            this.buffer[tailCursor] = null;
            producerSemaphore.release();
            ConsumerSemaphore.release();
            return e;
        }
        return null;
    }

    public synchronized void printActionSummery(String type, String itemValue) {
        System.out.print(type+" :" + itemValue + "> |");
        Arrays.asList(buffer).stream().forEach(e -> System.out.print(((e!= null)?e+"":" ")+"|") );
        System.out.println("");
    }
}
