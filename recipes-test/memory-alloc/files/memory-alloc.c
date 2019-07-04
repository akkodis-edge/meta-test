#include <stdio.h>
#include <stdlib.h>
#include <inttypes.h>
#include <string.h>
#include <errno.h>
#include <unistd.h>

int main(int argc, char* argv[])
{
	if (argc < 2) {
		printf("memory-alloc, memory allocation test tool, Data Respons Solutions AB\n");
		printf("Usage:   memory-alloc [SIZE] [HOLD(s)]\n");

		return 1;
	}

	char *endptr = NULL;

	uintmax_t size = strtoumax(argv[1], &endptr, 0);
	if (errno) {
		fprintf(stderr, "SIZE out of range: %s: %s\n", argv[1], strerror(errno));
		return 1;
	}

	uint8_t *mem = NULL;
	mem = malloc(size);
	if (!mem) {
		fprintf(stderr, "failed allocating %ju bytes\n", size);
		return 1;
	}

	memset(mem, 1, size);


	if (argc > 2) {
		uintmax_t hold = strtoumax(argv[2], &endptr, 0);
		if (errno) {
			fprintf(stderr, "HOLD out of range: %s: %s\n", argv[2], strerror(errno));
			return 1;
		}
		sleep(hold);
	}

	free(mem);

	return 0;
}
