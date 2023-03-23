// ReSharper disable CppExpressionWithoutSideEffects
#include "./headers/handler.hpp"
#include "headers/file_handler.hpp"


using namespace concurrency::streams;

handler::handler()
{
    //ctor
}
handler::handler(utility::string_t url) :m_listener(url)
{
    m_listener.support(methods::GET, [this](auto&& ph1) { handle_get(std::forward<decltype(ph1)>(ph1)); });
    m_listener.support(methods::POST, [this](auto&& ph1) { handle_post(std::forward<decltype(ph1)>(ph1)); });
    m_listener.support(methods::DEL, [this](auto&& ph1) { handle_delete(std::forward<decltype(ph1)>(ph1)); });
    file_handler::create_product_directory();
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
void handler::handle_get(const http_request& message)
{
    ucout << message.to_string() << endl;
    const auto paths = http::uri::split_path(http::uri::decode(message.relative_uri().path()));

    if(paths[0] == U("product"))
    {
        const utility::string_t& path = message.request_uri().path();
        utility::string_t mime_type = U("application/octet-stream");
        if (path.ends_with(U(".png"))) {
            mime_type = U("image/png");
        }
        else {
            mime_type = U("image/jpeg");
        }


        http_response response;
        try
        {
	        const concurrency::streams::istream returned_file = file_handler::get_product_image(paths[1]);
			response.set_body(returned_file);
            response.set_status_code(status_codes::OK);
            response.headers().set_content_type(mime_type);
            message.reply(response);
        }
        catch (runtime_error& e)
        {
            response.set_status_code(status_codes::BadRequest);
            response.headers().set_content_type(mime_type);
            message.reply(response);
        }

    }
};
//
// A POST request
//
void handler::handle_post(const http_request& message)
{
    ucout << message.to_string() << endl;

    
    //auto names = 
	file_handler::save_files(message.body());

    //string json_string = "[";
    //for(int i = 0; i < names.size()-1; i++)
    //{
    //    json_string += names[i] + ",";
    //}
    //json_string += names.back() + "]";


    http_response response(status_codes::Created);
    //response.headers().set_content_type(U("application/json"));
    //response.set_body(json_string);
    message.reply(response);
}

////
// A DELETE request
//
void handler::handle_delete(const http_request& message)
{
    ucout << message.to_string() << endl;

    message.reply(status_codes::OK, "rep");
    return;
};