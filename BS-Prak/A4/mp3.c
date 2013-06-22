#include <stdio.h>

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
	/* HIER MUESST IHR NOCH WEITERE VARIABLEN ANLEGEN */
	
	printf("== Datei: %s ==\n\n", dateiname);
	
	/* HIER MUSS EUER CODE EINGEFUEGT WERDEN */
	
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
