#include <cpprest/http_listener.h>

#include "file_handler.hpp"

using namespace std;
using namespace web;
using namespace http;
using namespace utility;
using namespace http::experimental::listener;

class handler
{
public:
    handler();
    handler(string_t url, string_t image_folder_path);
    virtual ~handler();

    pplx::task<void>open() { return m_listener.open(); }
    pplx::task<void>close() { return m_listener.close(); }

protected:

private:
    void handle_get(const http_request& message);
    void handle_post(const http_request& message);
    void handle_delete(const http_request& message);
    void handle_error(pplx::task<void>& t);
    http_listener m_listener;
    file_handler image_handler_;
};