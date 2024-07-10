package com.example.java_spring_advanced_project.service.impl;

import com.example.java_spring_advanced_project.model.binding.ReportABugBindingModel;
import com.example.java_spring_advanced_project.model.dto.Bugs.BugDto;
import com.example.java_spring_advanced_project.model.dto.HomeBugsDto;
import com.example.java_spring_advanced_project.model.entity.Bug;
import com.example.java_spring_advanced_project.model.entity.UserEntity;
import com.example.java_spring_advanced_project.repository.BugsRepository;
import com.example.java_spring_advanced_project.repository.UserRepository;
import com.example.java_spring_advanced_project.service.BugsService;
import com.example.java_spring_advanced_project.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BugsServiceImpl implements BugsService {

    private final BugsRepository bugsRepository;
    private final UserRepository userRepository;

    public BugsServiceImpl(BugsRepository bugsRepository,
                           UserRepository userRepository) {
        this.bugsRepository = bugsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public HomeBugsDto getBugsForBugsHome() {
        List<Bug> bugs = bugsRepository.findAll();

        List<BugDto> bugDtoList = new ArrayList<>();

        for (Bug bug : bugs) {
            bugDtoList.add(new BugDto(bug));
        }

        HomeBugsDto homeBugsDto = new HomeBugsDto(bugDtoList);

        return homeBugsDto;
    }

    @Override
    public boolean create(ReportABugBindingModel reportABugBindingModel) {
        Bug bug = new Bug();
        bug.setDescription(reportABugBindingModel.getDescription());

        // Getting the current logged-in user
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();

            Optional<UserEntity> userOptional = userRepository.findByUsername(username);

            if (userOptional.isPresent()) {
                UserEntity user = userOptional.get();
                bug.setReportedBy(user);
            } else {
                throw new UsernameNotFoundException("User not found: " + username);
            }
        }

        try {
            bugsRepository.save(bug);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void deleteBug(String uuid) {
        bugsRepository.deleteById(uuid);
    }
}
