package com.uma.informatica.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

/**
 * Created by rafa on 11/06/14.
 */
@Profile({"production"})
@Controller
public class TwitterControllerRest implements TwitterController{

    private Twitter twitter;

    private ConnectionRepository connectionRepository;

    @Autowired
    public TwitterControllerRest(Twitter twitter, ConnectionRepository connectionRepository) {
        this.twitter = twitter;
        this.connectionRepository = connectionRepository;
    }


    @Override
    public String twitterConnect(Model model) {
        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
            return "redirect:/connect/twitter";
        }
        return "";
    }

    @Override
    public CursoredList<TwitterProfile> twitterFriends(){
        CursoredList<TwitterProfile> friends = twitter.friendOperations().getFriends();
        return friends;
    }

}
