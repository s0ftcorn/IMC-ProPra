#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

struct id3tag {
	char title[31];
	char artist[31];
	char album[31];
	char year[5];
	char comment[30];
	unsigned char tracknumber;
	unsigned char genre;
};


static void metadaten_ausgabe(char *);
static void id3tag_ausgabe(struct id3tag *);

static char* get_genre(unsigned char);

int main(int argc, char **argv) {
	int i;
	
	/* IN AUFGABENTEIL B MUSS AUCH NOCH DIE MAIN-FUNKTION ERWEITERT WERDEN */	
	
	if(argc <= 1) {
		printf("Es muss mindestens ein Dateiname angegeben werden\n");
		return 1;
	}
	for(i = 1; i < argc; ++i)
		metadaten_ausgabe(argv[i]);
	
	return 0;
}

static void metadaten_ausgabe(char *dateiname) {
	struct id3tag tag;
	int r_fd = 0;
        ssize_t r_bytes = 0;
        char buf[128] = {0};
        int offset = 0;
        int i = 0;
        
        printf("== Datei: %s ==\n\n", dateiname);
        
        r_fd = open(dateiname,O_RDONLY);
        if(r_fd == -1){
                perror("Fehler beim oeffnen der Datei\n");
        }
        
        int retval = lseek(r_fd,-128L,SEEK_END);
        if(retval == -1){
                perror("Fehler beim setzen des Filedeskriptor\n");
        }
        
        r_bytes = read(r_fd,buf,128);
	if(r_bytes < 128 && r_bytes != -1){
                printf("Evtl. ging etwas schief\n");
	}else if(r_bytes == -1){
                perror("Fehler beim lesen.\n");
	}
	
	if( close(r_fd) == -1 ){
                perror("Fehler beim schließen der Datei.\n");
	}
	
	char temp[3] = {0};
        for(i = offset; i < offset+3; i++){
                temp[i] = buf[i];
        }
        temp[3] = '\0';
        
        if(strcmp("TAG",temp) != 0){
                printf("Konnte \"TAG\" nicht erkennen.\n");
        }
        
        offset = 3;
        for(i = 0; i < 31; i++){
                tag.title[i] = buf[offset+i];
        }
        tag.title[31] = '\0';
        
        offset= 33;
        for(i = 0; i < 31; i++){
                tag.artist[i] = buf[offset+i];
        }
        tag.artist[31] = '\0';
        
        offset = 63;
        for(i = 0; i < 031; i++){
                tag.album[i] = buf[offset+i];
        }
        tag.album[31] = '\0';
        
        offset = 93;
        for(i = 0; i < 4; i++){
                tag.year[i] = buf[offset+i];
        }
        tag.year[4] = '\0';

        offset = 97;
        for(i = 0; i < 30; i++){
                tag.comment[i] = buf[offset+i];
        }
        tag.comment[30] = '\0';
        
        tag.tracknumber = buf[126];
        tag.genre = buf[127];
	
	/* AUSGABE */
	id3tag_ausgabe(&tag);
}

static void id3tag_ausgabe(struct id3tag *tag) {
	printf(
		"Titel:     %s\n"
		"Künstler:  %s\n"
		"Album:     %s\n"
		"Jahr:      %s\n"
		"Kommentar: %s\n"
		"Nummer:    %d\n"
		"Genre:     %s\n\n",
		tag->title,
		tag->artist,
		tag->album,
		tag->year,
		tag->comment,
		tag->tracknumber,
		get_genre(tag->genre)
	);
}

static char *genres[80];

/* SUCHT DAS GENRE AUS FOLGENDER TABELLE HERAUS, PARAMETER IST DAS GENRE-BYTE */
static char *get_genre(unsigned char index) {
	if(index >= sizeof(genres) / sizeof(genres[0]))
		return "undefined";
	else
		return genres[index];
}

static char *genres[80] = {
	"Blues",
	"Classic Rock",
	"Country",
	"Dance",
	"Disco",
	"Funk",
	"Grunge",
	"Hip-Hop",
	"Jazz",
	"Metal",
	"New Age",
	"Oldies",
	"Other",
	"Pop",
	"Rhythm and Blues",
	"Rap",
	"Reggae",
	"Rock",
	"Techno",
	"Industrial",
	"Alternative",
	"Ska",
	"Death Metal",
	"Pranks",
	"Soundtrack",
	"Euro-Techno",
	"Ambient",
	"Trip-Hop",
	"Vocal",
	"Jazz&Funk",
	"Fusion",
	"Trance",
	"Classical",
	"Instrumental",
	"Acid",
	"House",
	"Game",
	"Sound Clip",
	"Gospel",
	"Noise",
	"Alternative Rock",
	"Bass",
	"Soul",
	"Punk",
	"Space",
	"Meditative",
	"Instrumental Pop",
	"Instrumental Rock",
	"Ethnic",
	"Gothic",
	"Darkwave",
	"Techno-Industrial",
	"Electronic",
	"Pop-Folk",
	"Eurodance",
	"Dream",
	"Southern Rock",
	"Comedy",
	"Cult",
	"Gangsta",
	"Top 40",
	"Christian Rap",
	"Pop/Funk",
	"Jungle",
	"Native US",
	"Cabaret",
	"New Wave",
	"Psychedelic",
	"Rave",
	"Showtunes",
	"Trailer",
	"Lo-Fi",
	"Tribal",
	"Acid Punk",
	"Acid Jazz",
	"Polka",
	"Retro",
	"Musical",
	"Rock & Roll",
	"Hard Rock"
};
