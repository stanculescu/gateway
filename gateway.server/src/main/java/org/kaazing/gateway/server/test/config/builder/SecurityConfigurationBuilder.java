/**
 * Copyright (c) 2007-2014 Kaazing Corporation. All rights reserved.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.kaazing.gateway.server.test.config.builder;

import java.util.Set;
import org.kaazing.gateway.server.test.config.SecurityConfiguration;
import org.kaazing.gateway.server.test.config.SuppressibleConfiguration.Suppression;

public class SecurityConfigurationBuilder extends AbstractSecurityConfigurationBuilder<SecurityConfiguration> {

    public SecurityConfigurationBuilder(Set<Suppression> suppressions) {
        this(new SecurityConfiguration(), suppressions);
    }

    @Override
    public AddRealmBuilder<SecurityConfigurationBuilder> realm() {
        return new AddRealmBuilder<SecurityConfigurationBuilder>(this, getCurrentSuppressions());
    }

    private SecurityConfigurationBuilder(SecurityConfiguration configuration, Set<Suppression> suppressions) {
        super(configuration, configuration, suppressions);
    }

}
