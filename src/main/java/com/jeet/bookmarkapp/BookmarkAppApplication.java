package com.jeet.bookmarkapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;

@SpringBootApplication
public class BookmarkAppApplication {


    public static void main(String[] args) {

        SpringApplication.run(BookmarkAppApplication.class, args);

        System.out.println("SERVER STARTED");



    }




}
