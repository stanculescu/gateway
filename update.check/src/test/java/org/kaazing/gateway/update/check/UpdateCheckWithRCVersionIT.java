/**
 * Copyright 2007-2016, Kaazing Corporation. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kaazing.gateway.update.check;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.kaazing.gateway.util.InternalSystemProperty.UPDATE_CHECK;
import static org.kaazing.gateway.util.InternalSystemProperty.UPDATE_CHECK_SERVICE_URL;
import static org.kaazing.test.util.ITUtil.timeoutRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runners.model.Statement;
import org.kaazing.gateway.server.impl.VersionUtils;
import org.kaazing.gateway.server.test.GatewayRule;
import org.kaazing.gateway.server.test.config.GatewayConfiguration;
import org.kaazing.gateway.server.test.config.builder.GatewayConfigurationBuilder;
import org.kaazing.k3po.junit.annotation.Specification;
import org.kaazing.k3po.junit.rules.K3poRule;
import org.kaazing.test.util.MemoryAppender;
import org.kaazing.test.util.MethodExecutionTrace;

public class UpdateCheckWithRCVersionIT {

    public GatewayRule gateway = new GatewayRule() {
        {
            GatewayConfiguration configuration = createGatewayConfiguration();
            //The product information from the server JAR will be overridden by the call to reset
            VersionUtils.reset("Gateway", "Kaazing WebSocket Gateway", "5.6.1-RC001", "");
            init(configuration);
        }
    };
    private K3poRule k3po = new K3poRule();
    private List<String> expectedPatterns = new ArrayList();
    private List<String> forbiddenPatterns = new ArrayList();
    private TestRule checkLogMessageRule = (base, description) -> new Statement() {
        @Override
        public void evaluate() throws Throwable {
            base.evaluate();
            MemoryAppender.assertMessagesLogged(expectedPatterns, forbiddenPatterns, null, true);
        }
    };

    @Rule
    public final TestRule chain = RuleChain.outerRule(timeoutRule(5, SECONDS))
            .around(new MethodExecutionTrace()).around(checkLogMessageRule).around(k3po).around(gateway);

    protected GatewayConfiguration createGatewayConfiguration() {
        GatewayConfiguration configuration = new GatewayConfigurationBuilder()
                .property(UPDATE_CHECK.getPropertyName(), "true")
                .property(UPDATE_CHECK_SERVICE_URL.getPropertyName(), "http://localhost:8080")
                .done();
        return configuration;
    }

    @Specification("shouldNotifyOnUpdateCheckWithLatestVersion")
    @Test
    public void shouldNotifyOnUpdateCheckWithLatestVersion() throws Exception {
        k3po.finish();
        expectedPatterns = Arrays.asList(
                "Update Check: New release available for download: Kaazing (WebSocket )?Gateway 5.6.1 \\(you are currently running (\\d+).(\\d+).(\\d+)(\\-RC(\\d+))?()\\)"

        );
    }

    @Specification("shouldNotNotifyOnUpdateCheckWithLowerVersion")
    @Test
    public void shouldNotNotifyOnUpdateCheckWithLowerVersion() throws Exception {
        k3po.finish();
        forbiddenPatterns = Arrays.asList(
                "Update Check: New release available for download: Kaazing (WebSocket )?Gateway 5.6.0 \\(you are currently running (\\d+).(\\d+).(\\d+)(\\-RC(\\d+))?()\\)"

        );
    }
}
