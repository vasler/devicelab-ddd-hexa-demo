package vasler.devicelab.adapters.primary.htmx;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import vasler.devicelab.ports.primary.testermanagement.TesterManagementUseCase;
import vasler.devicelab.ports.primary.testermanagement.dto.TesterDetails;

import java.util.List;

@Controller
@RequestMapping( "/ui/testers")
@AllArgsConstructor
public class TesterManagementController {
    private final TesterManagementUseCase testerManagementUseCase;

    @GetMapping("")
    @ResponseBody
    public String displayRegisteredTestersPage() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(HtmlFragments.PAGE_HEADER);

        appendRegisteredTesters(stringBuilder);
        stringBuilder.append(HtmlFragments.PAGE_FOOTER);

        return stringBuilder.toString();
    }

    private void appendRegisteredTesters(StringBuilder stringBuilder) throws Exception {
        List<TesterDetails> testerDetailsList = testerManagementUseCase.listRegisteredTesters();

        stringBuilder.append("""
            <h1 class="bg-green-700 text-white align-middle width-auto min-w-full p-4 mt-2 mb-0 text-center text-2xl">
                REGISTERED TESTERS
            </h1>
            <table class="border-2 border-solid border-green-700 min-w-full divide-y divide-gray-200">
                <thead class="bg-gray-800 text-white">
                    <tr>
                        <th class="bg-green-700 mx-6 px-6 py-3 text-center">Tester</th>
                        <th class="bg-green-700 mx-6 px-6 py-3 text-center"></th>
                    </tr>
                </thead>
                <tbody id="booked-phones" class="text-white divide-y divide-gray-600">"""
            );

        if (!testerDetailsList.isEmpty()) {
            testerDetailsList.forEach(testerDetails -> {
                stringBuilder.append("""
                <tr>
                    <td class="bg-green-700 mx-6 px-6 py-3 text-center">%s</td>
                    <td class="bg-green-700 mx-6 px-6 py-3 text-center"><a href="/ui/tester/%s/phones" class="font-extrabold">Select</a></td>
                </tr>""".formatted(testerDetails.getTesterId(), testerDetails.getTesterId())
                );
            });
        }
        else {
            stringBuilder.append("""
                <tr>
                    <td colspan="2" class="bg-green-700 mx-6 px-6 py-3 text-center">NO TESTERS REGISTERED</td>
                </tr>"""
            );
        }

        stringBuilder.append("""
                </tbody>
            </table>
            """
        );
    }
}
