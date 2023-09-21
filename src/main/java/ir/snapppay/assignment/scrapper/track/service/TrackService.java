package ir.snapppay.assignment.scrapper.track.service;


import ir.snapppay.assignment.scrapper.security.security.services.UserDetailsImpl;
import ir.snapppay.assignment.scrapper.track.model.AddUrlDTO;
import ir.snapppay.assignment.scrapper.user.model.dto.ResponseDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TrackService {
    public ResponseDTO addURL(AddUrlDTO dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl principal = (UserDetailsImpl)auth.getPrincipal();
        System.out.println(principal.getId());
        return new ResponseDTO("URL saved successfully!");
    }
}
