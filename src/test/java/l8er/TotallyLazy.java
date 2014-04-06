package l8er;

import static com.googlecode.totallylazy.Option.none;
import static com.googlecode.totallylazy.Sequences.sequence;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.googlecode.totallylazy.Callable2;
import com.googlecode.totallylazy.Predicate;

public class TotallyLazy {
	static final Predicate<Integer> even = n -> (n & 1) != 1;
	static final Predicate<Integer> odd = n -> !even.matches(n);
	static final Callable2<Integer, Integer, Integer> sum = (n, m) -> n + m;
	static final Callable2<Integer, Integer, Integer> add = sum;

	@Test
	public void examples() {
		assertThat(sequence(1, 2, 3, 4).filter(even), is(sequence(2, 4)));
		assertThat(sequence(1, 2).map(String::valueOf), is(sequence("1", "2")));
		assertThat(sequence(1, 2).mapConcurrently(String::valueOf), is(sequence("1", "2")));
		assertThat(sequence(1, 2, 3).take(2), is(sequence(1, 2)));
		assertThat(sequence(1, 2, 3).drop(2), is(sequence(3)));
		assertThat(sequence(1, 2, 3).tail(), is(sequence(2, 3)));
		assertThat(sequence(1, 2, 3).head(), is(1));
		assertThat(sequence(1, 2, 3).reduce(sum), is(6));
		assertThat(sequence(1, 3, 5).find(even), is(none()));
		assertTrue(sequence(1, 2, 3).contains(2));
		assertTrue(sequence(1, 2, 3).exists(even));
		assertFalse(sequence(1, 2, 3).forAll(odd));
		assertThat(sequence(1, 2, 3).foldLeft(0, add), is(6));
		assertThat(sequence(1, 2, 3).toString(), is("1,2,3"));
		assertThat(sequence(1, 2, 3).toString(":"), is("1:2:3"));
	}
}
