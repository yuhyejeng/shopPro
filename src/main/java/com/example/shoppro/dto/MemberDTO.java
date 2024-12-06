package com.example.shoppro.dto;

import com.example.shoppro.constant.Role;
import com.example.shoppro.entity.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@ToString
@Builder                 //밑에 두게 빌더쓰려면 필요
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    //어차피 등록시에는 안씀
    private Long id;
//
    @NotBlank
    @Size(min = 2, max = 10, message = "이름은 2자 이상 10자이하로 작성해주세요")
    private String name;

    @Email(message = "이메일 형식으로 작성해주세요")
    @NotBlank(message = "이메일을 작성해주세요")
    @Size(min = 2, max = 20, message = "이메일은 2자 이상 20자이하로 작성해주세요")
    private String email;

    @NotBlank
    @Size(min = 4, max = 10)
    private String password;

    private String address;

    private Role role;

    public Member dtoToEntity(MemberDTO memberDTO) {  // 디티오를 엔티티로 바꾸는 메소드
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Member member = new Member();
        member.setName(memberDTO.getName());
        member.setEmail(memberDTO.getEmail());
        member.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
        member.setAddress(memberDTO.getAddress());
        member.setRole(Role.ADMIN);

        return member;
    }

}
