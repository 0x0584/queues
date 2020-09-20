import java.util.Iterator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item>
	implements Iterable<Item>
{
	private static final String ExcpNullItem = "Cannot add a null";
	private static final String ExcpEmptyQueue = "The Queue is already empty";
	private static final String ExcpUnsupported = "Cannot remove on itertor";
	private static final double GrowthRate = 1.5;

	private Object[] base = new Object[2];
	private int head = 0, tail = 0;

	private void fit() {
		Object[] old = base;
		boolean shrink = (size() + 2 < base.length / GrowthRate);

		if (tail < base.length && !shrink)
			return;

		int sz = 3;
		if (shrink)
			sz = Math.max((int)(base.length / GrowthRate), sz);
		else
			sz = (int)(base.length * GrowthRate);

		// StdOut.println("new size: " + sz);

		base = new Object[sz];
		tail = 0;
		while (head + tail < old.length) {
		// StdOut.println("kkkkk ");
			base[tail] = old[head + tail];
			tail++;
		}
		head = 0;
	}

	// construct an empty randomized queue
	public RandomizedQueue() {  }

	// is the randomized queue empty?
	public boolean isEmpty() { return base == null || size() == 0; }

	// return the number of items on the randomized queue
	public int size()        { return tail - head; }

	// add the item
	public void enqueue(Item item) {
		if (item == null)
			throw new IllegalArgumentException(ExcpNullItem);
		fit();
		base[tail++] = (Object) item;
	}

	// remove and return a random item
	public Item dequeue() {
		if (isEmpty())
			throw new java.util.NoSuchElementException(ExcpEmptyQueue);
		Item item = (Item) base[head];
		base[head++] = null;
		fit();
		// StdOut.printf(" deq>> head: %d - tail: %d\n", head, tail);
		return item;
	}

	// return a random item (but do not remove it)
	public Item sample() {
		if (isEmpty())
			throw new java.util.NoSuchElementException(ExcpEmptyQueue);
		// StdOut.printf(" sample>> head: %d - tail: %d\n", head, tail);
		return (Item) base[StdRandom.uniform(head, tail)];
	}

	private class QueueIterator
		implements Iterator<Item>
	{
		int[] indices = null;
		int walk = 0;
		int walk2 = head;

		public boolean hasNext() {
			if (size() == 0)
				return false;

			if (indices == null) {
				indices = new int[size()];
				indices[0] = head;
				for (int i = 0; head + i < tail; ++i) {
					int j = StdRandom.uniform(0, i + 1);
					indices[i] = indices[j];
					indices[j] = head + i;
				}
			}
			return walk < size();
		}

		public Item next()   {
			if (!hasNext())
				throw new java.util.NoSuchElementException(ExcpEmptyQueue);
			return (Item) base[indices[walk++]];
		}
		public void remove() { throw new UnsupportedOperationException(ExcpUnsupported); }
	}

	// return an independent iterator over items in random order
	public Iterator<Item> iterator() {
		return new QueueIterator();
	}

	// unit testing (required)
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);

		RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
		for (int i = 1; i <= n; ++i) {
			rq.enqueue(i);
			int m = StdRandom.uniform(1, i + 1);
			if (m % i == 0)
				rq.dequeue();
		}

	}
}
