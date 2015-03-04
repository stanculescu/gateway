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

package org.kaazing.gateway.transport.nio;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import org.kaazing.gateway.transport.Transport;
import org.kaazing.gateway.transport.TransportFactorySpi;

public final class UdpTransportFactorySpi extends TransportFactorySpi {

    private final Collection<String> UDP_SCHEMES = Collections.singleton("udp");

    @Override
    public String getTransportName() {
        return "udp";
    }

    @Override
    public Collection<String> getSchemeNames() {
        return UDP_SCHEMES;
    }

    @Override
    public Transport newTransport(Map<String, ?> configuration) {
        Properties properties = new Properties();
        properties.putAll(configuration);
        return new UdpTransport(properties);
    }

}
