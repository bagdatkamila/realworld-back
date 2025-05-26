package sdu.project.realworldback.services;

import sdu.project.realworldback.dto.ProfileDto;

public interface ProfileService {
    ProfileDto getProfile(String username);

    ProfileDto followToUser(String username);

}
