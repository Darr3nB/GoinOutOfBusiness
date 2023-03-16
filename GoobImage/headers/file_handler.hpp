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
private:
	/*static constexpr std::string product_image_path = "product";*/
	static inline string_t product_image_path = U("product");
};
