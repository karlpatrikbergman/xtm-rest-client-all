//package com.infinera.metro.acceptance.test.node.dto;//package com.infinera.metro.acceptance.test.node.dto;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@JsonIgnoreProperties(ignoreUnknown = true)
//public class AttributeObjects {
//    private final Map<String,AttributeObject> attributeObjectMap;
//
//    public AttributeObjects(List<AttributeObject> attributeObjectList) {
//        this.attributeObjectMap = attributeObjectList.stream()
//                .collect(Collectors.toMap(x -> x.getName(), x -> x));
//    }
//
//    public Map<String, AttributeObject> getAttributeObjectMap() {
//        return attributeObjectMap;
//    }
//
//    public Optional<AttributeObject> getAttributeObject(String key) {
//        AttributeObject value = getAttributeObjectMap().get(key);
//        return value == null ? Optional.empty() : Optional.of(value);
//    }
//}
