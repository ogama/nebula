package com.nebula.core;

import com.nebula.core.types.date.DateTimeTypeBuilder;
import com.nebula.core.types.number.DoubleTypeBuilder;
import com.nebula.core.types.number.LongTypeBuilder;

public final class NebulaTypes {

	public static LongTypeBuilder integer() {
		return new LongTypeBuilder();
	}

	public static DoubleTypeBuilder decimal() {
		return new DoubleTypeBuilder();
	}

	public static DateTimeTypeBuilder dateTime() {
		return new DateTimeTypeBuilder();
	}
}
