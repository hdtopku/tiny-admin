package com.tiny.admin.biz.websocket.service;

import com.tiny.admin.biz.websocket.po.User;
import jakarta.annotation.Resource;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedHashMap;

@Service
public class MemberService {
    private static LinkedHashMap<String, User> store = new LinkedHashMap<>();
    @Resource
    private RedissonClient redissonClient;
    @Value("${constant.redis-users-token-map-key}")
    private String redisUsersTokenMapKey;
    

    public Collection<Object> getMembers() {
        RMapCache<Object, Object> userMap = redissonClient.getMapCache(redisUsersTokenMapKey);
        return userMap.values();
    }


    public void addMember(User user) {
        store.put(user.id(), user);
    }

    public void removeMember(User user) {
        store.remove(user.id());
    }
    public void removeMember(String userId) {
        store.remove(userId);
    }
}
