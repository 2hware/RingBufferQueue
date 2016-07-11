

public class Main {

    public static void main(String[] args) {

        //Size of the RingBuffer
        int RING_BUFFER_QUEUE_SIZE = 10;
        // For Producer, its the number of object to produce , for Consumer its termination signal to stop consuming.
        int PRODUCED_RANGE = 100;
        //Delay in producing process in milliseconds
        int PRODUCER_DELAY = 0;
        //Delay in consuming process in milliseconds
        int CONSUMER_DELAY = 20;

        //RingBufferImplementation
        RingQueue<String> ringQueue = new RingQueue<>(RING_BUFFER_QUEUE_SIZE);

        //Producer
        RingBufferProducer ringBufferProducer = new RingBufferProducer(ringQueue, PRODUCED_RANGE, PRODUCER_DELAY);

        //Consumer
        RingBufferConsumer ringBufferConsumer = new RingBufferConsumer(ringQueue, PRODUCED_RANGE, CONSUMER_DELAY);

        //Starting producing processing thread
        new Thread(ringBufferProducer).start();

        //Starting consuming processing thread
        new Thread(ringBufferConsumer).start();

    }
}