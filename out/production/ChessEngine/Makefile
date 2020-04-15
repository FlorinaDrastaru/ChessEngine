JFLAGS = -g -sourcepath src
JVC = javac
JV = java



.SUFFIXES: .java .class

.java.class: $(JVC) $(JFLAGS) $com/commands/*.java com/board/*.java com/constants/*.java com/game/*.java com/main/*.java com/pieces/*.java

clean:
	rm -rf com/commands/*.class com/board/*.class com/constants/*.class com/game/*.class com/main/*.class com/pieces/*.class

build:
	javac -cp . com/main/Main.java

run:
	xboard -fcp "java -cp . com/main/Main" -debug
