package jpabook.jpashoop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        System.out.println("들어옴");
        model.addAttribute("data","hello");
        return "hello";
    }
}
