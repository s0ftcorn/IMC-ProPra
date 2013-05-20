#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/types.h>

void error(char *str);

void dieGabel(char *c_ptr);

int main(int argc, char **argv){
	char auswahl;
	while(1){
	// Ausgeben der ersten PID des baldigen Elternprozeses
	printf("Main-PID: %d\n",getpid());
	printf("Bitte waehlen sie\nd fuer dolphin\np für ps\nx für xterm\n");
	/* Falls scanf einen Wert kleiner als Eins zurückgibt, dann ist etwas schief gegangen
	   weil wir einen Char erwarten. Kriegen wir einen, ist der Rückgabewert von scanf 1,
	   ging etwas schief, gibt scanf einen Wert kleiner als Null zurück, d.h. auch hier
	   greift die Fehlerbehandlgun */
	if( scanf(" %c",&auswahl) < 1 ) error("Eingabe fehlgeschlagen!");
	if(auswahl == 'd') dieGabel("dolphin");
	if(auswahl == 'p') dieGabel("ps");
	if(auswahl == 'x') dieGabel("xterm");
	}
	exit(EXIT_SUCCESS);
}

void dieGabel(char *c_ptr){
	int pid = fork();
	// Ausgeben des Rückgabewertes von fork()
	printf("Die Gabel sagt: %d\n",pid);
	// Ist die Variable "pid" größer als 0, so sind wir im Elternprozess und geben dessen PID aus.
	if(pid > 0){
		printf("Eltern-Prozess, PID des Kinds: %d\n",pid);
	// Ansonsten befinden wir uns im Kindprozess und rufen das gewünschte Programm auf
	}else if(pid == 0){
		printf("Im Kind-Prozess, PID %d, PPID %d\n",getpid(),getppid());
		// Laut Konvention bekommt jedes Programm als argv[0] den Programmnamen
		execlp(c_ptr,c_ptr,NULL);
	// Gibt fork() einen Wert kleiner als 0 zurück so ging etwas schief und wir rufen die Error-Funktion auf
	}else if(pid < 0){
		error("Die Gabel scheiterte!");
	// Hier sollten wir nie hinkommen
	}else{
		printf("Universe check failed.");
	}
}

void error(char *str){
	
	perror(str);
	exit(EXIT_FAILURE);
}