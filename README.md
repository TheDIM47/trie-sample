## Search sample project compiled with Scala 3

### Usage

This is a normal sbt project. You can compile code with `sbt compile`, run it with `sbt run`.

#### Search words in files.

This should read all the text files in the given directory, building an in-memory representation of the files
and their contents, and then give a command prompt at which interactive searches can be performed.
The search should take the words given on the prompt and return a list of the top 10 (maximum)
matching filenames in rank order, giving the rank score against each match.
Note: single char words (like `'a'` or `'1'`) will be ignored

#### Quickstart

1. Clone repository
2. cd %search%
3. `sbt run`

`sbt run` will print short help file

#### Command mode

Usage: `sbt "run <dir>"`
then application give a command prompt at which interactive searches can be performed.

```
> to be or not to be
file1.txt:100%
file2.txt:90%
>
> cats
no matches found
> :quit
```

#### Auto mode

Usage: `sbt "run <dir> word {more search words}"`

Sample: `sbt "run ./src/test/resources/texts to be or not to be"`
