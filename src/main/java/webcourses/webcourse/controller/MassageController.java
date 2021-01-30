package webcourses.webcourse.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("massage")
public class MassageController {
    public List<Map<String, String>> massages = new ArrayList<>() {{
       add(new HashMap<String, String>() {{put("id", "1"); put("text", "Fist massage"); }});
       add(new HashMap<String, String>() {{put("id", "1"); put("text", "Second massage"); }});
       add(new HashMap<String, String>() {{put("id", "1"); put("text", "Third massage"); }});
    }};

    @GetMapping
    public List<Map<String, String>> list(){
        return massages;
    }

//    @GetMapping("{id}")
//    public
}
