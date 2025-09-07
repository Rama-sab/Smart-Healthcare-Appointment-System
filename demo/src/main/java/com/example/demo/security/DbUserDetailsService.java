package com.example.demo.security;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import java.util.List;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class DbUserDetailsService implements UserDetailsService {
  private final UserRepository users;
  public DbUserDetailsService(UserRepository users) { this.users = users; }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User u = users.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    List<String> roles = List.of(u.getRole().name()); 
    return org.springframework.security.core.userdetails.User.withUsername(u.getUsername())
        .password(u.getPassword())
        .roles(roles.toArray(String[]::new))
        .disabled(!u.isEnabled())
        .build();
  }
}
