package com.example.springvalidate.member;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class MemberController {

    @PostMapping("/join")
    public ResponseEntity join(@Valid @RequestBody MemberJoinRequest joinRequest){
        return ResponseEntity.ok(joinRequest);
    }
}
