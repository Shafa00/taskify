package com.taskify.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@ToString
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "organization_id")
    private String organizationId;

    @Column(name = "organization_name")
    private String name;

    @Column(name = "organization_phone_number")
    private String phoneNumber;

    @Column(name = "organization_address")
    private String address;

    @OneToMany(mappedBy = "organization")
    private List<User> users;

    @OneToMany(mappedBy = "organization")
    private List<Task> tasks;
}
