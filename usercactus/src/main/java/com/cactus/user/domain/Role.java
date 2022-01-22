package com.cactus.user.domain;

import lombok.*;

import javax.persistence.*;

/**
 * freight
 * Class: Role
 * Created by hapo on 2019-02-01.
 * Description:
 */

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="role")
public class Role {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType. IDENTITY)
    private int roleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role",  updatable=false)
    protected RoleType role;

    @Builder
    public Role(RoleType role) {
        this.role = role;
    }
}
