//package com.tiny.admin.biz.advice;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.ArrayNode;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import com.tiny.admin.biz.utils.LanguageUtil;
//import com.tiny.core.web.Result;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.MethodParameter;
//import org.springframework.http.MediaType;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
//
//import javax.annotation.Resource;
//import java.util.*;
//
///**
// * Created by lxh at 2024-10-10 18:38:51
// */
//@Slf4j
//@SuppressWarnings("all")
//@RestControllerAdvice
//public class ResponseAdvice implements ResponseBodyAdvice<Object> {
//    @Resource
//    private LanguageUtil languageUtil;
//    @Resource
//    private ObjectMapper objectMapper;
//
//    @Override
//    public boolean supports(MethodParameter returnType, Class converterType) {
//        return returnType.getGenericParameterType().getTypeName().startsWith(Result.class.getTypeName());
//    }
//
//    @SneakyThrows
//    @Override
//    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
//        // 通过HttpServletRequest获取到用户当前的语言环境
//        ServletRequestAttributes servletRequestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
//        if (servletRequestAttributes != null) {
//            HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();
//            String stringBody = objectMapper.writeValueAsString(body);
//            JsonNode node = objectMapper.readTree(stringBody);
//            String language = httpServletRequest.getHeader("language");
//            if (!StringUtils.hasText(language)) {
//                language = "CN";
//            }
//            LanguageUtil.setLanguage(language);
//            responseDataParseAndRemove(node, language);
//            return node;
//        }
//        return body;
//    }
//
//
//    /**
//     * <p> 响应数据解析和指定前缀字段移除</p>
//     *
//     * @param node 响应数据节点
//     * @description: 通过解析Json结构的响应数据、移除指定前缀字段、以达到动态数据在不同语言环境下的动态切换
//     **/
//    public void responseDataParseAndRemove(JsonNode node, String language) {
//        HashMap<Integer, ArrayList<Integer>> map=new HashMap<Integer, ArrayList<Integer>>();
//        map.computeIfAbsent(1000, k ->new ArrayList<Integer>()).add(3);
//        if (StringUtils.hasText(languageUtil.getLanguage()) && "notranslation".equals(languageUtil.getLanguage().toLowerCase())) return;
//        // 节点只有两种：容器节点和非容器节点
//        if (node.isContainerNode()) {
//            // 判断该节点是对象还是数组
//            if (node.isObject()) {
//                // 待移除的字段列表初始化，本列表的作用是暂存所有满足条件待移除的字段
//                ObjectNode objectNode = (ObjectNode) node;
//                Iterator<Map.Entry<String, JsonNode>> nodeFieldList = objectNode.fields();
//                while (nodeFieldList.hasNext()) {
//                    Map.Entry<String, JsonNode> nodeField = nodeFieldList.next();
//                    String fieldName = nodeField.getKey();
//                    JsonNode fieldValue = nodeField.getValue();
//                    // 继续判断字段值是不是一个对象，如果是则递归调用当前方法进行处理;如果是数组则循环递归调用当前方法进行处理
//                    if (fieldValue.isObject()) {
//                        responseDataParseAndRemove(fieldValue, language);
//                    } else if (fieldValue.isArray()) {
//                        ArrayNode arrayNode = (ArrayNode) fieldValue;
//                        for (JsonNode element : arrayNode) {
//                            responseDataParseAndRemove(element, language);
//                        }
//                    } else {
//                        if (languageUtil.containsTranslation(fieldValue.asText())) {
//                            String translationText = languageUtil.getTranslation(fieldValue.asText(), language);
//                            if(fieldName.equals("商品管理")) {
//                                System.out.println(fieldValue);
//                            }
//                            if(StringUtils.hasText(translationText)) {
//                                objectNode.put(fieldName, translationText);
//                            }
//                        }
//                    }
//                }
//            } else if (node.isArray()) {
//                // 如果JsonNode是数组
//                ArrayNode arrayNode = (ArrayNode) node;
//                for (JsonNode element : arrayNode) {
//                    responseDataParseAndRemove(element, language);
//                }
//            }
//        }
//    }
//}
//
