// GoobImage.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include <iostream>

#include "./headers/stdafx.hpp"
#include "./headers/handler.hpp"

using namespace std;
using namespace web;
using namespace http;
using namespace utility;
using namespace http::experimental::listener;

using namespace web;
using namespace web::http;

std::unique_ptr<handler> g_httpHandler;
const string product_image_path = "product";


void on_initialize(const string_t& address)
{


    uri_builder uri(address);


    auto addr = uri.to_uri().to_string();
    g_httpHandler = std::unique_ptr<handler>(new handler(addr, product_image_path));
    g_httpHandler->open().wait();

    ucout << utility::string_t(U("Listening for requests at: ")) << addr << std::endl;

    return;
}

void on_shutdown()
{
    g_httpHandler->close().wait();
    return;
}


#ifdef _WIN32
int wmain(int argc, wchar_t* argv[])
#else
int main(int argc, char* argv[])
#endif
{
    utility::string_t port = U("34568");
    if (argc == 2)
    {
        port = argv[1];
    }

    utility::string_t address = U("http://127.0.0.1:");
    address.append(port);

    on_initialize(address);
    std::cout << "Press ENTER to exit." << std::endl;

    std::string line;
    std::getline(std::cin, line);

    on_shutdown();
    return 0;
}

// Run program: Ctrl + F5 or Debug > Start Without Debugging menu
// Debug program: F5 or Debug > Start Debugging menu

// Tips for Getting Started: 
//   1. Use the Solution Explorer window to add/manage files
//   2. Use the Team Explorer window to connect to source control
//   3. Use the Output window to see build output and other messages
//   4. Use the Error List window to view errors
//   5. Go to Project > Add New Item to create new code files, or Project > Add Existing Item to add existing code files to the project
//   6. In the future, to open this project again, go to File > Open > Project and select the .sln file



//#include <cpprest/http_client.h>
//#include <cpprest/filestream.h>
//
//using namespace utility;                    // Common utilities like string conversions
//using namespace web;                        // Common features like URIs.
//using namespace web::http;                  // Common HTTP functionality
//using namespace web::http::client;          // HTTP client features
//using namespace concurrency::streams;


//auto fileStream = std::make_shared<ostream>();
//
//// Open stream to output file.
//pplx::task<void> requestTask = fstream::open_ostream(U("results.html")).then([=](ostream outFile)
//    {
//        *fileStream = outFile;
//        // Create http_client to send the request.
//        http_client client(U("http://www.bing.com/"));
//
//        // Build request URI and start the request.
//        uri_builder builder(U("/search"));
//        builder.append_query(U("q"), U("cpprestsdk github"));
//        return client.request(methods::GET, builder.to_string());
//    })
//    .then([=](http_response response)
//        {
//            printf("Received response status code:%u\n", response.status_code());
//
//            // Write response body into the file.
//            return response.body().read_to_end(fileStream->streambuf());
//        })
//        .then([=](size_t)
//            {
//                return fileStream->close();
//            });
//
//
//        try
//        {
//            requestTask.wait();
//        }
//        catch (const std::exception& e)
//        {
//            printf("Error exception:%s\n", e.what());
//        }

//Severity	Code	Description	Project	File	Line	Suppression State
//Error	LNK2019	unresolved external symbol "private: void __cdecl handler::handle_put(class web::http::http_request)" (?handle_put@handler@@AEAAXVhttp_request@http@web@@@Z) referenced in function "public: auto __cdecl `public: __cdecl handler::handler(class std::basic_string<wchar_t,struct std::char_traits<wchar_t>,class std::allocator<wchar_t> >)'::`2'::<lambda_2>::operator()<class web::http::http_request>(class web::http::http_request &&)const " (??$?RVhttp_request@http@web@@@<lambda_2>@?1???0handler@@QEAA@V?$basic_string@_WU?$char_traits@_W@std@@V?$allocator@_W@2@@std@@@Z@QEBA?A_P$$QEAVhttp_request@http@web@@@Z)	GoobImage	C:\Users\lordz\git\practice\GoinOutOfBusiness\GoobImage\handler.obj	1	
