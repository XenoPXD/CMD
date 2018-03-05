#include <iostream>
#include <vector>
#include <math.h>
#include <algorithm>
#include <fstream>
#include <time.h>

using namespace std;

// g++ -std=c++11 paper-folding-curve.cpp -o paper-folding-curve
// https://stackoverflow.com/questions/33206409/unused-dt-entry-type-0x1d-arg
// https://stackoverflow.com/questions/865668/how-to-parse-command-line-arguments-in-c

ofstream file;
long compteurProgressBar = 0;
clock_t tStart = clock();

class InputParser {
public:
	InputParser(int &argc, char **argv) {
		for (int i = 1; i < argc; ++i)
			this->tokens.push_back(std::string(argv[i]));
	}
	/// @author iain
	const std::string& getCmdOption(const std::string &option) const {
		std::vector<std::string>::const_iterator itr;
		itr = std::find(this->tokens.begin(), this->tokens.end(), option);
		if (itr != this->tokens.end() && ++itr != this->tokens.end()) {
			return *itr;
		}
		static const std::string empty_string("");
		return empty_string;
	}
	/// @author iain
	bool cmdOptionExists(const std::string &option) const {
		return std::find(this->tokens.begin(), this->tokens.end(), option)
				!= this->tokens.end();
	}
private:
	std::vector<std::string> tokens;
};

int help() {
    cout << "usage: ./paper-folding-curve [-h] -o ORDER [-s START] [-e END]" << endl;
    cout << "                             [-out OUTPUT] [-n NUMBER]" << endl;
    cout << endl;
    cout << "Paper-folding curve." << endl;
    cout << "Coding Games"  << endl;
    cout << "https://www.codingame.com/training/community/paper-folding-curve"  << endl;
    cout << "create by java_coffee_cup"  << endl;
    cout << "resolved by Xeno_PXD"  << endl;
    cout << endl;
    cout << "mantatory argument:"  << endl;
    cout << "  -o, --order ORDER     An integer N for the order of the curve (default:"  << endl;
    cout << "                        None)"  << endl;
    cout << endl;
    cout << "optional arguments:"  << endl;
    cout << "  -h, --help            Show this help message and exit"  << endl;
    cout << "  -s, --start START     A starting index (default: 0)"  << endl;
    cout << "  -e, --end END         A ending index (default: Number curves)"  << endl;
    cout << "  -out, --output OUTPUT Output file (default: None)"  << endl;
    cout << "  -n, --number          Return number curve (default: False)"  << endl;
    cout << endl;
    cout << "Get a stripe of paper."  << endl;
    cout << "Fold it into half."  << endl;
    cout << "Fold it into half again in the same direction."  << endl;
    cout << "Fold it the third time into half, in the same direction."  << endl;
    cout << "Then open it, so that every fold is at a right angle."  << endl;
    cout << "Viewing it from the edge you could see a pattern like this:"  << endl;
    cout << "   _"  << endl;
    cout << "|_| |_"  << endl;
    cout << "     _|"  << endl;
    cout << endl;
    cout << "Input:"  << endl;
    cout << "--order 3 --start 3 --end 6"  << endl;
    cout << "--order 5 --start 16 --end 18"  << endl;
    cout << "--order 10 --start 999 --end 1011"  << endl;
    cout << "--order 20 --start 100 --end 111"  << endl;
    cout << "--order 50 --start 562949953421300 --end 562949953421400"  << endl;
    cout << endl;
    cout << "Output:"  << endl;
    cout << "1100"  << endl;
    cout << "110"  << endl;
    cout << "1110010001101"  << endl;
    cout << "100111001000"  << endl;
    cout << "10001100100111011001110010011101100011001001110110011100100011011000110010011101100111001001110110001"  << endl;
    cout << endl;
	return EXIT_SUCCESS;
}

long bin(int order) {
	long c = 0;
	long out = 0;
	while (c < order) {
		out += pow(2, c);
		c += 1;
	}
	return out;
}

void curve(int order, long start, long current, long end, long modulo) {
	if (current <= end + 1) {
		if (current % modulo) {
			compteurProgressBar++;
			string value = "1";
			if (long(ceil(current / modulo)) % 2) {
				value = "0";
			}
			if (file.is_open()) {
				file << value;
			} else {
				cout << value;
			}
		} else {
			curve(order - 1, start, current, end, modulo * 2);
		}
	}
}

void path(int order, long s, long e) {
	long start = s + 1;
	long current = s + 1;
	long end = e;
	long modulo = 2;
	if (file.is_open()) {
		cout << (end - start) + 1;
		cout << " curves in progress..." << endl;
	}
	while (s <= e) {
		curve(order, start, current, end, modulo);
		current += 1;
		s += 1;
	}
	if (!file.is_open())
		cout << endl;
}

int main(int argc, char* argv[]) {

	InputParser input(argc, argv);

	string temp;

	int order;
	long nb;

	if (input.cmdOptionExists("-h") || input.cmdOptionExists("--help")) {
		return help();
	}

	string strO = input.getCmdOption("-o");
	string strOrder = input.getCmdOption("--order");
	string strFinalOrder;
	if (strO != "") {
		strFinalOrder = strO;
	} else if (strOrder != "") {
		strFinalOrder = strOrder;
	} else {
		return help();
	}
	if (sscanf(strFinalOrder.c_str(), "%d", &order) != 1) {
	}
	nb = bin(order);

	if (input.cmdOptionExists("-n") || input.cmdOptionExists("--number")) {
		cout << nb << endl;
		return EXIT_SUCCESS;
	}

	long start = 0;
	long end = nb;

	string strStart = input.getCmdOption("--start");
	if (sscanf(strStart.c_str(), "%ld", &start) != 1) {
	}
	if (start < 0)
		start = 0;

	string strEnd = input.getCmdOption("--end");
	if (sscanf(strEnd.c_str(), "%ld", &end) != 1) {
	}
	if (end > nb)
		end = nb;

	string fileName = input.getCmdOption("--output");
	if (fileName != "") {
		file.open(fileName.c_str());
	}

	path(order, start, end);

	if (file.is_open()) {
		file.close();
		printf("Time taken: %.3fs\n",
				(double) (clock() - tStart) / CLOCKS_PER_SEC);
	}

	return EXIT_SUCCESS;
}
