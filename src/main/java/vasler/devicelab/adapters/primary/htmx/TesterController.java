package vasler.devicelab.adapters.primary.htmx;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import vasler.devicelab.ports.primary.phonereservation.PhoneReservationUseCase;
import vasler.devicelab.ports.primary.phonereservation.dto.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Controller
@RequestMapping( "/ui/tester")
@AllArgsConstructor
public class TesterController {
    private final PhoneReservationUseCase phoneReservationUseCase;

    @PostMapping(path = "/{id}", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    @ResponseBody
    public String registerPhone(@PathVariable @NotNull String id, @RequestParam MultiValueMap<String, String> params) throws Exception {
        params.entrySet().stream().forEach(e -> log.info("{} : {}", e.getKey(), e.getValue()));

        String phoneType = params.get("phoneTypeId").get(0);
        PhoneReservationResult phoneReservationResult = phoneReservationUseCase
            .reservePhone(PhoneReservationRequest.builder()
                .phoneType(phoneType)
                .tester(id).build()
            );

        StringBuilder stringBuilder = new StringBuilder();
        appendPageTitle(id, stringBuilder);

        if (phoneReservationResult.isSuccessful()) {
            stringBuilder.append("""
                <h1 class="rounded-lg bg-green-500 text-white align-middle width-auto min-w-full p-4 mt-2 mb-0 text-center text-2xl">
                    Phone reserved 
                </h1>""");
        }
        else {
            stringBuilder.append("""
                <h1 class="rounded-lg bg-red-500 text-white align-middle width-auto min-w-full p-4 mt-2 mb-0 text-center text-2xl">
                Phone not reserved
                </h1>""");
        }

        appendReservedPhones(id, stringBuilder);
        appendAvailablePhoneTypes(id, stringBuilder);

        return stringBuilder.toString();
    }

    @DeleteMapping(path = "/{id}/{phoneId}")
    @ResponseBody
    public String returnPhone(@PathVariable @NotNull String id, @PathVariable @NotNull String phoneId) throws Exception {

        PhoneReturnResult phoneReturnResult = phoneReservationUseCase
            .returnPhone(PhoneReturnRequest.builder()
                .phoneId(phoneId)
                .tester(id).build()
            );

        StringBuilder stringBuilder = new StringBuilder();
        appendPageTitle(id, stringBuilder);

        if (phoneReturnResult.isSuccessful()) {
            stringBuilder.append("""
                <h1 class="rounded-lg bg-green-500 text-white align-middle width-auto min-w-full p-4 mt-2 mb-0 text-center text-2xl">
                    Phone returned
                </h1>""");
        }
        else {
            stringBuilder.append("""
                <h1 class="rounded-lg bg-red-500 text-white align-middle width-auto min-w-full p-4 mt-2 mb-0 text-center text-2xl">
                Phone not returned
                </h1>""");
        }

        appendReservedPhones(id, stringBuilder);
        appendAvailablePhoneTypes(id, stringBuilder);

        return stringBuilder.toString();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public String displayTesterPage(@PathVariable @NotNull String id) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(HtmlFragments.PAGE_HEADER);
        appendPageTitle(id, stringBuilder);
        appendReservedPhones(id, stringBuilder);
        appendAvailablePhoneTypes(id, stringBuilder);
        stringBuilder.append(HtmlFragments.PAGE_FOOTER);

        return stringBuilder.toString();
    }

    private void appendPageTitle(String testerId, StringBuilder stringBuilder) {
        stringBuilder.append("""
            <h1 class="align-middle width-auto min-w-full p-4 mb-2 text-center text-4xl">
                <a href="/ui/testers"><img class="align-middle float-left" src="/static/img/back-arrow.png"></a>
                <span class="text-orange-700 p-0 m-0 align-middle">%s</span>
            </h1>""".formatted(testerId));
    }

    private void appendReservedPhones(String testerId, StringBuilder stringBuilder) throws Exception {
        List<ReservedPhone> reservedPhones = phoneReservationUseCase.fetchPhonesReservedByTester(testerId);

        stringBuilder.append("""
            <h1 class="bg-orange-700 text-white align-middle width-auto min-w-full p-4 mt-2 mb-0 text-center text-2xl">
                RESERVED PHONES
            </h1>
            <table class="border-2 border-solid border-orange-700 min-w-full divide-y divide-gray-200">
                <thead class="bg-gray-800 text-white">
                    <tr>
                        <th class="bg-orange-700 mx-6 px-6 py-3 text-center">Phone Type</th>
                        <th class="bg-orange-700 mx-6 px-6 py-3 text-center">Reserved On</th>
                        <th class="bg-orange-700 mx-6 px-6 py-3 text-center"></th>
                    </tr>
                </thead>
                <tbody id="reserved-phones" class="text-white divide-y divide-gray-600">"""
                .formatted(testerId));

        if (!reservedPhones.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            reservedPhones.forEach(reservedPhone -> {
                stringBuilder.append("""
                    <tr>
                        <td class="bg-orange-700 mx-6 px-6 py-3 text-center">%s</td>
                        <td class="bg-orange-700 mx-6 px-6 py-3 text-center">%s</td>
                        <td class="bg-orange-700 mx-6 px-6 py-3 text-center">
                            <button hx-delete="/ui/tester/%s/%s" hx-target="#page-content" class="font-extrabold">Return</button>
                        </td>
                    </tr>""".formatted(reservedPhone.getPhoneType(),
                        formatter.format(reservedPhone.getReservedOn()),
                        testerId,
                        reservedPhone.getPhoneId().toString())
                );
            });
        }
        else {
            stringBuilder.append("""
                <tr>
                    <td colspan="3" class="bg-orange-700 mx-6 px-6 py-3 text-center">NO PHONES RESERVED</td>
                </tr>"""
            );
        }

        stringBuilder.append("""
                </tbody>
            </table>
            """.formatted(testerId));
    }

    private void appendAvailablePhoneTypes(String testerId, StringBuilder stringBuilder) throws Exception {
        List<AvailablePhoneType> availablePhoneTypes = phoneReservationUseCase.findAvailablePhoneTypes();

        stringBuilder.append("""
            <h1 class="bg-green-700 text-white align-middle width-auto min-w-full p-4 mt-2 mb-0 text-center text-2xl">
                AVAILABLE PHONES
            </h1>
            <table class="border-2 border-solid border-green-700 min-w-full divide-y divide-gray-200">
                <thead class="bg-gray-800 text-white">
                    <tr>
                        <th class="bg-green-700 mx-6 px-6 py-3 text-center">Phone Type</th>
                        <th class="bg-green-700 mx-6 px-6 py-3 text-center"></th>
                    </tr>
                </thead>
                <tbody id="reserved-phones" class="text-white divide-y divide-gray-600">"""
        );

        if (!availablePhoneTypes.isEmpty()) {
            availablePhoneTypes.forEach(availablePhoneType -> {
                stringBuilder.append("""
                    <tr>
                        <td class="bg-green-700 mx-6 px-6 py-3 text-center">%s</td>
                        <td class="bg-green-700 mx-6 px-6 py-3 text-center">
                            <input name="phoneTypeId" class="phoneTypeId" type="hidden" value="%s">
                            <button hx-post="/ui/tester/%s" hx-include="previous .phoneTypeId" hx-target="#page-content" class="font-extrabold">Reserve</button>
                        </td>
                    </tr>""".formatted(availablePhoneType.getPhoneTypeName(), availablePhoneType.getPhoneTypeId(), testerId)
                );
            });
        }
        else {
            stringBuilder.append("""
                <tr>
                    <td colspan="2" class="bg-green-700 mx-6 px-6 py-3 text-center">NO PHONES AVAILABLE</td>
                </tr>"""
            );
        }

        stringBuilder.append("""
                </tbody>
            </table>""");
    }
}
