package l8er;

import static com.googlecode.totallylazy.Sequences.sequence;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.junit.Test;

import com.googlecode.totallylazy.Callable2;
import com.googlecode.totallylazy.Option;

public class TotallyLazyVsJava8 {
	private static final Predicate<Integer> even = n -> (n & 1) != 1;
	private static final Predicate<Integer> odd = n -> (n & 1) == 1;
	private static final BinaryOperator<Integer> sum = (n, m) -> n + m;

	static final com.googlecode.totallylazy.Predicate<Integer> _even = n -> (n & 1) != 1;
	static final com.googlecode.totallylazy.Predicate<Integer> _odd = n -> !_even.matches(n);
	static final Callable2<Integer, Integer, Integer> _sum = (n, m) -> n + m;
	static final Callable2<Integer, Integer, Integer> _add = _sum;

	@Test
	public void examples() {

		assertArrayEquals(
				sequence(1, 2, 3, 4).filter(_even).toArray(),
				Stream.of(1, 2, 3, 4).filter(even).toArray()
		);

		assertArrayEquals(
				sequence(1, 2).map(String::valueOf).toArray(),
				Stream.of(1, 2).map(String::valueOf).toArray()
		);

		assertArrayEquals(
				sequence(1, 2).mapConcurrently(String::valueOf).toArray(),
				asList(1, 2).parallelStream().map(String::valueOf).toArray()
		);

		assertArrayEquals(
				sequence(1, 2, 3).take(2).toArray(),
				Stream.of(1, 2, 3).limit(2).toArray()
		);

		assertArrayEquals(
				sequence(1, 2, 3).drop(2).toArray(),
				Stream.of(1, 2, 3).skip(2).toArray()
		);

		assertArrayEquals(
				sequence(1, 2, 3).tail().toArray(),
				Stream.of(1, 2, 3).skip(1).toArray()
		);

		assertEquals(
				sequence(1, 2, 3).head(),
				Stream.of(1, 2, 3).findFirst().get()
		);

		assertEquals(
				sequence(1, 2, 3).reduce(_sum),
				Stream.of(1, 2, 3).reduce(sum).get()
		);

		assertEquals(
				sequence(1, 3, 5).find(_even) == Option.<Integer>none(),
				Stream.of(1, 3, 5).filter(even).findFirst() == Optional.<Integer>empty()
		);

		assertEquals(
				sequence(1, 2, 3).contains(2),
				Stream.of(1, 2, 3).anyMatch(n -> n == 2)
		);

		assertEquals(
				sequence(1, 2, 3).exists(_even),
				Stream.of(1, 2, 3).anyMatch(even)
		);

		assertEquals(
				sequence(1, 2, 3).forAll(_odd),
				Stream.of(1, 2, 3).allMatch(odd)
		);

		assertEquals(
				sequence(1, 2, 3).foldLeft(0, _sum),
				Stream.of(1, 2, 3).reduce(0, sum)
		);

		assertEquals(
				sequence(1, 2, 3).toString(),
				Stream.of(1, 2, 3).map(String::valueOf).collect(joining(","))
		);

		assertEquals(
				sequence(1, 2, 3).toString(":"),
				Stream.of(1, 2, 3).map(String::valueOf).collect(joining(":"))
		);
	}
}
