package ru.yandex.practicum.filmorate.storage.film;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FindFilmException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Data
@Component
public class InMemoryFilmStorage implements FilmStorage {

    private static Map<Long, Film> films = new HashMap<>();

    @Override
    public void reset() {
        films.clear();
    }

    public boolean containsKey(Long id) {
        return films.containsKey(id);
    }

    @Override
    public Collection<Film> findAll() {
        return films.values();
    }

    @Override
    public Film findById(Long id) throws FindFilmException {

        if (films.containsKey(id)) {
            return films.get(id);
        } else {
            throw new FindFilmException(id);
        }
    }

    @Override
    public Film addFilm(Film film) {
        return films.put(film.getId(), film);
    }

    @Override
    public Film updateFilm(Film film) {

        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            return film;
        } else {
            log.debug("error updateFilm with ID: {}", film);
            throw new FindFilmException(film.getId());
        }
    }

    @Override
    public List<Film> getMostPopular(Integer count) {

        return films.values().stream().sorted(Comparator.comparingInt(f -> -f.getLikes().size()))
                .limit(count).collect(Collectors.toList());
    }
}
