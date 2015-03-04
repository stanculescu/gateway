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

package org.kaazing.gateway.transport.http;

import static org.junit.rules.RuleChain.outerRule;

import java.net.URI;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.kaazing.gateway.server.test.GatewayRule;
import org.kaazing.gateway.server.test.config.GatewayConfiguration;
import org.kaazing.gateway.server.test.config.builder.GatewayConfigurationBuilder;
import org.kaazing.robot.junit.annotation.Robotic;
import org.kaazing.robot.junit.rules.RobotRule;

public class ClientAccessPolicyIT {

    private RobotRule robot = new RobotRule();

    private GatewayRule gateway = new GatewayRule() {
        {
            // @formatter:off
            GatewayConfiguration configuration =
                    new GatewayConfigurationBuilder()
            
                        // allow *
                        .service()
                            .accept(URI.create("http://localhost:8001/echo"))
                            .type("echo")
                            .crossOrigin()
                                .allowOrigin("*")
                            .done()
                        .done()
                        
                        // allow specific origin
                        .service()
                            .accept(URI.create("http://localhost:8002/echo"))
                            .type("echo")
                            .crossOrigin()
                                .allowOrigin("http://localhost:8000")
                            .done()
                        .done()
                        
                        // overlapping services on specific origin
                        .service()
                            .accept(URI.create("http://localhost:8003/echo2"))
                            .type("echo")
                            .crossOrigin()
                                .allowOrigin("http://localhost:8000")
                            .done()
                        .done()
                        .service()
                            .accept(URI.create("http://localhost:8003/echo3"))
                            .type("echo")
                            .crossOrigin()
                                .allowOrigin("*")
                            .done()
                        .done()
                        
                        // multiple origins allowed
                        .service()
                            .accept(URI.create("http://localhost:8004/echo"))
                            .type("echo")
                            .crossOrigin()
                                .allowOrigin("http://localhost:8000")
                            .done()
                            .crossOrigin()
                                .allowOrigin("http://localhost:8001")
                            .done()
                            .crossOrigin()
                                .allowOrigin("http://localhost:8002")
                            .done()
                        .done()
                        
                        // no cross-site-constraint
                        .service()
                            .accept(URI.create("http://localhost:8005/echo"))
                            .type("echo")
                        .done()
                        

// TODO: create robot test for multiple accepts 
//                        .service()
//                             .accept(URI.create("ws://localhost:8007/echo"))
//                             .accept(URI.create("ws://localhost:8007/echo2"))
//                             .accept(URI.create("ws://localhost:8007/echo3"))
//                            .type("echo")
//                            .crossOrigin()
//                                .allowOrigin("http://localhost:8000")
//                            .done()
//                        .done()
                            
                        
                    .done();
            // @formatter:on
            init(configuration);
        }
    };

    @Rule
    public TestRule chain = outerRule(robot).around(gateway);

    @Robotic(script = "client.access.policy.resource.path.starred.constraints")
    @Test(timeout = 3000)
    public void clientAccessPolicyStarredAllowOriginTest() throws Exception {
        robot.join();
    }

    @Robotic(script = "client.access.policy.resource.path.specific.origin.constraints")
    @Test(timeout = 3000)
    public void clientAccessPolicySpecificAllowOriginTest() throws Exception {
        robot.join();
    }

    @Robotic(script = "client.access.policy.resource.path.overlapping.constraints")
    @Test(timeout = 3000)
    public void clientAccessPolicyOverlappingAllowOriginTest() throws Exception {
        robot.join();
    }

    //@Ignore("Test is not deterministic because the policies can appear in any order, but keeping it in case we make it deterministic, or get robot feature to improve")
    @Robotic(script = "client.access.policy.multiple.constraints")
    @Test(timeout = 3000)
    public void clientAccessPolicyOverMultipleOriginTest() throws Exception {
        robot.join();
    }

    @Robotic(script = "client.access.policy.no.constraints")
    @Test(timeout = 3000)
    public void clientAccessPolicyNoConstraintTest() throws Exception {
    	robot.join();
    }
    
    @Robotic(script = "client.access.policy.test.cache")
    @Test(timeout = 10000)
    public void clientAccessPolicyTestCache() throws Exception {
        robot.join();
    }
}
