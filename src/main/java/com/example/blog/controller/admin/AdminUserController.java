package com.example.blog.controller.admin;

import com.example.blog.common.PageResult;
import com.example.blog.common.Result;
import com.example.blog.dto.response.UserDTO;
import com.example.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;

    @GetMapping
    public Result<PageResult<UserDTO>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return Result.success(PageResult.of(userService.getAllUsers(pageable)));
    }

    @GetMapping("/{id}")
    public Result<UserDTO> getUserById(@PathVariable Long id) {
        return Result.success(userService.getUserById(id));
    }

    @PostMapping("/{id}/toggle-status")
    public Result<Void> toggleUserStatus(@PathVariable Long id) {
        userService.toggleUserStatus(id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return Result.success();
    }
}
