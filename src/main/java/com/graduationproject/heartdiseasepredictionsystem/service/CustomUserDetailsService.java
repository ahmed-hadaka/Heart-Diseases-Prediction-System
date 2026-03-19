package com.graduationproject.heartdiseasepredictionsystem.service;

import com.graduationproject.heartdiseasepredictionsystem.exception.UserNotFoundException;
import com.graduationproject.heartdiseasepredictionsystem.model.Person;
import com.graduationproject.heartdiseasepredictionsystem.repository.PersonRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
   @Autowired
   PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = personRepository.findByEmail(username);
        if(person.isEmpty())
            throw new UserNotFoundException("UD: Email or Password not correct!");
        return User.withUsername(username)
                .password(person.get().getPassword())
                .roles(person.get().getRole().getName())
                .build();
    }
}
