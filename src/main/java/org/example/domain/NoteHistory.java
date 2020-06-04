package org.example.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class NoteHistory {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;
    private String text;
    private String tag;
    private Date createDate;
    @JoinColumn(name="noteId")
    private Integer noteId;


    private String authorName;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
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

    public NoteHistory(String text, String tag, User user, Date date, Integer noteId) {
        this.text = text;
        this.tag = tag;
        this.authorName = user.getUsername();
        this.createDate = date;
        this.noteId = noteId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public NoteHistory(Note note) {
        this(note.getText(), note.getTag(), note.getAuthor(), note.getUpdateDate() != null ? note.getUpdateDate() : note.getCreateDate(), note.getId());
    }

    public NoteHistory() { }
}
