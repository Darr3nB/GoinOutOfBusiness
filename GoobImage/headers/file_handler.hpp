#pragma once
#include <codecvt>
#include <string_view>

#include "stdafx.hpp"

using namespace utility;
using namespace concurrency;
using namespace streams;


class file_handler
{
public:
	static void create_product_directory()
	{
		std::filesystem::create_directory(product_image_path);
	}
	static auto get_product_image(const string_t& file_name)
	{
		auto file_path = product_image_path + U("/") + file_name;

		return streams::fstream::open_istream(file_path, std::ios::in).get();
	}
	static std::vector<std::string> save_files(concurrency::streams::istream message_body)
	{
		struct m_file
		{
			std::string file_name;
			std::string raw_data;
		};

		container_buffer<std::string> buffer;
		message_body.read_to_end(buffer).get();
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
				std::ofstream out_file(converter.to_bytes(product_image_path) + "/" + files.at(file_count - 1).file_name, std::ios::binary);
				out_file << files.at(file_count - 1).raw_data;
				out_file.close();
			}

		}

		vector<string> file_names;
		for (const auto& file : files) file_names.push_back(file.file_name);
		return file_names;
	}
	static int get_last_product_filename()
	{
		int max = 0;
		for (const auto& entry : filesystem::directory_iterator(product_image_path))
		{
			int file_number = convert_to_pos_int(entry.path().filename().stem().generic_string().c_str());
			if (file_number > max) max = file_number;
		}

		return max;
	}
private:
	/*static constexpr std::string product_image_path = "product";*/
	static inline string_t product_image_path = U("product");
	static inline int last_file_name = get_last_product_filename();
	static int convert_to_pos_int(char const* s)
	{
		if (s == NULL || *s == '\0')
			return -1;

		bool negate = (s[0] == '-');
		if (*s == '+' || *s == '-')
			++s;

		if (*s == '\0')
			return -1;

		int result = 0;
		while (*s)
		{
			if (*s < '0' || *s > '9')
				return -1;
			result = result * 10 + (*s - '0');
			++s;
		}
		return result;
	}
};

