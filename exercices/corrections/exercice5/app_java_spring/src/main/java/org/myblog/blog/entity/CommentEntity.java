package org.myblog.blog.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CommentEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String email;

    private String content;


    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity postEntity;

}


