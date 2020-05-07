package pl.mbierut;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TransactionHistory {
    private List<String> list;

    public void addTransaction(String transaction){
        Pattern pattern = Pattern.compile("^Error");
        Matcher matcher = pattern.matcher(transaction);
        if (!matcher.matches()){
            list.add(transaction);
        }
    }

    public TransactionHistory() {
        this.list = new ArrayList<>();
        this.list.add(LocalDateTime.now() + ": Account created");
    }

    @Override
    public String toString() {
        return this.list.toString();
    }
}
