package ru.yandex.practicum.filmorate.controller;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import javax.validation.Valid;
import java.util.*;
// commit for PR
@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService service;

    @Autowired
    public FilmController(FilmService service) {
        this.service = service;
    }

    @PutMapping("{id}/like/{userId}")
    public void addLike(@PathVariable Long id, @PathVariable Long userId) {
        service.addLike(id, userId);
    }
    @DeleteMapping("{id}/like/{userId}")
    public void removeLike(@PathVariable Long id, @PathVariable Long userId) {
        service.remoteLike(id, userId);
    }
    @GetMapping("/popular")
    public Collection<Film> getPopular(@RequestParam(required = false, defaultValue = "10") Integer count ) {
        return service.getMostPopular(count);
    }

    @GetMapping("{id}")
    public Film getFilm(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {

        service.addFilm(film);

        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        return service.updateFilm(film);
    }

    @GetMapping
    public Collection<Film> findAll() {
        log.info("findAll");
        return service.findAll();
    }

}
