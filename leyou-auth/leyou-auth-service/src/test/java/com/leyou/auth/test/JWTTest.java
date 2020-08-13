package com.leyou.auth.test;

import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.auth.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.PrivateKey;
import java.security.PublicKey;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JWTTest {

    private static final String pubKeyPath = "F:\\Code\\Java\\project\\mall\\LeyouProject\\leyou\\leyou-auth\\leyou-auth-service\\src\\main\\resources\\rsa\\rsa.pub";

    private static final String priKeyPath = "F:\\Code\\Java\\project\\mall\\LeyouProject\\leyou\\leyou-auth\\leyou-auth-service\\src\\main\\resources\\rsa\\rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
    }

    @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    @Test
    public void testGenerateToken() throws Exception {
        // 生成token
        String token = JwtUtils.generateToken(new UserInfo(20L, "jack"), privateKey, 5);
        System.out.println("token = " + token);
    }

    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9" +
                ".eyJpZCI6MjAsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTU5NTkzMTUxMX0" +
                ".ZrkcpRADB_AIh1BkGQEgwZd5AsoBWLJgORh_wsmtHNmwfIzQaHhg9h0tOi0c9WLF8TGiu3gUVdxha4HDjRlg4uOGmMC4cTe4j2iNCQiGTGT5ht-vHWE2dCP7ayfrzPj6-5Ul3qOZ8n37orgjEttWhrBAgJTzkSlMOsY77AkU8cY";

        // 解析token
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUsername());
    }
}
