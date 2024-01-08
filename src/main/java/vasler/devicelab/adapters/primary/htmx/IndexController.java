package vasler.devicelab.adapters.primary.htmx;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping( "/ui")
public class IndexController {
    @GetMapping("")
    @ResponseBody
    public String index() {
        return HtmlFragments.PAGE_HEADER +
            """
                <div hx-get="/ui/testers" hx-trigger="load" class="bg-grey-100 p-4 mt-2 border rounded-lg max-w-4xl m-auto"></div>
                <img src="/static/img/loader.gif" width="50" height="50">INDEX""" +
            HtmlFragments.PAGE_FOOTER;
    }

    // TODO
    @GetMapping("/error")
    @ResponseBody
    public String error() {
        return HtmlFragments.PAGE_HEADER +

            """
            ERROR""" +

            HtmlFragments.PAGE_FOOTER;
    }
}
