/*
 * Copyright [2007] [University Corporation for Advanced Internet Development, Inc.]
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

package org.opensaml.ws.message.encoder;

import org.apache.log4j.Logger;
import org.opensaml.log.Level;
import org.opensaml.ws.message.MessageContext;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.util.XMLHelper;
import org.w3c.dom.Element;

/**
 * Base class for message decoders.
 */
public abstract class BaseMessageEncoder implements MessageEncoder {

    /** Class logger. */
    private Logger log = Logger.getLogger(BaseMessageEncoder.class);
    
    /** Constructor. */
    public BaseMessageEncoder(){
        
    }

    /** {@inheritDoc} */
    public void encode(MessageContext messageContext) throws MessageEncodingException {
        if (log.isDebugEnabled()) {
            log.debug("Beginning encode message to outbound transport of type: "
                    + messageContext.getMessageOutTransport().getClass().getName());
        }

        doEncode(messageContext);

        if (log.isDebugEnabled()) {
            log.debug("Successfully encoded message.");
        }
    }

    /**
     * Encodes the outbound message onto the outbound transport.
     * 
     * @param messageContext current message context
     * 
     * @throws MessageEncodingException thrown if there is a problem encoding the message
     */
    protected abstract void doEncode(MessageContext messageContext) throws MessageEncodingException;

    /**
     * Helper method that marshalls the given message.
     * 
     * @param message message the marshall and serialize
     * 
     * @return marshalled message
     * 
     * @throws MessageEncodingException thrown if the give message can not be marshalled into its DOM representation
     */
    protected Element marshallMessage(XMLObject message) throws MessageEncodingException {
        if (log.isDebugEnabled()) {
            log.debug("Marshalling message");
        }

        try {
            Marshaller marshaller = Configuration.getMarshallerFactory().getMarshaller(message);
            if (marshaller == null) {
                log.error("Unable to marshall message, no marshaller registered for message object: "
                        + message.getElementQName());
            }
            Element messageElem = marshaller.marshall(message);
            if (log.isEnabledFor(Level.TRAIL)) {
                log.log(Level.TRAIL, "Marshalled message into DOM:\n" + XMLHelper.nodeToString(messageElem));
            }
            return messageElem;
        } catch (MarshallingException e) {
            log.error("Encountered error marshalling message to its DOM representation", e);
            throw new MessageEncodingException("Encountered error marshalling message into its DOM representation", e);
        }
    }
}