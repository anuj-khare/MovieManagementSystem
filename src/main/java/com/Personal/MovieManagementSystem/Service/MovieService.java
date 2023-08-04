package com.Personal.MovieManagementSystem.Service;

import com.Personal.MovieManagementSystem.Model.Movie;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {
    @Autowired
    Connection connection;
    PreparedStatement addStatement = null;
    PreparedStatement getMovieByNameStatement = null;
    PreparedStatement getMovieByIdStatement  = null;
    PreparedStatement getAllMovieStatement = null;
    PreparedStatement deleteMovieByTitleStatement = null;
    PreparedStatement getTopRatingMoviesBasedOnGenreStatement = null;
    PreparedStatement updateMovieStatement = null;
    @PostConstruct
    public void createTable() {
        String query = "create table if not exists movie(id INT auto_increment primary key, " +
                "title varchar(24) not null,"+
                "genre varchar(25) ,"+
                "rating Double);";
        try(Statement st = connection.createStatement()){
            st.execute(query);
            addStatement = connection.prepareStatement("insert into movie(title,genre,rating) values(?,?,?);");
            getMovieByNameStatement = connection.prepareStatement("select * from movie where name = ?;");
            getMovieByIdStatement = connection.prepareStatement("select * from movie where title = ?;");
            getAllMovieStatement = connection.prepareStatement("select * from movie;");
            deleteMovieByTitleStatement = connection.prepareStatement("delete from movie where title = ?;");
            getTopRatingMoviesBasedOnGenreStatement = connection.prepareStatement("" +
                "select * from movie where genre = ? order by rating desc limit 5");
            updateMovieStatement = connection.prepareStatement("update movie set title = ? ,genre = ? ," +
                    "rating=? where id = ?;");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Movie addMovie(Movie movie){
        try{
            addStatement.setString(1, movie.getTitle());
            addStatement.setString(2, movie.getGenre());
            addStatement.setDouble(3, movie.getRating());
            int status =  addStatement.executeUpdate();

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return movie;
    }


    public Movie getMovie(String Title){
        try{
            getMovieByIdStatement.setString(1,Title);
            ResultSet rs = getMovieByIdStatement.executeQuery();
            Movie movie = null;
            while(rs.next()){
                movie = new Movie(rs.getInt(1),rs.getString(2),rs.getString(3), rs.getDouble(4));
            }
            return movie;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Movie> getAllMovies() {
        List<Movie> movieList = new ArrayList<>();
        try {
            ResultSet rs = getAllMovieStatement.executeQuery();
            while(rs.next()) {
                movieList.add(new Movie(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getDouble(4)));
            }
            return movieList;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return movieList;
    }

    public List<Movie> getTopMoviesInEachGenre(String genre){
        List<Movie> movies = new ArrayList<>();
        try{
            getTopRatingMoviesBasedOnGenreStatement.setString(1,genre);

            ResultSet rs = getTopRatingMoviesBasedOnGenreStatement.executeQuery();
            while(rs.next()){
                movies.add(new Movie(rs.getInt(1),rs.getString(2), rs.getString(3),rs.getDouble(4) ));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return movies;
    }
    public boolean deleteByTitle(String title){
        int status = 0;
        try{
            deleteMovieByTitleStatement.setString(1,title);
            status = deleteMovieByTitleStatement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return status == 1 ? true : false;
    }
    public boolean updateMovie(Movie m1,int id){
        int status = 0;
        try{
            updateMovieStatement.setString(1,m1.getTitle());
            updateMovieStatement.setString(2,m1.getGenre());
            updateMovieStatement.setDouble(3,m1.getRating());
            updateMovieStatement.setInt(4,id);
            status = updateMovieStatement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return status == 1 ? true:false;
    }
}