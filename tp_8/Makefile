SRC = $(wildcard src/*.java src/**/*.java)
DIST = dist

all: compile

compile:
	mvn compile

run:
	mvn exec:java

build-run: compile run

package:
	mvn package

run-jar:
	java -jar target/user-crud-1.0-SNAPSHOT-jar-with-dependencies.jar

clean:
	mvn clean
	rm -rf $(DIST)

.PHONY: all compile run build-run package run-jar clean db-up db-down db-logs db-reset db-shell
