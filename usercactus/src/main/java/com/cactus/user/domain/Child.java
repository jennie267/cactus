package com.cactus.user.domain;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="child")
public class Child {

    @Id
    @Column(name = "child_id")
    @GeneratedValue(strategy = GenerationType. IDENTITY)
    private int childId;

    @Column(name = "child_user_id")
    private int childUserId;

    @Builder
    public Child(int childUserId) {
        this.childUserId = childUserId;
    }
}
