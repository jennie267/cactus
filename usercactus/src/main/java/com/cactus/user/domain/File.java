package com.cactus.user.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="file")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    int fileId;

    @Column(name = "name")
    String name;

    @Column(name = "ext")
    String ext;

    @Column(name = "table_name")
    String table_name;

    @Column(name = "table_id")
    int table_id;

    @Column(name = "file_url")
    String file_url;

    @Builder
    public File(String name, String ext, String table_name, int table_id, String file_url) {
        this.name = name;
        this.ext = ext;
        this.table_name = table_name;
        this.table_id = table_id;
        this.file_url = file_url;
    }
}
