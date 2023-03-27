// GoobImage.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include <iostream>
#include "./headers/handler.hpp"

using namespace std;
using namespace web;
using namespace http;
using namespace utility;
using namespace http::experimental::listener;

using namespace web;
using namespace web::http;

std::unique_ptr<handler> product_http_handler;
const string_t product_image_path = U("product");


void on_initialize(const string_t& address)
{


    uri_builder uri(address);


    auto addr = uri.to_uri().to_string();
    product_http_handler = std::unique_ptr<handler>(new handler(addr, product_image_path));
    product_http_handler->open().wait();

    ucout << utility::string_t(U("Listening for requests at: ")) << addr << std::endl;

    return;
}

void on_shutdown()
{
    product_http_handler->close().wait();
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
    address.append(U("/product"));

    on_initialize(address);
    std::cout << "Press ENTER to exit." << std::endl;

    std::string line;
    std::getline(std::cin, line);

    on_shutdown();
    return 0;
}