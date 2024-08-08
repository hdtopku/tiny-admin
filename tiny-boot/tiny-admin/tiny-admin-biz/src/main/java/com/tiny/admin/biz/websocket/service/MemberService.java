package com.tiny.admin.biz.websocket.service;

import com.tiny.admin.biz.websocket.po.User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedHashMap;

@Service
public class MemberService {
    private static LinkedHashMap<String, User> store = new LinkedHashMap<>();

    public Collection<User> getMembers() {
        return store.values();
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
