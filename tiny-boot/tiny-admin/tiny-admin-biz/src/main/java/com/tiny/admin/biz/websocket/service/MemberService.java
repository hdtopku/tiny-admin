package com.tiny.admin.biz.websocket.service;

import com.tiny.admin.biz.config.security.AdminUserDetails;
import jakarta.annotation.Resource;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class MemberService {
    @Resource
    private RedissonClient redissonClient;
    @Value("${constant.redis-users-token-map-key}")
    private String redisUsersTokenMapKey;

    public Collection<String> getMembers() {
        RMapCache<String, AdminUserDetails> userMap = redissonClient.getMapCache(redisUsersTokenMapKey);
        ArrayList<String> userList = new ArrayList<>(userMap.keySet());
        userList.sort((username1, username2)-> {
            AdminUserDetails user1 = userMap.get(username1);
            AdminUserDetails user2 = userMap.get(username2);
            if(user1 == null) return -1;
            if(user2 == null) return 1;
            return Long.compare(user1.getLoginTime(), user2.getLoginTime());
        });
        return userList;
    }

    public void removeMember(String username) {
        RMapCache<String, String> userMap = redissonClient.getMapCache(redisUsersTokenMapKey);
        userMap.remove(username);
    }
}
