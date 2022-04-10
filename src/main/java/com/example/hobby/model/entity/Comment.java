package com.example.hobby.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="comments")
public class Comment extends BaseEntity{

    private String content;

    public Comment() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
