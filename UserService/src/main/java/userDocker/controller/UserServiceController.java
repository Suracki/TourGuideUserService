package userDocker.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gpsUtil.location.Attraction;
import userDocker.remote.user.model.inputEntities.VisitedLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import userDocker.remote.user.gson.MoneyTypeAdapterFactory;
import userDocker.remote.user.model.User;
import userDocker.service.UserService;

@RestController
public class UserServiceController {

    private Logger logger = LoggerFactory.getLogger(UserServiceController.class);

    UserService userService;

    Gson gson;

    public UserServiceController(UserService userService){
        this.userService = userService;
        gson = new GsonBuilder().registerTypeAdapterFactory(new MoneyTypeAdapterFactory()).setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();
    }

    @PostMapping("/user/addUser")
    public boolean addUser(@RequestBody String userJson) {
        logger.info("/addUser endpoint called");
        String json = userJson.substring(1, userJson.length() - 1).replaceAll("\\\\","");
        User user = gson.fromJson(json, User.class);
        return userService.addUser(user);
    }

    @PostMapping("user/addToVisitedLocations")
    public String addToVisitedLocations(@RequestBody String visitedLocation, @RequestParam String userName) {
        logger.info("/addToVisitedLocations endpoint called");
        String json = visitedLocation.substring(1, visitedLocation.length() - 1).replaceAll("\\\\","");
        VisitedLocation location = gson.fromJson(json, VisitedLocation.class);
        return userService.addToVisitedLocations(location, userName);
    }

    @GetMapping("user/getAllCurrentLocations")
    public String getAllCurrentLocations() {
        logger.info("/getAllCurrentLocations endpoint called");
        //List<UserLocation>
        return gson.toJson(userService.getAllCurrentLocations());
    }

    @PostMapping("user/addUserReward")
    public boolean addUserReward(@RequestParam String userName, @RequestParam VisitedLocation visitedLocation,
                                 @RequestParam Attraction attraction, @RequestParam int rewardPoints) {
        logger.info("/addUserReward endpoint called");
        return userService.addUserReward(userName, visitedLocation, attraction, rewardPoints);
    }

    @GetMapping("user/getAllUsers")
    public String getAllUsers() {
        System.out.println("GET ALL USERS");
        logger.info("/getAllUsers endpoint called");
        //List<User>
        return gson.toJson(userService.getAllUsers());
    }

    @GetMapping("user/getUserByUsername")
    public String getUserByUsername(String userName) {
        logger.info("/getUser endpoint called");
        //User
        return gson.toJson(userService.getUserByUsername(userName));
    }

    @GetMapping("user/getLastVisitedLocationByName")
    public String getLastVisitedLocationByName(String userName) {
        logger.info("/getLastVisitedLocationByName endpoint called");
        //VisitedLocation
        return gson.toJson(userService.getLastVisitedLocationByName(userName));
    }

    @GetMapping("user/getUserRewardsByUsername")
    public String getUserRewardsByUsername(String userName) {
        logger.info("/getUserRewardsByUsername endpoint called");
        //List<UserReward>
        return gson.toJson(userService.getUserRewardsByUsername(userName));
    }

    @GetMapping("user/getVisitedLocationsByUsername")
    public String getVisitedLocationsByUsername(String userName) {
        logger.info("/getVisitedLocationsByUsername endpoint called");
        //List<VisitedLocation
        String json = gson.toJson(userService.getVisitedLocationsByUsername(userName));
        System.out.println("JSON RESPONSE: " + json);
        return json;
        //return gson.toJson(userService.getVisitedLocationsByUsername(userName));
    }

    @GetMapping("user/getUserIdByUsername")
    public String getUserIdByUsername(String userName) {
        logger.info("/getUserIdByUsername endpoint called");
        //UUID
        return gson.toJson(userService.getUserIdByUsername(userName));
    }

    @PostMapping("user/trackAllUserLocations")
    public boolean trackAllUserLocations() {
        logger.info("/trackAllUserLocations endpoint called");
        System.out.println("TRACK ALL USERS");
        return userService.trackAllUserLocations();
    }

    @GetMapping("user/getUserCount")
    public String getUserCount() {
        logger.info("/getUserCount endpoint called");
        //int
        return gson.toJson(userService.getUserCount());
    }

    @GetMapping("user/getAllUserNames")
    public String getAllUserNames() {
        logger.info("/getAllUserNames endpoint called");
        //List<String>
        return gson.toJson(userService.getAllUserNames());
    }
}
