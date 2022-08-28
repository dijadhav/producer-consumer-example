package producer_consumer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Implementation of producer and consumer class using blocking queue
 * 
 * @author dijadhav
 *
 */
public class ProducerConsumerSample {

	public static void main(String[] args) {
		BlockingQueue<Integer> bq = new LinkedBlockingQueue<Integer>(10);
		Producer producer = new Producer(bq);
		Consumer consumer = new Consumer(bq);
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				producer.produce();
			}
		});
		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				consumer.consume();

			}
		});
		t1.start();
		t2.start();
	}

}

/**
 * Implementation of Producer class
 * 
 * @author dijadhav
 */
class Producer {
	BlockingQueue<Integer> bq;
	int i = 0;

	public Producer(BlockingQueue<Integer> bq) {
		this.bq = bq;
	}

	public void produce() {
		System.out.println("Producer started");
		while (true) {
			synchronized (bq) {
				while (bq.size() == 10) {
					try {
						bq.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				bq.add(i++);
				bq.notifyAll();
			}

		}
	}

}

/**
 * Implementation of consumer class
 * 
 * @author dijadhav
 *
 */
class Consumer {
	BlockingQueue<Integer> bq;

	public Consumer(BlockingQueue<Integer> bq) {
		this.bq = bq;
	}

	public void consume() {
		System.out.println("Consumer started");
		while (true) {
			synchronized (bq) {
				while (bq.isEmpty()) {
					try {
						bq.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println(bq.poll());
				bq.notifyAll();
			}

		}
	}

}