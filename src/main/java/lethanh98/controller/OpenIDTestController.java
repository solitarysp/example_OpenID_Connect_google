package lethanh98.controller;

import lethanh98.config.teamplate.RestAll;
import lethanh98.dto.request.LoginGoogleRequestDto;
import lethanh98.dto.response.OpenIdTestResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/OIDC")
public class OpenIDTestController {
    @Autowired
    RestAll restAll;

    @GetMapping("/getInfoUser")
    public OpenIdTestResponseDto get(@RequestParam(value = "token") String token) {


        OpenIdTestResponseDto openIdTestResponseDto = new OpenIdTestResponseDto();
        openIdTestResponseDto.setAccessToken(token);
        openIdTestResponseDto.getUrl().put("Lấy thông tin user", "https://openidconnect.googleapis.com/v1/userinfo?access_token=" + openIdTestResponseDto.getAccessToken());
        return openIdTestResponseDto;
    }

    @GetMapping("/getToken")
    public String getToken() {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<h1>My First Heading</h1>\n" +
                "<p>My first paragraph.</p>\n" +
                "\n" +
                "</body>\n" +
                "<script>\n" +
                "var token = window.location.hash.split('#access_token=')[1];\n" +
                "window.location.href = \"http://\"+window.location.host +'/OIDC/getInfoUser?token='+token;\n" +
                "\n" +
                "</script> "+
                "</html>";
    }

    @GetMapping("/login_google")
    public OpenIdTestResponseDto getLoginGooogle(@RequestParam("code") String code) {
        LoginGoogleRequestDto checkRequestDto = LoginGoogleRequestDto.builder()
                .code(code)
                .clientId("601662646531-aaja6d30rsh2b6mt4nm7f2dovnm7pup0.apps.googleusercontent.com")
                .clientSecret("8oWBL4fnEYX4_sspOvct9YW0")
                .redirectUri("http://localhost:8080/OIDC/login_google")
                .grantType("authorization_code")
                .build();
        HttpEntity<LoginGoogleRequestDto> requestEntityRemit = new HttpEntity<>(checkRequestDto);

        ResponseEntity<OpenIdTestResponseDto> response = restAll.restTemplate().exchange("https://oauth2.googleapis.com/token", HttpMethod.POST, requestEntityRemit, new ParameterizedTypeReference<OpenIdTestResponseDto>() {
        });
        OpenIdTestResponseDto openIdTestResponseDto = response.getBody();
        openIdTestResponseDto.getUrl().put("Lấy thông tin user", "https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + openIdTestResponseDto.getAccessToken());
        return openIdTestResponseDto;
    }
    @GetMapping("Info")
    public Map<String, String> login() {
        Map<String, String> url = new HashMap<>();
        url.put("Authentication token", "https://accounts.google.com/o/oauth2/v2/auth?client_id=601662646531-aaja6d30rsh2b6mt4nm7f2dovnm7pup0.apps.googleusercontent.com&scope=openid%20email%20profile&redirect_uri=http://localhost:8080/OIDC/getToken&response_type=token&prompt=consent");
        url.put("Authentication code", "https://accounts.google.com/o/oauth2/v2/auth?client_id=601662646531-aaja6d30rsh2b6mt4nm7f2dovnm7pup0.apps.googleusercontent.com&scope=openid%20email%20profile&redirect_uri=http://localhost:8080/OIDC/login_google&response_type=code&prompt=consent&access_type=offline");
        url.put("Authentication id_token ", "https://accounts.google.com/o/oauth2/v2/auth?client_id=601662646531-aaja6d30rsh2b6mt4nm7f2dovnm7pup0.apps.googleusercontent.com&scope=openid%20email%20profile&redirect_uri=http://localhost:8080/OIDC/login_google&response_type=id_token&prompt=consent&access_type=offline&nonce=testnonce");
        url.put("Authentication id_token token", "https://accounts.google.com/o/oauth2/v2/auth?client_id=601662646531-aaja6d30rsh2b6mt4nm7f2dovnm7pup0.apps.googleusercontent.com&scope=openid%20email%20profile&redirect_uri=http://localhost:8080/OIDC/login_google&response_type=token id_token&prompt=consent&nonce=testnonce");
        url.put("Authentication code id_token ", "https://accounts.google.com/o/oauth2/v2/auth?client_id=601662646531-aaja6d30rsh2b6mt4nm7f2dovnm7pup0.apps.googleusercontent.com&scope=openid%20email%20profile&redirect_uri=http://localhost:8080/OIDC/login_google&response_type=code id_token&access_type=offline&prompt=consent&nonce=testnonce");
        url.put("Authentication code token  ", "https://accounts.google.com/o/oauth2/v2/auth?client_id=601662646531-aaja6d30rsh2b6mt4nm7f2dovnm7pup0.apps.googleusercontent.com&scope=openid%20email%20profile&redirect_uri=http://localhost:8080/OIDC/login_google&response_type=code token&access_type=offline&prompt=consent&nonce=testnonce");
        url.put("Authentication code id_token token", "https://accounts.google.com/o/oauth2/v2/auth?client_id=601662646531-aaja6d30rsh2b6mt4nm7f2dovnm7pup0.apps.googleusercontent.com&scope=openid%20email%20profile&redirect_uri=http://localhost:8080/OIDC/login_google&response_type=code token id_token&access_type=offline&prompt=consent&nonce=testnonce");
        url.put("openid-configuration", "https://accounts.google.com/.well-known/openid-configuration");
        return url;
    }
}
