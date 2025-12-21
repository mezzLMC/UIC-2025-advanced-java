SRC = $(wildcard src/*.java src/**/*.java)
DIST = dist

all: $(DIST)
	javac -d $(DIST) $(SRC)

$(DIST):
	mkdir -p $(DIST)

clean:
	rm -rf $(DIST)
