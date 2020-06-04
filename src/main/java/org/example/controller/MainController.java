package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.Note;
import org.example.domain.NoteHistory;
import org.example.domain.NoteJSON;
import org.example.domain.User;
import org.example.repos.NotesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class MainController {
    @Autowired
    private NotesRepo notesRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter,
                       @AuthenticationPrincipal User user,
                       Model model) {
        Iterable<Note> notes;

        if (filter != null && !filter.isEmpty()) {
            notes = notesRepo.findByTag(filter);
        } else {
            notes = notesRepo.findAllByAuthor(user);
        }

        model.addAttribute("notes", notes);
        model.addAttribute("filter", filter);
        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag,
            Map<String, Object> model,
            @RequestParam("file") MultipartFile file) throws IOException {
        Note note = new Note(text, tag, user);

        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            note.setFilename(resultFilename);
        }

        notesRepo.save(note);
        Iterable<Note> notes = notesRepo.findAllByAuthor(user);
        model.put("notes", notes);
        return "main";
    }

    @GetMapping("/remove/{id}")
    public String delete(@PathVariable String id, @AuthenticationPrincipal User user,
                         Model model) {
        Note byId = notesRepo.findById(Integer.valueOf(id));
        if (byId != null) {
            notesRepo.delete(byId);
            Iterable<Note> notes = notesRepo.findAllByAuthor(user);
            model.addAttribute("notes", notes);
            return "redirect:/main";
        }
        return "wrongremove";
    }

    @GetMapping("/tojson/{id}")
    public String toJson(@PathVariable String id, @AuthenticationPrincipal User user,
                         Model model) throws JsonProcessingException {
        Note byId = notesRepo.findById(Integer.valueOf(id));
        if (byId != null) {
            model.addAttribute("jsonString", new ObjectMapper().writeValueAsString(byId));
            return "tojson";
        }
        return "wrong";
    }

    @GetMapping("/fromjson")
    public String toJson(@AuthenticationPrincipal User user,
                         Model model) throws JsonProcessingException {
        return "fromjson";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, @AuthenticationPrincipal User user,
                       Model model) {
        Note byId = notesRepo.findById(Integer.valueOf(id));
        model.addAttribute("notes", Collections.singletonList(byId));
        List<NoteHistory> noteHistories = byId.getNoteHistories();
        Collections.reverse(noteHistories);
        model.addAttribute("notesHistory", noteHistories);
        return "edit";
    }

    @PostMapping("/save/{id}")
    public String save(@PathVariable String id,
                       @AuthenticationPrincipal User user,
                       @RequestParam String text,
                       @RequestParam String tag) {
        Note note = notesRepo.findById(Integer.valueOf(id));
        if (note.getAuthor().getUsername().equals(user.getUsername())) {
            note.getNoteHistories().add(new NoteHistory(note));
            note.setTag(tag);
            note.setText(text);
            note.setUpdateDate(new Date());
            notesRepo.save(note);
            return "redirect:/main";
        }
        return "wrongsave";
    }

    @PostMapping("/fromjson")
    public String save(@AuthenticationPrincipal User user,
                       @RequestParam String json) {
        NoteJSON noteJSON = new NoteJSON();
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            noteJSON = mapper.readValue(json, NoteJSON.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Note note =new Note(noteJSON.getText(), noteJSON.getTag(), user);
        notesRepo.save(note);
        return "redirect:/main";
    }
}
