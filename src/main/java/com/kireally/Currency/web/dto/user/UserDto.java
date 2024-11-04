package com.kireally.Currency.web.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kireally.Currency.web.validation.OnCreate;
import com.kireally.Currency.web.validation.OnUpdate;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class UserDto {
    @NotNull(message ="Id must not be null!", groups = OnUpdate.class)
    private Long id;

    @NotNull(message = "Name must not be null!", groups = {OnCreate.class, OnUpdate.class})
    @Length(max =255, message = "Name length must be smaller than 255 symbols!", groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @NotNull(message = "Surname must not be null!", groups = {OnCreate.class, OnUpdate.class})
    @Length(max =255, message = "Surname length must be smaller than 255 symbols!", groups = {OnCreate.class, OnUpdate.class})
    private String surname;

    @NotNull(message = "Nickname must not be null!", groups = {OnCreate.class, OnUpdate.class})
    @Length(max =255, message = "Nickname length must be smaller than 255 symbols!", groups = {OnCreate.class, OnUpdate.class})
    private String nickname;

    @NotNull(message = "Email must not be null!", groups = {OnCreate.class, OnUpdate.class})
    @Email
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password must not be null!", groups = {OnCreate.class, OnUpdate.class} )
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password confirmation must not be null!", groups = {OnCreate.class, OnUpdate.class})
    private String passwordConfirmation;
}
