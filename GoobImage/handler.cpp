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
	        const auto returned_file = file_handler::get_product_image(paths[1]);
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
    struct m_file
    {
        std::string extension;
        std::string raw_data = "";
    };

    ucout << message.to_string() << endl;
    auto istream1 = message.body();

    container_buffer<std::string> buffer;
    istream1.read_to_end(buffer).get();

    // Split the contents of the buffer into lines
    std::string contents = buffer.collection();
    std::stringstream ss(contents);
    std::string line;

    std::string file_name;


    std::vector<std::string> linesInStream;

    while (std::getline(ss, line)) {
        linesInStream.push_back(line);
    }

    int file_count = 0;
    std::vector<m_file> files;
    int data_begin_index;
    int data_end_index = 0;
    for(int i = 2; i < linesInStream.size(); i++)
    {
	    if(linesInStream[i].find("image/jpeg")!=string::npos)
	    {
            file_count++;
            files.push_back(m_file());
            files.at(file_count - 1).extension = ".jpg";
            data_begin_index = i + 2;
	    }
        if (linesInStream[i].find("image/png") != string::npos)
        {
            file_count++;
            files.push_back(m_file());
            files.at(file_count - 1).extension = ".png";
            data_begin_index = i + 2;
        }
        if (linesInStream[i].starts_with("----"))
        {
            data_end_index = i - 1;
        }
        if(data_end_index> data_begin_index)
        {
	        for(int j = data_begin_index; j < data_end_index; j++)
	        {
                files.at(file_count - 1).raw_data.append(linesInStream[j] + '\n');
	        }
            std::ofstream out_file(std::to_string(file_count) + files.at(file_count - 1).extension, std::ios::binary);
            out_file << files.at(file_count - 1).raw_data;
            out_file.close();
        }

    }

    http_response response(status_codes::Created);
    /*json::value js = json::value::array();

    for (int i = 0; i < names.size(); i++) {
        js[i] = json::value::parse(names[i]);
    }
    response.set_body(js);*/
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