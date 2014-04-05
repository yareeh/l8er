package l8er;

import static com.googlecode.totallylazy.Sequences.sequence;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.googlecode.totallylazy.Callable1;
import com.googlecode.totallylazy.Callable2;
import com.googlecode.totallylazy.Predicate;

public class TotallyLazy {

	private static final Predicate<Integer> even = new Predicate<Integer>() {
		@Override public boolean matches(final Integer n) {
			return (n & 1) != 1;
		}
	};

	private static final Predicate<Integer> odd = new Predicate<Integer>() {
		@Override public boolean matches(final Integer n) {
			return !even.matches(n);
		}
	};

	private static final Callable1<Integer, String> toString = new Callable1<Integer, String>() {
		@Override public String call(final Integer n) throws Exception {
			return String.valueOf(n);
		}
	};

	private static final Callable2<Integer, Integer, Integer> sum = new Callable2<Integer, Integer, Integer>() {
		@Override public Integer call(final Integer n, final Integer m) throws Exception {
			return n + m;
		}
	};

	private static final Callable2<Integer, Integer, Integer> add = sum;

	@Test
	public void evenTest() {
		assertFalse(even.matches(7));
		assertTrue(even.matches(14));
		assertTrue(odd.matches(7));
		assertFalse(odd.matches(14));
	}

	@Test
	public void examples() {
		sequence(1, 2, 3, 4).filter(even); // lazily returns 2,4
		sequence(1, 2).map(toString); // lazily returns "1", "2"
		sequence(1, 2).mapConcurrently(toString); // lazily distributes the work to background threads
		sequence(1, 2, 3).take(2); // lazily returns 1,2
		sequence(1, 2, 3).drop(2); // lazily returns 3
		sequence(1, 2, 3).tail(); // lazily returns 2,3
		sequence(1, 2, 3).head(); // eagerly returns 1
		sequence(1, 2, 3).reduce(sum); // eagerly return 6
		sequence(1, 3, 5).find(even); // eagerly returns none()
		sequence(1, 2, 3).contains(2); // eagerly returns true
		sequence(1, 2, 3).exists(even); // eagerly return true
		sequence(1, 2, 3).forAll(odd); // eagerly returns false;
		sequence(1, 2, 3).foldLeft(0, add); // eagerly returns 6
		sequence(1, 2, 3).toString(); // eagerly returns "1,2,3"
		sequence(1, 2, 3).toString(":"); // eagerly returns "1:2:3"

	}
}
