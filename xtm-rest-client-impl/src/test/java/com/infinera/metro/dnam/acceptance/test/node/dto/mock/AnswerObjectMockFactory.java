package com.infinera.metro.dnam.acceptance.test.node.dto.mock;

import com.infinera.metro.dnam.acceptance.test.node.dto.AnswerObject;

import static com.infinera.metro.dnam.acceptance.test.node.dto.mock.AttributeObjectMockFactory.ATTRIBUTE_OBJECT_MOCK_FACTORY;

public enum AnswerObjectMockFactory {
    ANSWER_OBJECT_MOCK_FACTORY;

    public AnswerObject mockAnswerObject() {
        return AnswerObject.builder()
                .rFlags("RAISEMGNOQPCYVULTBJK") //R
                .aFlags("AVNDHPUIMJOSE")        //A
                .queryID(0)                     //I
                .success(true)                  //S
//              .error("some error")            //E
                .module("eq")                   //M
                .groupOrTable("board")          //G
                .entry("tp10g:1:2")             //E
                .operation("GET")               //O
                .queryString("_RFLAGS_=RAISEMGNOQPCYVULTBJK&_AFLAGS_=AVNDHPUIMJOSE") //Q
                .attributeObjectList(ATTRIBUTE_OBJECT_MOCK_FACTORY.mockAttributeArray())
                .build();
    }
}
