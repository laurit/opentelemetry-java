/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package io.opentelemetry.exporter.internal.otlp.metrics;

import io.opentelemetry.exporter.internal.marshal.MarshalerContext;
import io.opentelemetry.exporter.internal.marshal.MarshalerUtil;
import io.opentelemetry.exporter.internal.marshal.Serializer;
import io.opentelemetry.exporter.internal.marshal.StatelessMarshaler;
import io.opentelemetry.proto.metrics.v1.internal.ExponentialHistogram;
import io.opentelemetry.sdk.metrics.data.ExponentialHistogramData;
import java.io.IOException;

final class ExponentialHistogramStatelessMarshaler
    implements StatelessMarshaler<ExponentialHistogramData> {
  static final ExponentialHistogramStatelessMarshaler INSTANCE =
      new ExponentialHistogramStatelessMarshaler();
  private static final Object DATA_POINT_SIZE_CALCULATOR_KEY = new Object();
  private static final Object DATA_POINT_WRITER_KEY = new Object();

  @Override
  public void writeTo(
      Serializer output, ExponentialHistogramData histogram, MarshalerContext context)
      throws IOException {
    output.serializeRepeatedMessage(
        ExponentialHistogram.DATA_POINTS,
        histogram.getPoints(),
        ExponentialHistogramDataPointStatelessMarshaler.INSTANCE,
        context,
        DATA_POINT_WRITER_KEY);
    output.serializeEnum(
        ExponentialHistogram.AGGREGATION_TEMPORALITY,
        MetricsMarshalerUtil.mapToTemporality(histogram.getAggregationTemporality()));
  }

  @Override
  public int getBinarySerializedSize(ExponentialHistogramData histogram, MarshalerContext context) {
    int size = 0;
    size +=
        MarshalerUtil.sizeRepeatedMessage(
            ExponentialHistogram.DATA_POINTS,
            histogram.getPoints(),
            ExponentialHistogramDataPointStatelessMarshaler.INSTANCE,
            context,
            DATA_POINT_SIZE_CALCULATOR_KEY);
    size +=
        MarshalerUtil.sizeEnum(
            ExponentialHistogram.AGGREGATION_TEMPORALITY,
            MetricsMarshalerUtil.mapToTemporality(histogram.getAggregationTemporality()));
    return size;
  }
}