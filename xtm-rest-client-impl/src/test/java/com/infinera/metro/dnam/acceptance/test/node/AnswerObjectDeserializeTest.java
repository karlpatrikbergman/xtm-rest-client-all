package com.infinera.metro.dnam.acceptance.test.node;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectReader;
import com.infinera.metro.dnam.acceptance.test.util.ResourceString;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

@Slf4j
public class AnswerObjectDeserializeTest {

    /**
     * From javadoc about ObjectReader:
     * Builder object that can be used for per-serialization configuration of deserialization parameters, such as root
     * type to use or object to update (instead of constructing new instance).
     * Uses "mutant factory" pattern so that instances are immutable (and thus fully thread-safe with no external
     * synchronization); new instances are constructed for different configurations. Instances are initially
     * constructed by ObjectMapper and can be reused, shared, cached; both because of thread-safety and because
     * instances are relatively light-weight.
     */

    @Test //From JSON to Java pojo == Deserialize
    public void deserializeAnswerObject() throws IOException {
        String answerObjectJsonString = new ResourceString("deserializing/answer-object.json").toString();
        assertNotNull(answerObjectJsonString);

        ObjectReader reader = JacksonUtil.INSTANCE.getReader().forType(AnswerObject.class);
        AnswerObject answerObject = reader.readValue(answerObjectJsonString);
        assertNotNull(answerObject);
        assertTrue(answerObject.isSuccess());
        assertTrue(answerObject.getError().equals(""));
        assertEquals("tp10g:1:2", answerObject.getEntry());
        assertEquals("eq", answerObject.getModule());
        assertEquals("board", answerObject.getGroupOrTable());

        log.info("{}", answerObject);
    }

    @Test //From JSON to Java pojo == Deserialize
    public void deserializeAnswerObjectLackingRflagsField() throws IOException {
        String answerObjectJsonString = new ResourceString("deserializing/answer-object-lacking-rflags-field.json").toString();
        assertNotNull(answerObjectJsonString);

        ObjectReader reader = JacksonUtil.INSTANCE.getReader().forType(AnswerObject.class);
        AnswerObject answerObject = reader.readValue(answerObjectJsonString);
        assertNotNull(answerObject);

        log.info("{}", answerObject);
    }

    @Test
    public void deserializeAnswerObjectWhenEntryNotFound() throws IOException {
        String answerObjectJsonString = new ResourceString("deserializing/entry-not-found-response.json").toString();
        assertNotNull(answerObjectJsonString);

        ObjectReader reader = JacksonUtil.INSTANCE.getReader().forType(AnswerObject.class);
        AnswerObject answerObject = reader.readValue(answerObjectJsonString);
        assertNotNull(answerObject);

        log.info("{}", answerObject);
    }


    @Test //From JSON to Java pojo == Deserialize
    public void deserializeAnswerObjectArray() throws IOException {
        String answerObjectJsonString = new ResourceString("deserializing/answer-object-array.json").toString();
        assertNotNull(answerObjectJsonString);

        ObjectReader reader = JacksonUtil.INSTANCE.getReader().forType(new TypeReference<List<AnswerObject>>(){});
        List<AnswerObject> answerObjects = reader.readValue(answerObjectJsonString);
        assertNotNull(answerObjects);

        answerObjects.forEach(answerObject -> log.info("{}\n", answerObject));
    }
}
