package com.example.test_app.json_util;

import jdk.nashorn.internal.ir.ObjectNode;

import java.io.IOException;
import java.util.*;

public class JSONUtil {

    /**
     * Получение списка идентификаторов сигналов по результатам парсинга данных в формате JSON
     *
     * @param requestBody тело запроса в формате JSON
     * @return ассоциативный массив, отражающий тело запроса произвольного уровня вложенности
     */
    public Map<String, Object> parseRequestBody(String requestBody) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode requestBodyNode = mapper.readTree(requestBody);
        Map<String, Object> resultMap = new HashMap();

        resultMap = getDataFromNode(requestBodyNode, resultMap);

        return resultMap;
    }

    private Map<String, Object> getDataFromNode(JsonNode requestBodyNode, Map<String, Object> resultMap) {
        ObjectNode objectNode = (ObjectNode) requestBodyNode;
        Iterator<Map.Entry<String, JsonNode>> iterator = objectNode.fields();
        String currentKey = "";
        while (iterator.hasNext()) {
            Map.Entry<String, JsonNode> entry = iterator.next();
            if (requestBodyNode.isObject()) {
                currentKey = entry.getKey();
                getDataFromNode(entry.getValue(), resultMap);
            }
            ValueNode valueNode = (ValueNode) requestBodyNode;
            resultMap.put(currentKey, valueNode.asText());
        }
        return resultMap;
    }

}


