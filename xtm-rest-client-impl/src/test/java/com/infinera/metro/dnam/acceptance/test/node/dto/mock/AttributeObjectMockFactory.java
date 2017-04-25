package com.infinera.metro.dnam.acceptance.test.node.dto.mock;

import com.infinera.metro.dnam.acceptance.test.node.dto.AttributeObject;

import java.util.Arrays;
import java.util.List;

public enum AttributeObjectMockFactory {
    ATTRIBUTE_OBJECT_MOCK_FACTORY;

    public List<AttributeObject> mockAttributeArray() {
        return Arrays.asList(
                mockAttributeObject(),
                mockAttributeObject(),
                mockAttributeObject()
        );
    }

    public AttributeObject mockAttributeObject() {
        return AttributeObject.builder()
                .alias("some alias")            //A
                .value("tp10g:1:2")             //V
                .name("equipmentBoardName")     //N
                .longName("some long name")     //D
                .helpText("Some help text")     //H
                .propertyMask(16)               //P
                .minValue(0)                    //I
                .maxValue(0)                    //M
                .majorType(4)                   //J
                .minorType(0)                   //O
                .build();
    }
}
