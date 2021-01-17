package com.example.demo.res;

import lombok.Data;

@Data
public class SetSignInResRsltDTO {
    private ProcesResRsltDTO procesResRsltDTO;
    /** 생성된 토큰 */
    private String jwtToken;

}
