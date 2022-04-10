package com.springredispostgres.common.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.Accessors;

@Value
@Builder
@Accessors(chain = true)
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class UserRequestDto {

    private String firstname;

    private String lastname;

    private String email;

    private String password;

}
