#include "./headers/handler.hpp"
#include "./headers/filesystem.hpp"


using namespace concurrency::streams;

handler::handler()
{
    //ctor
}
handler::handler(utility::string_t url, filesystem::path product_image_path) :m_listener(url), product_dir_path_(product_image_path)
{
    filesystem::create_directory(product_image_path);
    m_listener.support(methods::GET, [this](auto&& ph1) { handle_get(std::forward<decltype(ph1)>(ph1)); });
    m_listener.support(methods::PUT, [this](auto&& ph1) { handle_put(std::forward<decltype(ph1)>(ph1)); });
    m_listener.support(methods::POST, [this](auto&& ph1) { handle_post(std::forward<decltype(ph1)>(ph1)); });
    m_listener.support(methods::DEL, [this](auto&& ph1) { handle_delete(std::forward<decltype(ph1)>(ph1)); });
}
handler::~handler()
{
    //dtor
}

void handler::handle_error(pplx::task<void>& t)
{
    try
    {
        t.get();
    }
    catch (...)
    {
        // Ignore the error, Log it if a logger is available
    }
}


//
// Get Request 
//
void handler::handle_get(http_request message)
{
    ucout << message.to_string() << endl;
    const auto paths = http::uri::split_path(http::uri::decode(message.relative_uri().path()));

    if(paths[0] == U("product"))
    {
        const utility::string_t& path = message.request_uri().path();
        const utility::string_t file_path = product_dir_path_.concat(U("/") + paths[1]);
        utility::string_t mime_type = U("application/octet-stream");
        if (path.ends_with(U(".png"))) {
            mime_type = U("image/png");
        }
        else {
            mime_type = U("image/jpeg");
        }

        concurrency::streams::fstream::open_istream(file_path, std::ios::in).then([=](concurrency::streams::istream file_stream) {
            http_response response(status_codes::OK);
            response.set_body(file_stream);
            response.headers().set_content_type(mime_type);
            message.reply(response);
            }).wait();
            return;
    }

    return;
};

//
// A POST request
//
void handler::handle_post(http_request message)
{
    ucout << message.to_string() << endl;


    message.reply(status_codes::OK, message.to_string());
    return;
};

////
// A DELETE request
//
void handler::handle_delete(http_request message)
{
    ucout << message.to_string() << endl;

    message.reply(status_codes::OK, "rep");
    return;
};


//
// A PUT request 
//
void handler::handle_put(http_request message)
{
    ucout << message.to_string() << endl;

    message.reply(status_codes::OK, "rep");
    return;
};