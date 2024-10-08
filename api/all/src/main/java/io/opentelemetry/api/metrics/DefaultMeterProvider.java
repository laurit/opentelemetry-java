/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package io.opentelemetry.api.metrics;

import io.opentelemetry.api.internal.IncubatingUtil;

/** A {@link MeterProvider} that does nothing. */
class DefaultMeterProvider implements MeterProvider {
  @Override
  public MeterBuilder meterBuilder(String instrumentationScopeName) {
    return BUILDER_INSTANCE;
  }

  private static final MeterProvider INSTANCE =
      IncubatingUtil.incubatingApiIfAvailable(
          new DefaultMeterProvider(),
          "io.opentelemetry.api.incubator.metrics.ExtendedDefaultMeterProvider");
  private static final MeterBuilder BUILDER_INSTANCE = new NoopMeterBuilder();

  static MeterProvider getInstance() {
    return INSTANCE;
  }

  private DefaultMeterProvider() {}

  private static class NoopMeterBuilder implements MeterBuilder {

    @Override
    public MeterBuilder setSchemaUrl(String schemaUrl) {
      return this;
    }

    @Override
    public MeterBuilder setInstrumentationVersion(String instrumentationScopeVersion) {
      return this;
    }

    @Override
    public Meter build() {
      return DefaultMeter.getInstance();
    }
  }
}
