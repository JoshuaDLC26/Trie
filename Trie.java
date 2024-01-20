import java.util.ArrayList;

public class Trie  {

    TrieNode root = new TrieNode();

    public void insert(String key) {
        root.insert(key,"");
    }

    public boolean delete(String key) {
        if (root.find(key,"")==null)
            return false;
        else root.delete(key);
        return true;
    }

    public boolean find(String key) {

        TrieNode w = root.find(key,"");
        if (w==null)
            return false;
        else return true;
    }


    public ArrayList<String> allPrefixMatch(String s) {
        ArrayList<String> v = new ArrayList<String>();
        return root.allPrefixMatch(s,v);


    }
    public String longestPrefix(String s) {

        return root.longestPrefix(s,"");

    }

    public String longestPrefixWord(String s) {

        return root.longestPrefixWord(s,"");

    }

    public ArrayList<String> allKeys() {
        ArrayList<String> v = new ArrayList<String>();
        root.allKeys(v);
        return v;
    }

    public ArrayList<String> allKeysWithPrefix(String pre) {
        ArrayList<String> v = new ArrayList<String>();
        v = root.allKeysWithPrefix(pre);
        return v;
    }



    public ArrayList<String> allLargestPrefix(String pre) {
        ArrayList<String> v = new ArrayList<String>();
        v= root.allLargestPrefix(pre);
        return v;
    }




    public ArrayList<String> allFind(String target, ArrayList<String> searchFor) {
        Trie searchTrie = constructTrie(searchFor);
        ArrayList<String> result = new ArrayList<>();
        root.allFind(result, target, searchTrie);
        return result;
    }


    private Trie constructTrie(ArrayList<String> words) {
        Trie trie = new Trie();
        for (String word : words) {
            trie.insert(word);
        }
        return trie;
    }


    public ArrayList<String> wildCardMatch(String start) {
        ArrayList<String> v = new ArrayList<String>();
        root.wildCardMatch(v,start);
        return v;
    }

    public ArrayList<String> spellCheck(String start,int dist) {
        ArrayList<String> v = new ArrayList<String>();
        root.spellCheck(v,start,dist);
        return v;
    }



    public void print() {
        root.print("");
    }
    public static void main(String[] args) {

        System.out.println("VVV"+"V".substring(1));
        Trie t = new Trie();
        t.insert("HELLO");
        t.insert("WHY");;
        t.insert("HELLOR");
        t.insert("HELLO");
        t.insert("MEZZO");
        t.insert("MEZZA");
        t.insert("A");
        t.insert("HE");
        t.insert("HIM");
        t.print();

        System.out.println(t.find("HELLO"));
        System.out.println(t.find("HELLOR"));
        System.out.println(t.find("WHY"));

        ArrayList<String> ws = t.allKeys();
        System.out.println("All Keys");
        for (String w: ws) {
            System.out.println("AK "+w);
        }

        System.out.println("Prefix Match");
        ws = t.allPrefixMatch("HELLOS");
        for (String w: ws) {
            System.out.println("PM "+w);
        }
        System.out.println("Longest Prefix");
        String ws1 = t.longestPrefix("HELLA");
        System.out.println(ws1);

        System.out.println("Longest Prefix");
        ws1 = t.longestPrefix("HELLOS");
        System.out.println(ws1);

        System.out.println("Longest Prefix Word");
        ws1 = t.longestPrefixWord("HELLOS");
        System.out.println(ws1);

        System.out.println("Longest Prefix Word");
        ws1 = t.longestPrefixWord("HEL");
        System.out.println(ws1);



        System.out.println("All Keys With Prefix");
        ws = t.allKeysWithPrefix("HE");
        for (String w: ws) {
            System.out.println("AKP "+w);
        }

        System.out.println("All Largest Prefix");
        ws = t.allLargestPrefix("HEZZO");
        for (String w: ws) {
            System.out.println("ALP "+w);
        }

        System.out.println("Spell Check");
        ws = t.spellCheck("HAZZO",1);
        for (String w: ws) {
            System.out.println("SC "+w);
        }

        System.out.println("Spell Check");
        ws = t.spellCheck("HEZZO",1);
        for (String w: ws) {
            System.out.println("SC "+w);
        }

        System.out.println("Wild Card Match");
        ws = t.wildCardMatch("M*Z*");
        for (String w: ws) {
            System.out.println("WCM "+w);
        }


        System.out.println(t.delete("WHY"));
        t.print();

    }


}