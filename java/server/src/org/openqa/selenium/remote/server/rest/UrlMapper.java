/*
Copyright 2007-2009 Selenium committers

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package org.openqa.selenium.remote.server.rest;

import org.openqa.selenium.remote.server.DriverSessions;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;

public class UrlMapper {

  private final Set<ResultConfig> configs = new LinkedHashSet<ResultConfig>();
  private final DriverSessions sessions;
  private final Logger log;

  public UrlMapper(DriverSessions sessions, Logger log) {
    this.sessions = sessions;
    this.log = log;
  }

  public void bind(String url, Class<? extends RestishHandler<?>> handlerClazz) {
    ResultConfig existingConfig = getConfig(url);
    if (existingConfig != null) {
      configs.remove(existingConfig);
    }

    ResultConfig config = new ResultConfig(
        url, handlerClazz, sessions, log);
    configs.add(config);
  }

  public ResultConfig getConfig(String url) {
    for (ResultConfig config : configs) {
      if (config.isFor(url)) {
        return config;
      }
    }

    return null;
  }
}
