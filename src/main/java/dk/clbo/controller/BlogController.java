package dk.clbo.controller;

import dk.clbo.model.Post;
import dk.clbo.repository.PostRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class BlogController {

    PostRepository postRepository;

    public BlogController(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    // HTTP protokol metode get
    @GetMapping("/post")
    public Iterable<Post> index(){
        return postRepository.findAll();
    }

    // Read One
    @GetMapping("/post/{id}")
    public ResponseEntity<Optional<Post>> readOne(@PathVariable Long id){
        Optional<Post> res = postRepository.findById(id);
        if(res.isPresent()){
            return ResponseEntity.status(200).body(res);
        } else {
            return ResponseEntity.status(404).body(res); // TODO send
        }
    }

    // Post
    @CrossOrigin(origins = "*", exposedHeaders = "Location")
    @PostMapping("/post")
    public ResponseEntity<String> create(@ModelAttribute Post p){
        Post post = postRepository.save(p);
        return ResponseEntity.status(201).header("Location", "/post/" + post.getId()).body("{'Msg': 'post created'}");
    }

    // Update

    @PutMapping("/post")
    public ResponseEntity<String> update(@ModelAttribute Post p){
        postRepository.save(p);
        return ResponseEntity.status(204).body("{'msg':'Hello'}");
    }

    // Delete

    @DeleteMapping("/post/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        postRepository.deleteById(id);
        return ResponseEntity.status(200).body("{'msg':'Deleted'}");
    }




}
