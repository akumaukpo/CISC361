#include <stdio.h>
#include <stdlib.h>
#define INPUTFILE "projectInput.txt"

int currentTime = 0;

// Shortest Job First
typedef struct hold1{
	// data for each waiting process in holding queue 1
	struct hold1 * next;
} hold1_t;

// First in First out
typedef struct hold2{
	// data for each waiting process in holding queue 2
	struct hold2 * next;
} hold2_t;

void systemConfiguration(char* buffer){
	// set system configuration
}

void jobArrival(char* buffer){
	// handle new job
}

void deviceRequest(char* buffer){
	// handle device request
}

void deviceRelease(char* buffer){
	// handle device release
}

void display(char* buffer){
	// display the system in a readable format
}

void parseLine(char* buffer){
	switch(buffer[0]){
		case 'C':
			systemConfiguration(buffer);
			break;
		case 'A':
			jobArrival(buffer);
			break;
		case 'Q':
			deviceRequest(buffer);
			break;
		case 'L':
			deviceRelease(buffer);
			break;
		case 'D':
			display(buffer);
			break;
	}
}

int main(){
	FILE *file = fopen(INPUTFILE, "r");
	if(NULL == file)
    {
        fprintf(stderr, "Cannot open file: %s\n", INPUTFILE);
        return 1;
    }
	size_t buffer_size = 30;
    char *buffer = malloc(buffer_size * sizeof(char));
    
    while(-1 != getline(&buffer, &buffer_size, file)){
		    printf("%s", buffer);  
		    parseLine(buffer);  
    }

    fflush(stdout);
	fclose(file);
	free(buffer);
	return 0;
}