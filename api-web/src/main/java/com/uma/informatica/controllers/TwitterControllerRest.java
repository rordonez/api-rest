package com.uma.informatica.controllers;

import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * Created by rafa on 11/06/14.
 */
@Profile({"production"})
@RestController
@RequestMapping(value = "/twitter")
public class TwitterControllerRest {

    private Twitter twitter;

    private ConnectionRepository connectionRepository;

    @Inject
    public TwitterControllerRest(Twitter twitter, ConnectionRepository connectionRepository) {
        this.twitter = twitter;
        this.connectionRepository = connectionRepository;
    }


    @RequestMapping(method = RequestMethod.GET)
    public String twitterConnect(Model model) {
        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
            return "redirect:/connect/twitter";
        }
        return "";
    }

    @RequestMapping(value="/friends", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public CursoredList<TwitterProfile> twitterFriends(){
        CursoredList<TwitterProfile> friends = twitter.friendOperations().getFriends();
        return friends;
    }

}
