package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final UserServiceImp userServiceImp;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, UserServiceImp userServiceImp) {
        this.userService = userService;
        this.roleService = roleService;
        this.userServiceImp = userServiceImp;
    }

    @GetMapping("/")
    public ResponseEntity<User> showAdminInfo (Principal principal) {
        User user = userServiceImp.findByUsername(principal.getName());
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/listUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok().body(userService.findAll());
    }

    @GetMapping("/listRoles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok().body(roleService.getRoles());
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.getUser(id));
    }

    @PostMapping(value = "/addUser", consumes = "application/json", produces = "application/json")
    //@PostMapping("/addUser")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/editUser")
    public ResponseEntity<Void>saveEditUser(@RequestBody User user) {
        userService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{id}")
    public void deleteUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        userService.deleteUser(user);
        ResponseEntity.ok(HttpStatus.OK);
    }
}