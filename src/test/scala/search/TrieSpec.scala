package search

import org.scalatest._
import flatspec._
import matchers._

class TrieSpec extends AnyFlatSpec with must.Matchers {
  import scala.language.postfixOps

  "Trie" should "build and search words" in {
    val t = trie.Trie()
    t.append("abc")
    t.append("abd")

    t.find("abc") must contain ("abc")
    t.find("abx").size mustBe 0
  }

  it should "make trie from string" in {
    val t = trie.Trie.mkTrie("abc abd qwe rty")

    t.find("abc") must contain ("abc")
    t.find("qwe") must contain ("qwe")
    
    t.find("abx").size mustBe 0
    t.find("wwe").size mustBe 0
  }
  
  it should "make trie from file 1" in {
    val f = new java.io.File(this.getClass.getResource("/test1.txt").getFile)
    val t = trie.Trie.mkTrie(f)
    
    t.find("not") must contain ("not")
    t.find("question") must contain ("question")
    
    t.find("hot").size mustBe 0
    t.find("a").size mustBe 0
  }
  
  it should "make trie from file 2" in {
    val f = new java.io.File(this.getClass.getResource("/test2.txt").getFile)
    val t = trie.Trie.mkTrie(f)
    
    t.find("scALa") must contain ("Scala")
    t.find("hotel").size mustBe 0
  }
}
