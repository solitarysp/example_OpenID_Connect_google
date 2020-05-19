package lethanh98.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class DefaultController {
    @GetMapping("/")
    public String defaults() {
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
                "window.location.href = \"http://\"+window.location.host +'/OIDC/Info';\n" +
                "\n" +
                "</script> "+
                "</html>";
    }
}
