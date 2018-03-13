package com.nebula.benchmark.types.list;

import com.nebula.core.Model;
import com.nebula.core.ModelBuilder;
import com.nebula.core.Entity;
import com.nebula.core.types.NebulaTypes;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static com.nebula.core.types.NebulaTypes.number;
import static com.nebula.core.generators.NebulaGenerators.random;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(time = 1, iterations = 1)
@Measurement(time = 3, iterations = 3)
@Threads(1)
@Fork(1)
public class ListTypeOfTypeBenchmark {

	private AtomicLong index;
	private Model model;
	private Entity entity;

	@Benchmark
	public void benchmark_list_of_number_with_size_1(Blackhole blackhole) {
		blackhole.consume(model.generateEntityObject(entity, index.getAndIncrement(), 0L));
	}

	@Setup(Level.Iteration)
	public void setup() {
		model = ModelBuilder.newModel().build();
		entity = model.newEntity("test", 10000000);
		entity.addProperty("property", random(),
				NebulaTypes.list().of(random(), number().range()));
		index = new AtomicLong(0L);
	}
}
