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

import org.junit.Test;

public class Java8 {
	private static final Predicate<Integer> even = n -> (n & 1) != 1;
	private static final Predicate<Integer> odd = n -> (n & 1) == 1;
	private static final BinaryOperator<Integer> sum = (n, m) -> n + m;

	@Test
	public void examples() {
		assertThat(asList(1, 2, 3, 4).stream().filter(even).collect(toList()), is(asList(2, 4)));
		assertThat(asList(1, 2).stream().map(String::valueOf).collect(toList()), is(asList("1", "2")));
		assertThat(asList(1, 2).parallelStream().map(String::valueOf).collect(toList()), is(asList("1", "2")));
		assertThat(asList(1, 2, 3).stream().limit(2).collect(toList()), is(asList(1, 2)));
		assertThat(asList(1, 2, 3).stream().skip(2).collect(toList()), is(asList(3)));
		assertThat(asList(1, 2, 3).stream().skip(1).collect(toList()), is(asList(2, 3)));
		assertThat(asList(1, 2, 3).stream().limit(1).collect(toList()), is(asList(1)));
		assertThat(asList(1, 2, 3).stream().reduce(sum).get(), is(6));
		assertThat(asList(1, 3, 5).stream().filter(even).collect(toList()), is(asList()));
		assertTrue(asList(1, 2, 3).stream().anyMatch(n -> n == 2));
		assertTrue(asList(1, 2, 3).stream().anyMatch(even));
		assertFalse(asList(1, 2, 3).stream().allMatch(odd));
		assertThat(asList(1, 2, 3).stream().reduce(0, sum), is(6));
		assertThat(asList(1, 2, 3).stream().map(String::valueOf).collect(joining(",")), is("1,2,3"));
		assertThat(asList(1, 2, 3).stream().map(String::valueOf).collect(joining(":")), is("1:2:3"));
	}
}
