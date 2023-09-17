package com.example.singlemicroservicewithmultidbservers.authorDb;

import lombok.*;
import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length =30)
    private String name;
}
