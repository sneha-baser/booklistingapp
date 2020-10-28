package com.example.booklisting;

public class Book {

    /**
     * Title of the book
     */
    private String book_title;

    /**
     * List of Authors for a book, authors can be more than one
     */
    private String authorList;

    /**
     * Url for information and buying options of the book
     */
    private String mUrl;

    /**
     * Constructs a new {@link Book} object.
     *
     * @param book_title title of the book
     * @param authorList List of authors of the book, authors of a book can be multiple
     */
    public Book(String book_title, String authorList, String mUrl) {
        this.book_title = book_title;
        this.authorList = authorList;
        this.mUrl = mUrl;
    }

    /**
     * Returns the title of the book
     *
     * @return book_title
     */
    public String getBook_title() {
        return book_title;
    }

    /**
     * Returns the list of authors in the Comma Separated String object of the book
     *
     * @return list_price
     */
    public String getAuthorList() {
        return authorList;
    }

    /**
     * Returns the Url for information and buying options of the book
     *
     * @return mUrl
     */
    public String getmUrl() {
        return mUrl;
    }
}

