package com.uma.informatica.controllers;

import org.springframework.http.MediaType;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by rafa on 11/06/14.
 */
@RequestMapping(value = "/twitter")
public interface TwitterController {


    @RequestMapping(method = RequestMethod.GET)
    String twitterConnect(Model model);

    @RequestMapping(value="/friends", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    CursoredList<TwitterProfile> twitterFriends();

}
