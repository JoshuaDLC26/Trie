import java.util.ArrayList;

public class TrieNode {

    private String wordHere;

    private TrieNode[] links;

    public TrieNode() {
        wordHere = null;
        links= new TrieNode[26];
    }

    //Convert a letter to a number
    private int let(char c) {
        return c - 'A';
    }

    private char firstChar(String key) {
        return key.charAt(0);
    }

    private String rest(String key) {
        return key.substring(1,key.length());
    }

    private TrieNode linkWordStart(String start) {
        return links[let(firstChar(start))];
    }

    public void insert(String key,String toHere) {
        key = key.toUpperCase();
        if (key.length() == 0) { // Handle end of word
            if (wordHere == null) {
                wordHere = toHere;
            }
        }
            else{
               if(linkWordStart(key) == null){
                   this.links[let(firstChar(key))] = new TrieNode();
                   toHere += key.charAt(0);
                   linkWordStart(key).insert(rest(key), toHere);
               } else if (linkWordStart(key) != null) {
                   toHere += key.charAt(0);
                   linkWordStart(key).insert(rest(key), toHere);
               }
        }

    }
    public boolean anyKids() {
        for (TrieNode child : links) {
            if (child != null) {
                return true;
            }
        }
        return false;
    }


    public TrieNode find(String key, String soFar) {

        /*
         * The second parameter is mostly here to show you something that is useful in
         * other methods
         */

        if (key.length() == 0) {
            if (wordHere == null)
                return null;
            else {
                if (!soFar.equals(wordHere) ) {
                    System.out.println(
                            "Sanity check failure in find - this should never happen!");
                    System.exit(99);
                }
                return this;
            }
        }
        else {
            if (linkWordStart(key) == null)
                return null;
            else return linkWordStart(key).find(rest(key),soFar+key.charAt(0));
        }
    }




    public void spellCheck(ArrayList<String> ws, String key, int errs) {
        if(wordHere != null){
            if(wordHere.length() == key.length()) {
                int c = 0;
                for(int i = 0; i < key.length(); i++){
                    if (wordHere.charAt(i) != key.charAt(i)) {
                        c++;
                    }
                }
                if(errs >= c){
                    ws.add(wordHere);
                }
            }
        }
        if(this.anyKids()) {
            for (int i = 0; i < this.links.length; i++) {
                if (this.links[i] != null) {
                    this.links[i].spellCheck(ws, key, errs);
                }
            }
        }
    }


    public void print(String string) {
        if (wordHere != null)
            System.out.println(string+" "+wordHere);
        else System.out.println(string+" empty");
        for (int i =0; i<26; i++) {
            if (links[i]!=null){
                links[i].print(string+"-");
            }
        }
    }

    public boolean delete(String key) {
        if(key.length() == 1) {
            if (linkWordStart(key).anyKids()) {
                linkWordStart(key).wordHere = null;
                return true;
            }
             else {
                links[let(firstChar(key))] = null;
                return false;}
        }
            linkWordStart(key).delete(rest(key));
             if(linkWordStart(key).anyKids()){
                 return true;
             }
             if(!linkWordStart(key).anyKids()){
                 if (linkWordStart(key).wordHere == null){
                     links[let(firstChar(key))] = null;
                 }
             }
             else if(linkWordStart(key).wordHere !=null){
                 return true;
             }
             return true;
        }

    public ArrayList<String> allKeysWithPrefix(String pre) {
        ArrayList<String> v = new ArrayList<String>();
        ArrayList<String> list = new ArrayList<>();
        allKeys(list);
        int length = pre.length();
        for (int i = 0; i < list.size(); i++) {
            for (int j=0; j < pre.length(); j++){
                if (list.get(i).charAt(j) != pre.charAt(j)){
                    break;
                }
                if (j == length-1){
                    v.add(list.get(i));
                }
            }
        }
        return v;
    }

    public void allKeys(ArrayList<String> v){
        if (wordHere != null){
            v.add(wordHere);
        }
         if (anyKids()){
            for (int i=0; i< links.length; i++){
                if(links[i]!= null){
                    links[i].allKeys(v);
                }
            }
        }
        }
    public ArrayList<String> allLargestPrefix(String pre) {
        ArrayList<String> v = new ArrayList<String>();
        ArrayList<String> list = new ArrayList<>();
        String lp = longestPrefix(pre,"");
        allKeys(list);
        int length = lp.length();
        for (int i = 0; i < list.size(); i++) {
            for (int j=0; j < lp.length(); j++){
                if (list.get(i).charAt(j) != lp.charAt(j)){
                    break;
                }
                if (j == length-1){
                    v.add(list.get(i));
                }
            }
        }

        return v;
    }
    public void allFind(ArrayList<String> result, String target, Trie searchTrie) {
        int n = target.length();
        for (int i = 0; i < n; i++) {
            TrieNode currentNode = this;
            for (int j = i; j < n; j++) {
                char nextChar = target.charAt(j);
                TrieNode nextNode = currentNode.links[let(nextChar)];


                if (nextNode == null) {
                    break;
                }


                currentNode = nextNode;


                // Check if the current node is the end of a word in the searchTrie
                if (currentNode.wordHere != null && searchTrie.find(currentNode.wordHere)) {
                    result.add(currentNode.wordHere);
                }
            }
        }
    }



    public String longestPrefix(String s, String soFar) {
        if (s.length() == 0) {
            return soFar;
        } else {
            if (linkWordStart(s) != null) {
                return linkWordStart(s).longestPrefix(rest(s), soFar + s.charAt(0));
            }
        }
        return soFar;
       }



    public void wildCardMatch(ArrayList<String> v, String wild) {
        if(wild.length() == 0){
            if(wordHere != null && !v.contains(wordHere)){
                v.add(wordHere);
            }
        }
        else if(wild.length() == 1){
            if(wild.charAt(0) == '*'){
                if(anyKids()){
                    for (int i = 0; i < links.length; i++) {
                        if(links[i] != null) {
                            links[i].wildCardMatch(v, wild);
                        }
                    }
                }
                if(wordHere != null && !v.contains(wordHere)){
                    v.add(wordHere);
                }
            }
            else{
                if(linkWordStart(wild) != null){
                    linkWordStart(wild).wildCardMatch(v, rest(wild));
                }
            }
        }
        else if(anyKids()){
            //Accounting For Case Of Multiple * In A Row
            if(wild.charAt(1) == '*'){
                if(wild.charAt(0) == '*'){
                    this.wildCardMatch(v,rest(wild));
                }
                else {
                    if(linkWordStart(wild) != null) {
                        linkWordStart(wild).wildCardMatch(v, rest(wild));
                    }
                }
            }
            else if (wild.charAt(0) == '*') {
                for (int i = 0; i < links.length; i++) {
                    if (links[i] != null) {
                        links[i].wildCardMatch(v, wild);
                    }
                }
                if(linkWordStart(rest(wild)) != null) {
                    linkWordStart(rest(wild)).wildCardMatch(v, rest(rest(wild)));
                }
            }
            else {
                if (linkWordStart(wild)!= null) {
                    linkWordStart(wild).wildCardMatch(v, rest(wild));
                }
            }
        }

    }

    public String longestPrefixWord(String s, String soFar) {
        ArrayList<String> v = new ArrayList<>();
        boolean b1 = true;
        if(wordHere != null){
            String w = wordHere;
            if(w.length() <= s.length()){
                for (int i=0; i< w.length(); i++){
                    if (w.charAt(i) != s.charAt(i)){
                        b1 = false;
                        break;
                    }
                }
                if(b1){
                    v.add(wordHere);
                }
            }
        }
        if(b1){
            if(anyKids()){
                for (int i =0; i < links.length ;i++){
                    if(links[i] != null){
                        links[i].allPrefixMatch(s,v);
                    }
                }
            }
        }
        String tem = " ";
        for (int i=0; i< v.size(); i++){
            if(v.get(i).length()> tem.length()){
                tem = v.get(i);
            }
        }
        return tem;
    }

    public ArrayList<String> allPrefixMatch(String s, ArrayList<String> v) {
        boolean b1 = true;
        if(wordHere != null){
            String w = wordHere;
            if(w.length() <= s.length()){
                for (int i=0; i< w.length(); i++){
                    if (w.charAt(i) != s.charAt(i)){
                        b1 = false;
                        break;
                    }
                }
                if(b1){
                    v.add(wordHere);
                }
            }
        }
                if(b1){
                    if(anyKids()){
                        for (int i =0; i < links.length ;i++){
                            if(links[i] != null){
                                links[i].allPrefixMatch(s,v);
                            }
                        }
                    }
                }
                return v;
            }
        }

