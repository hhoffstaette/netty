/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.jboss.netty.handler.codec.marshalling;

import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.MarshallingConfiguration;
import org.jboss.marshalling.Unmarshaller;
import org.jboss.netty.channel.Channel;

/**
 * {@link UnmarshallerProvider} implementation which use a {@link ThreadLocal} to store references
 * to {@link Unmarshaller} instances. This may give you some performance boost if you need to unmarshall
 * many small {@link Object}'s.
 * 
 *
 */
public class ThreadLocalUnmarshallingProvider implements UnmarshallerProvider {
    private final ThreadLocal<Unmarshaller> unmarshallers = new ThreadLocal<Unmarshaller>();

    private final MarshallerFactory factory;
    private final MarshallingConfiguration config;
    
    /**
     * Create a new instance of the {@link ThreadLocalUnmarshallingProvider}
     * 
     * @param factory   the {@link MarshallerFactory} to use to create {@link Unmarshaller}'s if needed
     * @param config    the {@link MarshallingConfiguration} to use
     */
    public ThreadLocalUnmarshallingProvider(MarshallerFactory factory, MarshallingConfiguration config) {
        this.factory = factory;
        this.config = config;
    }

    public Unmarshaller getUnmarshaller(Channel channel) throws Exception {
        Unmarshaller unmarshaller = unmarshallers.get();
        if (unmarshaller == null) {
            unmarshaller = factory.createUnmarshaller(config);
            unmarshallers.set(unmarshaller);
        }
        return unmarshaller;
    }
    
}
