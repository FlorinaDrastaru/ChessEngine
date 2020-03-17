JFLAGS = -g -sourcepath src
JC = javac
JVM = java

.SUFFIXES: .java .class

.java.class: $(JC) $(JFLAGS) $*.java

clean:
	rm -rf *.class

build:
	javac -cp . com/main/Main.java

run:
	java -cp . com/main/Main
