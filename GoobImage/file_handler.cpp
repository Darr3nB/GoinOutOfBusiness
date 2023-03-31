#include <codecvt>

#include "filesystem"
#include <cpprest/http_client.h>
#include <cpprest/filestream.h>
#include "headers/file_handler.hpp"

using namespace utility;
using namespace concurrency;
using namespace streams;
using namespace std;


file_handler::file_handler()
{
	
}
file_handler::file_handler(const string_t& folder_name) : image_path_(folder_name)
{
	create_image_directory(folder_name);
	last_file_name = get_last_product_filename(folder_name);
};


streams::istream file_handler::get_image(const string_t& file_name)
{
	auto file_path = image_path_ + U("/") + file_name;

	return streams::fstream::open_istream(file_path, std::ios::in).get();
}
std::vector<std::string> file_handler::save_files(concurrency::streams::istream message_body)
{

	container_buffer<std::string> buffer;
	message_body.read_to_end(buffer).wait();
	std::string contents = buffer.collection();
	std::stringstream ss(contents);

	std::vector<std::string> linesInStream;
	std::string line;
	while (std::getline(ss, line)) {
		linesInStream.push_back(line);
	}

	int file_count = 0;
	std::vector<m_file> files;
	int data_begin_index;
	int data_end_index = 0;
	for (int i = 2; i < linesInStream.size(); i++)
	{
		if (linesInStream[i].find("image/jpeg") != string::npos)
		{
			file_count++;
			files.push_back(m_file());
			last_file_name++;
			files.at(file_count - 1).file_name = std::to_string(last_file_name) + ".jpg";
			data_begin_index = i + 2;
		}
		else if (linesInStream[i].find("image/png") != string::npos)
		{
			file_count++;
			files.push_back(m_file());
			last_file_name++;
			files.at(file_count - 1).file_name = std::to_string(last_file_name) + ".png";
			data_begin_index = i + 2;
		}
		else if (linesInStream[i].starts_with("----"))
		{
			data_end_index = i - 1;
		}
		if (data_end_index > data_begin_index)
		{
			for (int j = data_begin_index; j < data_end_index; j++)
			{
				files.at(file_count - 1).raw_data.append(linesInStream[j] + '\n');
			}
			static std::wstring_convert<codecvt_utf8_utf16<wchar_t>> converter;
			std::ofstream out_file(converter.to_bytes(image_path_) + "/" + files.at(file_count - 1).file_name, std::ios::binary);
			out_file << files.at(file_count - 1).raw_data;
			out_file.close();
		}

	}

	vector<string> file_names;
	for (const auto& file : files) file_names.push_back(file.file_name);
	return file_names;
}

