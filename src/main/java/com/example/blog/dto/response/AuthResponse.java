package com.example.blog.dto.response;

import com.example.blog.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {

    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Long expiresIn;
    private UserInfo user;

    @Data
    @Builder
    public static class UserInfo {
        private Long id;
        private String username;
        private String email;
        private String nickname;
        private String avatar;
        private String role;

        public static UserInfo fromUser(User user) {
            return UserInfo.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .nickname(user.getNickname())
                    .avatar(user.getAvatar())
                    .role(user.getRole().name())
                    .build();
        }
    }
}
