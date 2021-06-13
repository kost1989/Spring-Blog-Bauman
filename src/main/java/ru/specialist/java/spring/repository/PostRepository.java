package ru.specialist.java.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.specialist.java.spring.entity.Post;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByDtCreatedBetween(LocalDateTime from, LocalDateTime to);

    @Query(value = "select p.*\n" +
            "\tfrom post p\n" +
            "\t\tinner join post_tag pt\n" +
            "\t\t\tusing (post_id)\n" +
            "\t\tinner join tag t\n" +
            "\t\t\tusing (tag_id)\n" +
            "\t\twhere t.name ilike ?", nativeQuery = true)
    List<Post> findByTagName(String tagName);

    //    1) Найти посты по названию
    List<Post> findByTitle(String title);

    //    2) Найти посты, содержащие в тексте(content) заданную подстроку (оператор like в SQL)
    List<Post> findByContentContainingIgnoreCaseOrderByDtCreatedDesc(String content);

    //    3) Получить все посты, отсортированные по количеству тегов
    @Query(value = "select p.*\n" +
            "\tfrom post p\n" +
            "\t\tinner join post_tag pt\n" +
            "\t\t\tusing (post_id)\n" +
            "\t\tgroup by p.post_id\n" +
            "\t\torder by count(*) desc", nativeQuery = true)
    List<Post> findSortedByTags();

    //ACID

}
