/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package io.opentelemetry.exporter.internal.otlp;

import io.opentelemetry.exporter.internal.otlp.traces.TraceRequestMarshaler;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;

@BenchmarkMode({Mode.AverageTime})
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 10, time = 1)
@Fork(1)
public class RequestMarshalBenchmarks {

  @Benchmark
  @Threads(1)
  public TestOutputStream createCustomMarshal(RequestMarshalState state) {
    TraceRequestMarshaler requestMarshaler = TraceRequestMarshaler.create(state.spanDataList);
    return new TestOutputStream(requestMarshaler.getBinarySerializedSize());
  }

  @Benchmark
  @Threads(1)
  public TestOutputStream marshalCustom(RequestMarshalState state) throws IOException {
    TraceRequestMarshaler requestMarshaler = TraceRequestMarshaler.create(state.spanDataList);
    TestOutputStream customOutput =
        new TestOutputStream(requestMarshaler.getBinarySerializedSize());
    requestMarshaler.writeBinaryTo(customOutput);
    return customOutput;
  }

  @Benchmark
  @Threads(1)
  public TestOutputStream marshalJson(RequestMarshalState state) throws IOException {
    TraceRequestMarshaler requestMarshaler = TraceRequestMarshaler.create(state.spanDataList);
    TestOutputStream customOutput = new TestOutputStream();
    requestMarshaler.writeJsonTo(customOutput);
    return customOutput;
  }
}
