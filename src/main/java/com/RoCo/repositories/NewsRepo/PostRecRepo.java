package com.RoCo.repositories.NewsRepo;

import com.RoCo.entities.NewsEnt.PostRec;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRecRepo extends CrudRepository <com.RoCo.entities.NewsEnt.PostRec, Long>{
//    Optional<PostRec> findAllById(Long );
    Iterable<PostRec> findAllByAuthor(String author);
}
