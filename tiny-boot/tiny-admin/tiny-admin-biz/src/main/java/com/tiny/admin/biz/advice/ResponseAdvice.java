package com.tiny.admin.biz.advice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.*;

/**
 * Created by lxh at 2024-10-10 18:38:51
 */
@Slf4j
@SuppressWarnings("all")
@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 通过HttpServletRequest获取到用户当前的语言环境
        ServletRequestAttributes servletRequestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        if (servletRequestAttributes != null) {
            HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();
            // 将响应数据body序列化为JsonNode
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode node = objectMapper.readTree(objectMapper.writeValueAsString(body));
            String language = httpServletRequest.getHeader("language");
            if(!StringUtils.hasText(language)) {
                language = "CN";
            }
            responseDataParseAndRemove(node, language);
            return node;
        }
        return body;
    }


    /**
     * <p> 响应数据解析和指定前缀字段移除</p>
     *
     * @param node 响应数据节点
     * @description: 通过解析Json结构的响应数据、移除指定前缀字段、以达到动态数据在不同语言环境下的动态切换
     **/
    public static void responseDataParseAndRemove(JsonNode node, String language) {
        // 节点只有两种：容器节点和非容器节点
        if (node.isContainerNode()) {
            // 判断该节点是对象还是数组
            if (node.isObject()) {
                // 待移除的字段列表初始化，本列表的作用是暂存所有满足条件待移除的字段
                List<String> toRemoveFieldList = new ArrayList<>();// 如果JsonNode是对象
                ObjectNode objectNode = (ObjectNode) node;
                Iterator<Map.Entry<String, JsonNode>> nodeFieldList = objectNode.fields();
                while (nodeFieldList.hasNext()) {
                    Map.Entry<String, JsonNode> nodeField = nodeFieldList.next();
                    String fieldName = nodeField.getKey();
                    JsonNode fieldValue = nodeField.getValue();
                    // 继续判断字段值是不是一个对象，如果是则递归调用当前方法进行处理;如果是数组则循环递归调用当前方法进行处理
                    if (fieldValue.isObject()) {
                        responseDataParseAndRemove(fieldValue, language);
                    } else if (fieldValue.isArray()) {
                        ArrayNode arrayNode = (ArrayNode) fieldValue;
                        for (JsonNode element : arrayNode) {
                            responseDataParseAndRemove(element, language);
                        }
                    } else {
                        Map<String, String> map = new HashMap<>();
                        map.put("华为 HUAWEI P20 升级版", "aaaabbccdd");
                        map.put("华为 HUAWEI P20 Pro 升级版", "eeffgghhii");
                        if(map.containsKey(fieldValue.asText())) {
                            objectNode.put(fieldName, map.get(fieldValue.asText()));
                        }
                    }
                }
            } else if (node.isArray()) {
                // 如果JsonNode是数组
                ArrayNode arrayNode = (ArrayNode) node;
                for (JsonNode element : arrayNode) {
                    responseDataParseAndRemove(element, language);
                }
            }
        } else {
            // 非容器节点
            log.info("非容器节点，不需要任何处理");
        }
    }
}

