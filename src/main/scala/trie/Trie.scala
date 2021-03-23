package trie

sealed trait Trie:
  def append(s: String): Unit
  def find(s: String): Seq[String]

object Trie {
  def apply(): Trie = new TrieNode(None, None)

  def mkTrie(s: String): Trie = {
    val t = Trie()
    ingestLine(t)(s)
    t
  }

  def mkTrie(f: java.io.File): Trie = {
    val t = Trie()
    scala.io.Source.fromFile(f)
      .getLines
      .foreach(ingestLine(t))
    t
  }
  
  private def ingestLine(t: Trie)(s: String): Unit =
    s.split("\\s+")
      .filterNot(_.length == 1)
      .toSet
      .foreach(t.append)
}

private[trie] class TrieNode
(
  val ch: Option[Char], 
  var word: Option[String]
) extends Trie: 
  private[this] val childs = scala.collection.mutable.Map[Char, TrieNode]()

  override def append(s: String): Unit = {
    @annotation.tailrec 
    def go(n: TrieNode, idx: Int): Unit =
      if (idx == s.length) then n.word = Some(s)
      else 
        val char = s.charAt(idx).toLower
        val result = n.childs.getOrElseUpdate(char, TrieNode(char))
        go(result, idx + 1)

    go(this, 0)
  }

  override def find(s: String): Seq[String] = {
    @annotation.tailrec 
    def go(idx: Int, node: TrieNode, res: List[String]): List[String] =
      if (idx == s.length) then node.word.getOrElse("") :: res
      else
        node.childs.get(s.charAt(idx).toLower) match {
          case Some(child) => go(idx + 1, child, res)
          case None => res
        }

    go(0, this, Nil)
  }


private[trie] object TrieNode {
  def apply(): TrieNode = new TrieNode(None, None)
  def apply(ch: Char): TrieNode = new TrieNode(Some(ch), None)
}
