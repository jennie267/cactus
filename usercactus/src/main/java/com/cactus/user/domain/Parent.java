package com.cactus.user.domain;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="parent")
public class Parent {

    @Id
    @Column(name = "parent_id")
    @GeneratedValue(strategy = GenerationType. IDENTITY)
    private int parentId;

    @Column(name = "parent_user_id")
    private int parentUserId;

    @Builder
    public Parent(int parentUserId) {
        this.parentUserId = parentUserId;
    }
}
