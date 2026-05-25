package com.example.demo;

import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;

@Repository
public class MemberRepository {
    private final Map<Long, Member> memberMap = new HashMap<>();

    public void save(Member member) {
        memberMap.put(member.getId(), member);
    }

    public Member findById(Long id) {
        return memberMap.get(id);
    }
}