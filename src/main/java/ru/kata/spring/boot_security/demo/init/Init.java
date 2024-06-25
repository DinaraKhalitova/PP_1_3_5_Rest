package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashSet;
import java.util.Set;

@Component
public class Init {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private User user1;
    private User user2;

    @Autowired
    public Init(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void init() {
        Role role1 = roleRepository.findByRoleName("ROLE_ADMIN");
        if (role1 == null) {
            role1 = new Role(1, "ROLE_ADMIN");
            role1 = roleRepository.save(role1); // Сохраняем, если роли нет
        }

        Role role2 = roleRepository.findByRoleName("ROLE_USER");
        if (role2 == null) {
            role2 = new Role(2, "ROLE_USER");
            role2 = roleRepository.save(role2); // Сохраняем, если роли нет
        }

        //password (admin = admin, user = user)
        user1 = new User("admin", "$2a$10$pQHAL3nF4iwu6k9jwHeg5u520N66WmVDiu2bK03WdfGvpFF3QNP92", "admin", 1, "admin@mail.ru");
        user2 = new User("user", "$2a$10$ACmxffNNV33Ybg5.g3n3y.cu1QHp6AvmDWHU9TeKGvn1oUf7Grt7K", "user", 1, "user@mail.ru");

        Set<Role> roles1 = new HashSet<>();
        roles1.add(role1);
        roles1.add(role2);

        Set<Role> roles2 = new HashSet<>();
        roles2.add(role2);

        user1.setRoles(roles1);
        user2.setRoles(roles2);

        userRepository.save(user1);
        userRepository.save(user2);
    }

    @PreDestroy
    public void destroy() {
        userRepository.delete(user1);
        userRepository.delete(user2);
    }
}
