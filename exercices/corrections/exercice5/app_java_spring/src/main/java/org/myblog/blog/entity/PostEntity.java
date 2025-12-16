package org.myblog.blog.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    private String description;

    private String content;

    @OneToMany(mappedBy = "postEntity", cascade = CascadeType.ALL)
    private List<CommentEntity> commentEntityList = new ArrayList<>();
}
