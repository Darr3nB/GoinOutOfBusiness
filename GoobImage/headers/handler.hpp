#ifndef HANDLER_H
#define HANDLER_H
#include <iostream>
#include "stdafx.hpp"
#include "filesystem.hpp"


using namespace std;
using namespace web;
using namespace http;
using namespace utility;
using namespace http::experimental::listener;

class handler
{
public:
    handler();
    handler(utility::string_t url);
    virtual ~handler();

    pplx::task<void>open() { return m_listener.open(); }
    pplx::task<void>close() { return m_listener.close(); }

protected:

private:
    static void handle_get(const http_request& message);
    static void handle_post(const http_request& message);
    static void handle_delete(const http_request& message);
    void handle_error(pplx::task<void>& t);
    http_listener m_listener;
};

#endif // HANDLER_H