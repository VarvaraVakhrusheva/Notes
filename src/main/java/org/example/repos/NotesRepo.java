package org.example.repos;

import org.example.domain.Note;
import org.example.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotesRepo extends CrudRepository<Note, Long> {
    List<Note> findByTag(String tag);
    Note findById(Integer id);
    List<Note> findAllByAuthor(User author);

}
