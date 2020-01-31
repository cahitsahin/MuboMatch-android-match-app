package com.example.mubomatch.Library.LibraryBook;

public class LibraryBookObject {

        private String title;
        private String id;
        private String ISBN;
        private String authors;
        private String image;
        private String publishedDate;
        private String categories;
        private String publisher;

        public LibraryBookObject(String title, String id, String ISBN, String authors, String image, String publishedDate, String categories, String publisher) {
            this.title = title;
            this.id = id;
            this.ISBN = ISBN;
            this.authors = authors;
            this.image = image;
            this.publishedDate = publishedDate;
            this.categories = categories;
            this.publisher = publisher;

        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getISBN() {
            return ISBN;
        }

        public void setISBN(String ISBN) {
            this.ISBN = ISBN;
        }

        public String getAuthors() {
            return authors;
        }

        public void setAuthors(String authors) {
            this.authors = authors;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getPublishedDate() {
            return publishedDate;
        }

        public void setPublishedDate(String publishedDate) {
            this.publishedDate = publishedDate;
        }

        public String getCategories() {
            return categories;
        }

        public void setCategories(String categories) {
            this.categories = categories;

        }

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

