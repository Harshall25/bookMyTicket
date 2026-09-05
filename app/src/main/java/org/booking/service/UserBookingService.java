package org.booking.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.booking.entities.User;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserBookingService {

    private User user;
    private List<User> userList;
    public ObjectMapper objectMapper = new ObjectMapper();
    private static final String USERS_PATH = "../localdb/users.json";

    public UserBookingService(User user) throws IOException {
        this.user = user;
        File users = new File(USERS_PATH);
        userList = objectMapper.readValue(users, new TypeReference<List<User>>() {});
    }

    public Boolean loginUser(){
        Optional<User> foundUser = userList.stream().filter(user -> {
            return user.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user, )
        }).findFirst();
        return foundUser.isPresent();
    }

    public Boolean signUp(User user){
        try{
            userList.add(user);
            saveUserListToFile();
            return Boolean.TRUE;
        }catch (IOException e){
            return Boolean.FALSE;
        }
    }
}
