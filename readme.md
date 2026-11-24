running from cmd

```
javac -d bin -sourcepath ./ $(find ./ -name "*.java")
java -cp bin ArenaTracker
```