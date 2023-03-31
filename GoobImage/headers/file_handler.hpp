#include <codecvt>

#include "filesystem"
#include <cpprest/http_client.h>
#include <cpprest/filestream.h>

using namespace utility;
using namespace concurrency;
using namespace streams;

#define FILE_HANDLER_H
class file_handler
{
public:
	file_handler();
	file_handler(const string_t& folder_name);

	streams::istream get_image(const string_t& file_name);
	std::vector<std::string> save_files(concurrency::streams::istream message_body);
private:
	string_t image_path_ = U("product");
	int last_file_name;
	static int get_last_product_filename(const string_t& path)
	{
		int max = 0;
		for (const auto& entry : std::filesystem::directory_iterator(path))
		{
			int file_number = convert_to_pos_int(entry.path().filename().stem().generic_string().c_str());
			if (file_number > max) max = file_number;
		}

		return max;
	}
	static void create_image_directory(const string_t& path)
	{
		std::filesystem::create_directory(path);
	}
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
	struct m_file
	{
		std::string file_name;
		std::string raw_data;
	};
};

