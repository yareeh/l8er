package l8er;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.junit.Test;

public class Java8 {
	private static final Predicate<Integer> even = n -> (n & 1) != 1;
	private static final Predicate<Integer> odd = n -> (n & 1) == 1;
	private static final BinaryOperator<Integer> sum = (n, m) -> n + m;

	@Test
	public void examples() {
		assertThat(Stream.of(1, 2, 3, 4).filter(even).collect(toList()), is(asList(2, 4)));
		assertThat(Stream.of(1, 2).map(String::valueOf).collect(toList()), is(asList("1", "2")));
		assertThat(asList(1, 2).parallelStream().map(String::valueOf).collect(toList()), is(asList("1", "2")));
		assertThat(Stream.of(1, 2, 3).limit(2).collect(toList()), is(asList(1, 2)));
		assertThat(Stream.of(1, 2, 3).skip(2).collect(toList()), is(asList(3)));
		assertThat(Stream.of(1, 2, 3).skip(1).collect(toList()), is(asList(2, 3)));
		assertThat(Stream.of(1, 2, 3).limit(1).collect(toList()), is(asList(1)));
		assertThat(Stream.of(1, 2, 3).reduce(sum).get(), is(6));
		assertThat(Stream.of(1, 3, 5).filter(even).collect(toList()), is(asList()));
		assertTrue(Stream.of(1, 2, 3).anyMatch(n -> n == 2));
		assertTrue(Stream.of(1, 2, 3).anyMatch(even));
		assertFalse(Stream.of(1, 2, 3).allMatch(odd));
		assertThat(Stream.of(1, 2, 3).reduce(0, sum), is(6));
		assertThat(Stream.of(1, 2, 3).map(String::valueOf).collect(joining(",")), is("1,2,3"));
		assertThat(Stream.of(1, 2, 3).map(String::valueOf).collect(joining(":")), is("1:2:3"));
	}
}
