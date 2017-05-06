package com.nebula.core.types.number;

import java.math.BigDecimal;

import com.nebula.core.NebulaException;
import com.nebula.core.types.Range;
import com.nebula.core.types.Type;
import com.nebula.core.types.TypeBuilder;

public class NumberTypeBuilder implements TypeBuilder {

	private int precision = 0;
	private BigDecimal min = BigDecimal.valueOf(Long.MIN_VALUE);
	private BigDecimal max = BigDecimal.valueOf(Long.MAX_VALUE);

	@Override
	public Type build() throws NebulaException {
		return new NumberType(new Range<BigDecimal>(min, max), precision);
	}

	public NumberTypeBuilder withPrecision(int value) {
		precision = value;
		return this;
	}

	public NumberTypeBuilder withMin(BigDecimal value) {
		min = value;
		return this;
	}

	public NumberTypeBuilder withMax(BigDecimal value) {
		max = value;
		return this;
	}

}
