package com.example.demo.controller;

import com.example.demo.req.GetLoginTokenReqDTO;
import com.example.demo.res.SetSignInResRsltDTO;
import com.example.demo.res.GetUserInfoResRsltDTO;
import com.example.demo.res.ProcesResRsltDTO;
import com.example.demo.res.UserResDTO;
import com.example.demo.services.JwtService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/usr")
public class Logincontroller {

    @Autowired
    private JwtService JwtService;

    @RequestMapping("/")
    public String test(){
        System.out.println("ㅌㅔ스트");
        return "hello";
    }

    @RequestMapping("/setSignIn")
    public ProcesResRsltDTO getLoginToken(GetLoginTokenReqDTO reqDto, HttpServletResponse res){
        ProcesResRsltDTO procesResRsltDTO = new ProcesResRsltDTO();
        procesResRsltDTO.setState("400");

        UserResDTO resDto = reqDto;
        System.out.println("유저정보:"+resDto.getUserId());
        System.out.println("유저정보:"+resDto.getPassWord());

        String token = "";
        if(StringUtils.isNotBlank(reqDto.getUserId()) && StringUtils.isNotBlank(reqDto.getPassWord())){
            //TODO 모든 서비스 구현 해야됨
            token = JwtService.setAuthToken(resDto);
            res.setHeader("jwt-auth-token",token);
            procesResRsltDTO.setState("500");
        }

        return procesResRsltDTO;
    }

    @RequestMapping("/getUserInfo")
    public GetUserInfoResRsltDTO getUserInfo(@RequestParam("jwtToken") String jwtToken) throws Exception {

        GetUserInfoResRsltDTO rsltDto = new GetUserInfoResRsltDTO();

        boolean chk = false;
        if(StringUtils.isNotBlank(jwtToken)){
            chk = JwtService.checkJwt(jwtToken);
            System.out.println("chk:"+chk);
        }

        if(chk){
            UserResDTO userResDTO = new UserResDTO();
            rsltDto.setUserResDTO(userResDTO);
        }

        ProcesResRsltDTO procesResRsltDTO = new ProcesResRsltDTO();
        procesResRsltDTO.setState("500");

        return rsltDto;
    }

}
