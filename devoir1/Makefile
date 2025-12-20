SRC = $(wildcard src/*.java)
DIST = dist

all: $(DIST)
	javac -d $(DIST) -sourcepath src $(SRC)

$(DIST):
	mkdir -p $(DIST)

run-calc: all
	java -cp $(DIST) CalculStat

run-test: all
	java -cp $(DIST) TestPersonne

run-gui: all
	java -cp $(DIST) GestionPersonnesGUI

clean:
	rm -rf $(DIST)

.PHONY: all clean run-calc run-test run-gui