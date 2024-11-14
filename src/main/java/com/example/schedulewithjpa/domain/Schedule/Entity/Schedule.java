package com.example.schedulewithjpa.domain.Schedule.Entity;

import com.example.schedulewithjpa.domain.User.Entity.User;
import com.example.schedulewithjpa.domain.base.Entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "schedule")
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String contents;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Schedule() {

    }

    public Schedule(String title, String contents, User user) {
        this.title = title;

        this.contents = contents;

        this.user = user;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContents(String contents) {
        this.contents = contents;
    }
}
