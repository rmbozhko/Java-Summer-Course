package edu.summer.java;

import java.util.ArrayList;
import java.util.List;

final public class User {
    private final String    name;
    private final Integer   age;
    private List<Post>      posts;

    public User(String name, Integer age, List<Post> posts) {
        this.name = name;
        this.age = age;
        this.posts = new ArrayList<>();
        for (Post post : posts) {
            this.posts.add(new Post(post.getTitle(), post.getContent(), post.getLikesNumber()));
        }
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public List<Post> getPosts() {
        List<Post> postsClone = new ArrayList<>();
        for (Post post : posts) {
            postsClone.add(new Post(post.getTitle(), post.getContent(), post.getLikesNumber()));
        }
        return postsClone;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Object result = super.clone();
        ((User) result).posts = getPosts();
        return result;
    }

    static class Post {
        private String  title;
        private String  content;
        private int     likesNumber;

        public Post(String title, String content, int likesNumber) {
            this.title = title;
            this.content = content;
            this.likesNumber = likesNumber;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getLikesNumber() {
            return likesNumber;
        }

        public void setLikesNumber(int likesNumber) {
            this.likesNumber = likesNumber;
        }
    }
}
