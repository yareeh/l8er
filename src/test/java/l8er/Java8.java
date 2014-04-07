package l8er;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Map;
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
		assertFalse(Stream.of(1, 2, 3).noneMatch(odd));
		assertThat(Stream.of(1, 2, 3).reduce(0, sum), is(6));
		assertThat(Stream.of(1, 2, 3).map(String::valueOf).collect(joining(",")), is("1,2,3"));
		assertThat(Stream.of(1, 2, 3).map(String::valueOf).collect(joining(":")), is("1:2:3"));
		final Map<Integer, Integer> map = Stream.of(asList(1, 2), asList(3, 4))
				.collect(toMap(a -> a.get(0), a -> a.get(1)));
		assertThat(map.keySet(), hasItems(1, 3));
		assertThat(map.values(), hasItems(2, 4));
		assertThat(Stream.of(asList(1, 2), asList(3, 4)).flatMap(a -> a.stream()).collect(toList()), is(asList(1, 2, 3, 4)));
		assertThat(Stream.iterate(1, n -> n + 1).limit(3).collect(toList()), is(asList(1, 2, 3)));
		assertThat(Stream.generate(() -> "car").limit(3).collect(toList()), is(asList("car", "car", "car")));
	}

	@Test
	public void blogExamples() {
		Stream.of(1, 2, 3, 4).filter(even); // returns 2, 4
		Stream.of(1, 2).map(String::valueOf); // returns "1", "2"
		asList(1, 2).parallelStream().map(String::valueOf); // execute in parallel
		Stream.of(1, 2, 3).limit(2); // returns 1, 2
		Stream.of(1, 2, 3).skip(2); // returns 3
		Stream.of(1, 2, 3).skip(1); // returns 2, 3
		Stream.of(1, 2, 3).findFirst(); // returns 1
		Stream.of(1, 2, 3).reduce(sum); // returns 6
		Stream.of(1, 3, 5).filter(even).findFirst(); // returns Option.none()
		Stream.of(1, 2, 3).anyMatch(n -> n == 2); // returns true
		Stream.of(1, 2, 3).anyMatch(even); // returns true
		Stream.of(1, 2, 3).allMatch(odd); // returns false
		Stream.of(1, 2, 3).noneMatch(odd); // returns false
		Stream.of(1, 2, 3).reduce(0, sum); // returns 6
		Stream.of(1, 2, 3).map(String::valueOf).collect(joining(",")); // returns "1,2,3"
		Stream.of(1, 2, 3).map(String::valueOf).collect(joining(":")); // returns "1:2:3"
		Stream.of(asList(1, 2), asList(3, 4)).collect(toMap(a -> a.get(0), a -> a.get(1))); // returns map where 1 -> 2, 3 -> 4
		Stream.of(asList(1, 2), asList(3, 4)).flatMap(a -> a.stream()); // returns 1, 2, 3, 4
		Stream.iterate(1, n -> n + 1); // returns 1, 2, 3, ...
		Stream.generate(() -> "car"); // lazily returns an infinite sequence of "car"s
	}
}
