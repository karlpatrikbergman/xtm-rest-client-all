package com.infinera.metro.dnam.acceptance.test.node;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/*
Attribute object / _AFLAGS_
---------------------------
As specified in ppt document:
    Members:
    A               alias           string
    V               value           string
    N               name            string
    D               longName        string
    H               helpText        string
    P               propertyMask    number
    U               unit            string
    I               minValue        number
    M               maxValue        number
    J               majorType       number
    O               minorType       number
    S               setMessage      string
    E               enumRange [ ]   array
    X               include all of the above

Returned when checking every box in JSONRequestMaker:
{
    "A": "name",
    "V": "tp10g:1:2",
    "N": "equipmentBoardName",
    "D": "Name",
    "H": "The name of the board, for example 'tpmr2500:1:2'\nwhere the first number indicates in which sub-rack\nthe board is present and the second number\nin which slot.\n\n",
    "P": 33,
    "I": 0,
    "R": 0,     WHAT IS THIS? NOT DESCRIBED IN DOCUMENT??
    "M": 0,     WHAT IS THIS? NOT DESCRIBED IN DOCUMENT??
    "J": 4,
    "O": 1
}

Skipped in response for the later case are
    A   alias
    U   unit
    M   maxValue
    S   setMessage
    E   enumRange
*/
@AllArgsConstructor(access = AccessLevel.PUBLIC) //Needed by Orika
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true) //Needed by Hibernate and Jackson
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Value
@Builder
public class AttributeObject {
    private String alias;           //A
    private String value;           //V
    private String name;            //N
    private String longName;        //D
    private String helpText;        //H
    private int propertyMask;       //P
//  private String unit;            //U   Skip for now, see note above
    private int minValue;           //I
    private int maxValue;           //M   Skip for now, see note above
    private int majorType;          //J
    private int minorType;          //O
//  private String setMessage;      //S   Skip for now, see note above
//  private List<Object> enumRange; //E   Skip for now, see note above
}
