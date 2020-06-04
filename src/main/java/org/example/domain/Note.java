package org.example.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Note {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String text;
    private String tag;
    private Date createDate;
    private Date updateDate;

    @OneToMany(fetch= FetchType.EAGER, cascade=CascadeType.ALL, mappedBy = "noteId")
    private List<NoteHistory> noteHistories;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    private String filename;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getAuthorName() {
        return author != null ? author.getUsername() : "<none>";
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Note(String text, String tag, User user) {
        this.text = text;
        this.tag = tag;
        this.author = user;
        this.createDate = new Date();
        noteHistories = new ArrayList<>();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public List<NoteHistory> getNoteHistories() {
        return noteHistories;
    }

    public void setNoteHistories(List<NoteHistory> noteHistories) {
        this.noteHistories = noteHistories;
    }

    public Note() { }
}
