/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.nifi.processors.sms; 

import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.flowfile.FlowFile;
import org.apache.nifi.annotation.behavior.ReadsAttribute;
import org.apache.nifi.annotation.behavior.ReadsAttributes;
import org.apache.nifi.annotation.behavior.WritesAttribute;  
import org.apache.nifi.annotation.behavior.WritesAttributes;
import org.apache.nifi.annotation.lifecycle.OnScheduled;
import org.apache.nifi.annotation.documentation.CapabilityDescription;
import org.apache.nifi.annotation.documentation.SeeAlso;
import org.apache.nifi.annotation.documentation.Tags;
import org.apache.nifi.processor.exception.ProcessException;
import org.apache.nifi.processor.AbstractProcessor;
import org.apache.nifi.processor.ProcessContext;
import org.apache.nifi.processor.ProcessSession;
import org.apache.nifi.processor.ProcessorInitializationContext;
import org.apache.nifi.processor.Relationship;
import org.apache.nifi.processor.util.StandardValidators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Tags({"example"}) 
@CapabilityDescription("Provide a description")
@SeeAlso({})    
@ReadsAttributes({@ReadsAttribute(attribute="", description="")})   // to read the attribute of the directory. 
@WritesAttributes({@WritesAttribute(attribute="", description="")}) // to write attribute to the directory.
public class MyProcessor extends AbstractProcessor {   // Class myProcessor is derived from abstractprocessor.

    public static final PropertyDescriptor MY_PROPERTY = new PropertyDescriptor     // final is a access specifier which makes it not changeable. 
            .Builder().name("MY_PROPERTY")  // builder makes it to build step by step.    
            .displayName("My property")
            .description("Example Property") 
            .required(true) 
            .addValidator(StandardValidators.NON_EMPTY_VALIDATOR)   
            .build(); 

    public static final Relationship MY_RELATIONSHIP = new Relationship.Builder()   // setting the relationship of the processor. 
            .name("MY_RELATIONSHIP")
            .description("Example relationship") 
            .build(); 

    private List<PropertyDescriptor> descriptors;   // declaring a list of propertydescriptor type.
    private Set<Relationship> relationships;   //  

    @Override       // Inheritance.  
    protected void init(final ProcessorInitializationContext context) {     
        descriptors = new ArrayList<>();    // declaring a arraylist. 
        descriptors.add(MY_PROPERTY); // adding the my property created above to the descriptor created. 
        descriptors = Collections.unmodifiableList(descriptors);        // a 'read-only' type of list is formed. 

        relationships = new HashSet<>(); // decalaring a hashset of the relationship type. 
        relationships.add(MY_RELATIONSHIP);     // adding the my relationship property the hashset created above. 
        relationships = Collections.unmodifiableSet(relationships);                 
    }

    @Override
    public Set<Relationship> getRelationships() {  
        return this.relationships; 
    }

    @Override
    public final List<PropertyDescriptor> getSupportedPropertyDescriptors() {
        return descriptors; 
    }

    @OnScheduled                                                            // This means that the method must be called everytime the processor is scheduled to run. 
    public void onScheduled(final ProcessContext context) {

    }

    // this is where the logic takes place when something comes inside the flow file.
    @Override                       
    public void onTrigger(final ProcessContext context, final ProcessSession session) {         // ontrigger runs everytime the processor performs some sort of work. 
        if ( flowFile == null ) {       
        FlowFile flowFile = session.get();                                                      // session.get will get the session and if the session doesn't exist then will create one. 
            return;
        }
        // TODO implement
        System.out.println("Received a flow file"); 
        session.transfer(flowFile,MY_RELATIONSHIP);                         // The any flows files created since the last commit, or since the beginning if there was never a commit, will be transferred when the session is committed.
    }
}
