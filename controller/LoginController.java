package ro.tuc.ds2020.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.tuc.ds2020.dto.AccountDto;
import ro.tuc.ds2020.dto.JwtResponse;
import ro.tuc.ds2020.dto.LoginDto;
import ro.tuc.ds2020.model.Account;
import ro.tuc.ds2020.security.UserDetailsImpl;
import ro.tuc.ds2020.security.jwt.JwtUtils;
import ro.tuc.ds2020.services.AccountService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    private final AccountService accountService;

    @PostMapping("/login")
    /*public ResponseEntity<LoginDto> login(@RequestBody AccountDto accountDto) {
        LoginDto loginDto = accountService.findByUsernameAndPassword(accountDto.getUsername(), accountDto.getPassword());
        if (loginDto != null) {
            return new ResponseEntity<>(loginDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/

    public ResponseEntity<?> login(@Valid @RequestBody AccountDto accountDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(accountDto.getUsername(), accountDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(userDetails.getId(), jwt, userDetails.getUsername(), roles));
    }
}
