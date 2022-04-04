/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package io.opentelemetry.api.metrics;

import java.util.function.Consumer;

/**
 * A reference to an observable metric registered with {@link
 * LongCounterBuilder#buildWithCallback(Consumer)}.
 */
public interface ObservableLongCounter extends AutoCloseable {

  /**
   * Remove the callback registered via {@link LongCounterBuilder#buildWithCallback(Consumer)}.
   * After this is called, the callback won't be invoked on future collections. Subsequent calls to
   * {@link #close()} have no effect.
   *
   * <p>Note: other callbacks registered to the metric with the same identity are unaffected.
   */
  @Override
  default void close() {}
}