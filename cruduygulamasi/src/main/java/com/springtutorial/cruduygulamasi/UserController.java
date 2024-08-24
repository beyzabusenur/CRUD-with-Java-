package com.springtutorial.cruduygulamasi;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    // Bu liste, kullanıcı verilerini geçici olarak saklamak için kullanılıyor. Veritabanı yerine, bu örnek projede kullanıcılar liste içinde saklanıyor.
    private List<User> users = new ArrayList<>();

    // Varsayılan kullanıcıları ekleyelim
    public UserController() {
        users.add(new User(1, "Beyza", "beyza@ornek.com", "password123"));
        users.add(new User(2, "Busenur", "busenur@ornek.com", "password123"));
        users.add(new User(3, "Elif", "elif@ornek.com", "password123"));
    }

    // POST: Yeni bir kullanıcı ekle
    @PostMapping
    public String createUser(@RequestBody User newUser) {
        users.add(newUser);
        return "User " + newUser.getName() + " created";
    }

    // PUT: Belirli bir kullanıcıyı güncelle
    @PutMapping("/{id}")
    public String updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        Optional<User> existingUser = users.stream().filter(user -> user.getId() == id).findFirst();

        if (existingUser.isPresent()) {
            existingUser.get().setName(updatedUser.getName());
            existingUser.get().setEmail(updatedUser.getEmail());
            existingUser.get().setPassword(updatedUser.getPassword()); // Şifreyi güncelle
            return "User with id " + id + " updated";
        } else {
            return "User with id " + id + " not found";
        }
    }

    // GET: Tüm kullanıcıları listele
    @GetMapping
    public List<User> getUsers() {
        return users;
    }

    // GET: Belirli bir kullanıcıyı ID ile getir
    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return users.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
    }



    // DELETE: Belirli bir kullanıcıyı sil
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        boolean removed = users.removeIf(user -> user.getId() == id);
        if (removed) {
            return "User with id " + id + " deleted";
        } else {
            return "User with id " + id + " not found";
        }
    }

    // POST: Kullanıcı girişi
    @PostMapping("/login")
    public String login(@RequestBody User loginUser) {
        for (User user : users) {
            if (user.getName().equals(loginUser.getName()) && user.getPassword().equals(loginUser.getPassword())) {
                return "Login successful";
            }
        }
        return "Invalid username or password";
    }
}
