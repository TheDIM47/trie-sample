package search

import trie.Trie

import java.io.File

@main def app(args: String*): Unit = {
  if (args.size == 0) then printUsage
  else if (args.size == 1) then interactive(args.head)
  else search(args.head, args.tail)
}

def printUsage =
  println("Search words in files")
  println("Usage:")
  println("sbt run                  - show help")
  println("sbt \"run <dir>\"          - run in command mode")
  println("sbt \"run <dir> words...\" - run search")
  println("Sample: sbt \"run ./src/test/resources/texts to be or not to be\"")
  
def search(dir: String, words: Seq[String]) =
  println(s"Search dir: $dir")
  println(s"Search for: ${words.toSet.mkString(" ")}")
  val ts: Seq[(String, Trie)] = mkTries(new File(dir))
  val ws = words.filterNot(_.length == 1).toSet  
  ts.foreach { case (name, t) =>
    val res = rank(t, ws)
    println(s"$name:$res%")
  }  

def interactive(dir: String) =
  println(s"Search dir interactive: $dir")
  val ts: Seq[(String, Trie)] = mkTries(new File(dir))
  Iterator.continually({
    Console.out.print(">")  
    Console.in.readLine()
  })
    .takeWhile(_ != ":quit")
    .foreach(line => {
      val ws = line.split("\\s+").filterNot(_.length == 1).toSet
      ts.foreach { case (name, t) =>
        val res = rank(t, ws)
        println(s"$name:$res%")
      }   
    })

def getFileTree(f: File): LazyList[File] =
  f #:: (if (f.isDirectory) f.listFiles().to(LazyList).flatMap(getFileTree) else LazyList.empty)

def mkTries(r: File): LazyList[(String, trie.Trie)] = 
  getFileTree(r).filter(_.isFile).map(f => (f.getName, trie.Trie.mkTrie(f)))

def rank(t: Trie, words: Set[String]): Int =
  val found = words.foldLeft(0)((n, w) => n + (if (t.find(w).nonEmpty) 1 else 0))
  100 * found / words.size
