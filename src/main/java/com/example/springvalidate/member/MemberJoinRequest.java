package com.example.springvalidate.member;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Getter @Setter
public class MemberJoinRequest {

    @NotBlank(message = "{custom.notblank} [{field.name}]")
    @Size(max = 10)
    private String name;

    @NotBlank @Email @Size(max=5)
    private String email;

    @NotBlank(message = "{custom.notblank}") @Size(min = 0, max = 11)
    private String phoneNumber;

    @NotBlank
    private String address;

}
