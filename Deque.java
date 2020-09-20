import java.util.Iterator;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Deque<Item>
	implements Iterable<Item>
{
	private static final String ExcpNullItem = "Cannot add a null";
	private static final String ExcpEmptyDeque = "Deque is already empty";
	private static final String ExcpUnsupported = "Cannot remove on itertor";

	private final class Node
	{
		private Item item;
		private Node next;
		private Node prev;

		public Node()          {   setNode(null, null, null);   }
		public Node(Node prev, Item item, Node next) {
			if (item == null)
				throw new IllegalArgumentException(ExcpNullItem);
			setNode(prev, item, next);
		}

		private void setNode(Node prev, Item item, Node next) {
			this.prev = prev;
			this.item = item;
			this.next = next;
		}

		public void setItem(Item item) {
			if (item == null)
				throw new IllegalArgumentException(ExcpNullItem);
			setNode(prev, item, next);
		}

		public void setNext(Node next) {
			if (next == null)
				throw new IllegalArgumentException(ExcpNullItem);
			setNode(prev, item, next);
		}

		public void setPrev(Node prev) {
			if (prev == null)
				throw new IllegalArgumentException(ExcpNullItem);
			setNode(prev, item, next);
		}

		public Item getItem() { return item; }
		public Node getNext() { return next; }
		public Node getPrev() { return prev; }
	};

	private final class List
	{
		private Node head = new Node();
		private Node tail = new Node();

		private int size;

		public List() {
			size = 0;
			head.setNext(tail);
			tail.setPrev(head);
		}

		public int size()        { return size; }
		public boolean isEmpty() {
			return head.getNext() == tail
				|| tail.getPrev() == head;
		}

		public void pushBack(Item item) {
			Node node = new Node(tail.getPrev(), item, tail);
			tail.getPrev().setNext(node);
		    tail.setPrev(node);
			size++;
		}

		public Item popBack() {
			if (isEmpty())
				throw new java.util.NoSuchElementException(ExcpEmptyDeque);

			Item tmp = tail.getPrev().getItem();
			tail.setPrev(tail.getPrev().getPrev());
			tail.getPrev().setNext(tail);
			size--;
			return tmp;
		}

		public void pushFront(Item item) {
			Node node = new Node(head, item, head.getNext());
		    head.getNext().setPrev(node);
		    head.setNext(node);
			size++;
		}

		public Item popFront() {
			if (isEmpty())
				throw new java.util.NoSuchElementException(ExcpEmptyDeque);

			Item tmp = head.getNext().getItem();
			head.setNext(head.getNext().getNext());
		    head.getNext().setPrev(head);
			size--;
			return tmp;
		}

		private class ListIterator
			implements Iterator<Item>
		{
			private Node walk = head;

			public boolean hasNext() { return walk.getNext() != tail; }

			public Item next() {
				if (!hasNext())
					throw new java.util.NoSuchElementException(ExcpEmptyDeque);
				Item item = walk.getNext().getItem();
				walk = walk.getNext();
				return item;
			}

			public void remove() {
				throw new UnsupportedOperationException(ExcpUnsupported);
			}
		};

		public Iterator<Item> iterator() {
			return new ListIterator();
		}
	};

	private final List list;

    // construct an empty deque
    public Deque()                   { list = new List(); }

    // is the deque empty?
    public boolean isEmpty()         { return list.isEmpty(); }

    // return the number of items on the deque
    public int size()                { return list.size(); }

    // add the item to the front
    public void addFirst(Item item)  { list.pushFront(item); }

    // add the item to the back
    public void addLast(Item item)   { list.pushBack(item); }

    // remove and return the item from the front
    public Item removeFirst()        { return list.popFront(); }

    // remove and return the item from the back
    public Item removeLast()         { return list.popBack(); }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() { return list.iterator(); }

	private static boolean which(int i) {
		return StdRandom.uniform(1, i + 1)
			> StdRandom.uniform(1, i + 1);
	}

    // unit testing (required)
    public static void main(String[] args) {
		Deque<String> q = new Deque<String>();
		int size = 500;

		if (args.length > 0)
			size = Integer.parseInt(args[0]);

		for (int i = 1; i <= size; ++i)  {
			try {
				if (which(i)) {
					if (which(i))
						q.addLast(Integer.toString(i));
					else
						q.addFirst(q.removeLast());
				} else {
					if (which(i))
						q.addFirst(Integer.toString(i));
					else
						q.addLast(q.removeFirst());
				}
			} catch(java.util.NoSuchElementException e) {
				if (q.isEmpty())
					StdOut.println("(** rm from empty **)");
			}
		}

		StdOut.println("\nFinal Deque");
		for (String s : q)
			StdOut.printf(s + " ");

		Iterator<String> itr = q.iterator();
		int i = 0;

		StdOut.println();
		StdOut.println("\nFinal Deque");
		while (itr.hasNext()) {
			try {
				i++;
				StdOut.printf(itr.next() + " ");
				if (StdRandom.uniform(0, i) % 2 == 0)
					itr.remove();
			} catch(UnsupportedOperationException e) {
				continue;
			}
		}
		StdOut.println();
	}
}
