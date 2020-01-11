package com.soliduslabs;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class FindPair
{
    static class Item
    {
        String name;
        Integer price;

        Item(String name, Integer price)
        {
            this.name = name;
            this.price = price;
        }
    }

    public static void main(String[] args)
    {
        FindPair m = new FindPair();

        if(args[0].equals("--bonus"))
            m.findTriad(args[1], Integer.parseInt(args[2]));
        else
            m.findPair(args[0], Integer.parseInt(args[1]));
    }

    void findPair(String fileName, int price)
    {
        var items = parseItems(readFile(fileName));

        findPair(items, price).ifPresentOrElse(gifts -> {
            for(Item gift : gifts)
                System.out.println(gift.name + ", " + gift.price);
        }, () -> System.out.println("Not possible."));
    }

    Optional<Item[]> findPair(Item[] items, int total)//O(n)
    {
        var gifts = new Item[2];
        int start, bestPrice, temp; start = bestPrice = 0;
        int end = items.length - 1;

        while(start < end)
        {
            temp = items[start].price + items[end].price;
            if(temp <= total && temp > bestPrice)
            {
                gifts[0] = items[start++];
                gifts[1] = items[end];

                bestPrice = temp;
            }
            else
                end--;
        }

        if(gifts[0] == null)
            return Optional.empty();

        return Optional.of(gifts);
    }

    void findTriad(String fileName, int price)
    {
        var items = parseItems(readFile(fileName));

        findTriad(items, price).ifPresentOrElse(gifts -> {
            for(Item gift : gifts)
                System.out.println(gift.name + ", " + gift.price);
        }, () -> System.out.println("Not possible."));
    }

    Optional<Item[]> findTriad(Item[] items, int total)
    {
        var gifts = new Item[3];
        int start, bestPrice, temp; start = bestPrice = 0;
        int inner = 1;
        int end = items.length - 1;

        while(start < end)
        {
            while(inner < end)
            {
                temp = items[start].price + items[inner].price + items[end].price;
                if(temp <= total && temp > bestPrice)
                {
                    gifts[0] = items[start];
                    gifts[1] = items[inner++];
                    gifts[2] = items[end];

                    bestPrice = temp;
                }
                else if(temp > total)
                    end--;
                else
                    inner++;
            }

            start++;
            inner = start + 1;
        }

        if(gifts[0] == null)
            return Optional.empty();

        return Optional.of(gifts);
    }

    /*
    List<String> readFile(String fileName)
    {
        List<String> lines = null;

        try
        {
            lines = Files.readAllLines(Paths.get(fileName));

        } catch (IOException e) {
            System.err.println("Error reading from file. Exiting the program...");
            System.exit(2);
        }

        return lines;
    }
     */

    List<String> readFile(String fileName)
    {
        var lines = new LinkedList<String>();

        try(BufferedReader reader = Files.newBufferedReader(Paths.get(fileName)))
        {
            String line;
            while((line = reader.readLine()) != null)
            {
                //System.out.println(line);
                lines.add(line);
            }
        } catch(Exception e) {
            System.err.println("Error reading from file. Exiting the program...");
            System.exit(2);
        }

        return lines;
    }

    Item[] parseItems(List<String> stringList)
    {
        var itemList = new Item[stringList.size()];
        for(int x = 0; x < stringList.size(); x++)
        {
            var split = stringList.get(x).split(",[ ]*");//trim spaces
            itemList[x] = (new Item( split[0], Integer.valueOf(split[1]) ));//assuming properly formatted data (e.g. name,price)
        }

        return itemList;
    }
}
