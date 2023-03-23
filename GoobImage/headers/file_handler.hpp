#pragma once
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
	static string_t save_file()
	{
		return U("a");
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

