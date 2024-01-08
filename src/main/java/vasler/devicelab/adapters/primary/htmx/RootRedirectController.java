package vasler.devicelab.adapters.primary.htmx;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/")
public class RootRedirectController {
    @GetMapping
    public RedirectView redirect() {
        return new RedirectView("/ui/testers");
    }
}
