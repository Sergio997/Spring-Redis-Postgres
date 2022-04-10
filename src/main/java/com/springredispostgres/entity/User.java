package com.springredispostgres.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tbl_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class User extends AbstractEntity {

    @EqualsAndHashCode.Include
    @ToString.Include
    @Column(nullable = false)
    private String firstname;

    @EqualsAndHashCode.Include
    @ToString.Include
    @Column(nullable = false)
    private String lastname;

    @EqualsAndHashCode.Include
    @ToString.Include
    @Column(nullable = false)
    private String email;

    @EqualsAndHashCode.Include
    @ToString.Include
    @Column(nullable = false)
    private String password;

    @EqualsAndHashCode.Include
    @ToString.Include
    private Boolean enabled;
}
