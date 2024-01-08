package vasler.devicelab.adapters.primary.htmx;

public class HtmlFragments {
//                    <div class="sm:hidden">
//                        <label for="tabs" class="sr-only">Select your country</label>
//                        <select id="tabs" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
//                            <option>Profile</option>
//                            <option>Dashboard</option>
//                            <option>Settings</option>
//                            <option>Invoice</option>
//                        </select>
//                    </div>
//                    <ul class="hidden mx-auto max-w-5xl text-sm font-medium text-center text-gray-500 rounded-lg shadow sm:flex dark:divide-gray-700 dark:text-gray-400">
//                        <li class="w-full">
//                            <a href="#" class="inline-block w-full p-4 text-gray-900 bg-gray-100 border-r border-gray-200 dark:border-gray-700 rounded-s-lg focus:ring-4 focus:ring-blue-300 active focus:outline-none dark:bg-gray-700 dark:text-white" aria-current="page">Profile</a>
//                        </li>
//                        <li class="w-full">
//                            <a href="#" class="inline-block w-full p-4 bg-white border-r border-gray-200 dark:border-gray-700 hover:text-gray-700 hover:bg-gray-50 focus:ring-4 focus:ring-blue-300 focus:outline-none dark:hover:text-white dark:bg-gray-800 dark:hover:bg-gray-700">Dashboard</a>
//                        </li>
//                        <li class="w-full">
//                            <a href="#" class="inline-block w-full p-4 bg-white border-r border-gray-200 dark:border-gray-700 hover:text-gray-700 hover:bg-gray-50 focus:ring-4 focus:ring-blue-300 focus:outline-none dark:hover:text-white dark:bg-gray-800 dark:hover:bg-gray-700">Settings</a>
//                        </li>
//                        <li class="w-full">
//                            <button class="inline-block w-full p-4 bg-white border-r border-gray-200 dark:border-gray-700 hover:text-gray-700 hover:bg-gray-50 focus:ring-4 focus:ring-blue-300 focus:outline-none dark:hover:text-white dark:bg-gray-800 dark:hover:bg-gray-700">Invoice</button>
//                        </li>
//                    </ul>
        public static final String PAGE_HEADER = """
            <!DOCTYPE html>
            <html>
                <head>
                    <meta charset="UTF-8" />
                    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                    <script src="https://unpkg.com/htmx.org@1.9.10" integrity="sha384-D1Kt99CQMDuVetoL1lrYwg5t+9QdHe7NLX/SoJYkXDFfX37iInKRy5xLSi8nO7UC" crossorigin="anonymous"></script>
                    <script src="https://cdn.tailwindcss.com"></script>
                    <title>Phone booking demo</title>
                </head>
                <body class="bg-gray-100">
                        <div id="page-content" class="bg-white p-4 mt-2 border rounded-lg max-w-2xl m-auto">""";

    public static final String PAGE_FOOTER = """
                        </div>
                </body>
            </html>""";
}
