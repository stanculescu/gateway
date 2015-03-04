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

/**
 * 
 */
package org.kaazing.gateway.transport.ws.util;

import static org.kaazing.gateway.util.Utils.asString;

import java.nio.ByteBuffer;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

final class ByteBufferMatching extends BaseMatcher<ByteBuffer> {

    private final String pattern;

    public ByteBufferMatching(String regex) {
        this.pattern = regex;
    }

    @Override
    public boolean matches(Object arg) {
        return (arg instanceof ByteBuffer) && asString((ByteBuffer) arg).matches(pattern);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("matches regular expression ").appendValue(pattern);
    }
}
