package com.codesquad.issue04.domain.entity;

import java.util.List;

import lombok.*;

import javax.persistence.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String color;
    private String description;

    @ManyToMany(mappedBy = "label", cascade = CascadeType.ALL)
    private List<Issue> issues;
}
