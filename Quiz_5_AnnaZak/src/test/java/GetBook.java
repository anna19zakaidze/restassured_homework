import Models.Response.BookList;
import Models.Response.BookModel;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;


public class GetBook {

    @Test
    public void testBook() {

        //Add assertion for status code
        Response response = RestAssured.given().when()
                .get("https://demoqa.com/BookStore/v1/Books");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode,200);

        //Get the first isbn from https://demoqa.com/BookStore/v1/Books
        //Deserialize received response from JSON to Java object
        BookList booksList= response
                .jsonPath()
                .getObject("", BookList.class);
        String firstISBN = booksList.books.get(0).isbn;
        String url =  String.format("https://demoqa.com/BookStore/v1/Book?ISBN=%s", firstISBN);

        //Pass received isbn to https://demoqa.com/BookStore/v1/Book
        BookModel bookModel= RestAssured.given().when()
                .get(url)
                .as(BookModel.class);
        System.out.println(bookModel.isbn);

        // Add assertion for publisher and page properties
        String publisher = bookModel.publisher;
        int pages = bookModel.pages;
        Assert.assertEquals(publisher,"O'Reilly Media");
        Assert.assertEquals(pages,234);
    }

}