package com.example.ilovetruyen.api;

import com.example.ilovetruyen.dto.ComicCommentDto;
import com.example.ilovetruyen.model.Comment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ComicCommentAPI {
    @GET("api/v1/comments")
    Call<List<Comment>> getAllComment();

    @GET("api/v1/comic-detail/{comicId}/comments")
    Call<List<Comment>> getCommentByComicId(@Path("comicId") int comicId);

    @POST("api/v1/comments")
    Call<Comment> createComment(@Body ComicCommentDto comicCommentDto);

    @PUT("api/v1/comments/{id}")
    Call<Comment> updateComment(@Path("id") int id, @Body ComicCommentDto comicCommentDto);
}
