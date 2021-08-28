package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UsersService;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/admin")
public class AdminsController {
    private final UsersService usersService;
    private final RoleService roleService;

    @Autowired
    public AdminsController(UsersService usersService,
                            RoleService roleService) {
        this.usersService = usersService;
        this.roleService = roleService;
    }

    @GetMapping
    public String getAllUser(Model model) {
        model.addAttribute("users", usersService.readAllUsers());
        return "admins/admin";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "admins/new";
    }

    @PostMapping
    public String createUser(@ModelAttribute @Valid User user,
                             @RequestParam(required = false) String[] role) {

        Set<Role> roleSet = new HashSet<>();
        if (role != null) {
            roleSet = Arrays.stream(role).map(roleService::getRoleByName).collect(Collectors.toSet());
        }

        user.setRoles(roleSet);
        usersService.creatUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", usersService.readUser(id));
        return "admins/edit";
    }

    @PutMapping()
    public String updateUser(@ModelAttribute @Valid User user,
                             @RequestParam(required = false) String[] role) {

        Set<Role> roleSet = new HashSet<>();
        if (role != null) {
            roleSet = Arrays.stream(role).map(x -> roleService.getRoleByName(x)).collect(Collectors.toSet());
        }

        user.setRoles(roleSet);
        usersService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public  String deleteUser(@PathVariable("id") int id) {
        usersService.deleteUser(id);
        return "redirect:/admin";
    }

}
