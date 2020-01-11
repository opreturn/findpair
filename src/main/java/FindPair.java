import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

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

        m.findPair("./prices.txt", 2500);
    }

    void findPair(String fileName, int price)
    {
        var items = parseItems(readFile(fileName));

        var gifts = findPair(items, price);

        for(Item gift: gifts)
        {
            System.out.println(gift.name + ", " + gift.price);
        }
    }

    Item[] findPair(Item[] items, int total)//O(n)
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

        return gifts;
    }

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