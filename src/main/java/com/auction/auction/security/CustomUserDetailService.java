package com.auction.auction.security;

import com.auction.auction.member.entity.Member;
import com.auction.auction.member.repository.MemberRepository;
import groovyjarjarantlr4.v4.codegen.model.chunk.ListLabelRef;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> _member = memberRepository.findByUsername(username);

        if(_member.isEmpty()){
            throw new UsernameNotFoundException("사용자  찾응 수 없습니다.");
        }
        Member member = _member.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("member"));

        return new User(member.getUsername(), member.getPassword(),authorities);
    }
}
