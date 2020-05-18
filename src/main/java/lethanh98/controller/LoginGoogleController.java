package lethanh98.controller;

import lethanh98.config.teamplate.RestAll;
import lethanh98.dto.request.LoginGoogleRequestDto;
import lethanh98.dto.response.LoginGoogleResponseDto;
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
public class LoginGoogleController {
    @Autowired
    RestAll restAll;

    @GetMapping("/getInfoUser")
    public LoginGoogleResponseDto get(@RequestParam("code") String code) {
        LoginGoogleRequestDto checkRequestDto = LoginGoogleRequestDto.builder()
                .code(code)
                .clientId("601662646531-aaja6d30rsh2b6mt4nm7f2dovnm7pup0.apps.googleusercontent.com")
                .clientSecret("8oWBL4fnEYX4_sspOvct9YW0")
                .redirectUri("http://localhost:8080/OIDC/getInfoUser")
                .grantType("authorization_code")
                .build();
        HttpEntity<LoginGoogleRequestDto> requestEntityRemit = new HttpEntity<>(checkRequestDto);

        ResponseEntity<LoginGoogleResponseDto> response = restAll.restTemplate().exchange("https://oauth2.googleapis.com/token", HttpMethod.POST, requestEntityRemit, new ParameterizedTypeReference<LoginGoogleResponseDto>() {
        });
        LoginGoogleResponseDto loginGoogleResponseDto = response.getBody();
        loginGoogleResponseDto.getUrl().put("Lấy thông tin user", "https://openidconnect.googleapis.com/v1/userinfo?access_token=" + loginGoogleResponseDto.getAccessToken());
        return loginGoogleResponseDto;
    }

    @GetMapping("Info")
    public Map<String, String> login() {
        Map<String, String> url = new HashMap<>();
        url.put("Authentication google", "https://accounts.google.com/o/oauth2/v2/auth?client_id=601662646531-aaja6d30rsh2b6mt4nm7f2dovnm7pup0.apps.googleusercontent.com&scope=openid%20email%20profile&redirect_uri=http://localhost:8080/OIDC/getInfoUser&response_type=code&prompt=consent&access_type=offline");
        url.put("openid-configuration", "https://accounts.google.com/.well-known/openid-configuration");
        return url;
    }
}
