package com.nebula.core.types;

import com.nebula.core.NebulaException;

public class Range<T extends Comparable<T>> {

	private final T min;
	private final T max;

	private Range(T min, T max) {
		checkMin(min);
		checkMax(max);
		checkInterval(min, max);
		this.min = min;
		this.max = max;
	}

	public static <T extends Comparable<T>> Range<T> withMinAndMax(T min, T max) {
		return new Range<>(min, max);
	}

	public T getMin() {
		return min;
	}

	public T getMax() {
		return max;
	}

	private void checkInterval(T min, T max) {
		if (min.compareTo(max) > 0) {

			throw new NebulaException("range [" + min + ";" + max + "] is invalid");
		}
	}

	private void checkMax(T max) {
		if (max == null) {

			throw new NebulaException("max is null");
		}
	}

	private void checkMin(T min) {
		if (min == null) {

			throw new NebulaException("min is null");
		}
	}
}
