package com.joseyustiz.kata.cleanarchitecture.user.infrastructure.adapter.secundary;

import com.joseyustiz.kata.cleanarchitecture.user.core.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.Optional;

@Repository
public interface UserSpringRepository extends JpaRepository<UserSpringRepository.UserDto, Long> {

    Optional<UserDto> findByUserName(String userName);

    @Entity
    @Data
    @NoArgsConstructor
    class UserDto {

        @Id
        @GeneratedValue(strategy= GenerationType.AUTO)
        private Long id;

        @Column(nullable=false, unique=true)
        private String userName;

        private String email;

        private String fullName;

        private String password;

        public UserDto(User user) {
            this.userName = user.getUserName().getValue();
            this.email = user.getEmail().getValue();
            this.fullName = user.getFullName().getValue();
            this.password = user.getPassword().getValue();
        }
    }
}
