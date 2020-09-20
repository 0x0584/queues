import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {

	private Permutation() { }

	public static void main(String[] args) {
		int k        = (args.length == 0 ? 7 : Integer.parseInt(args[0]));
		int read     = 0;
		int i		 = 0;

		RandomizedQueue<String> q = new RandomizedQueue<String>();
		while (!StdIn.isEmpty()) {
			String s = StdIn.readString();

			read++;
			if (read <= k) {
				q.enqueue(s);
			} else if (k != 0) {
				i++;
				if (i % StdRandom.uniform(1, read + 1) == 0) {
					q.dequeue();
					q.enqueue(s);
				}
			}
		}

		for (String s : q)
			StdOut.println(s);
	}

}
