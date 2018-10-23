
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Iterator;

class SiteStats {
    private String url;
    private int numVisits;
    public SiteStats(String url, int numVisits) {
        this.url = url;
        this.numVisits = numVisits;
    }
    public int getNumVisits() {
        return this.numVisits;
    }
    public String getUrl() {
        return this.url;
    }
    public void setNumVisits(int updatedNumVisits) {
        this.numVisits = updatedNumVisits;
    }
    public String toString() {
        return this.url + " | " + this.numVisits;
    }
}

public class SolutionB {

    private static Queue<SiteStats> sites = new LinkedList<SiteStats>();

    // Queue elements after sortIndex are
    // already sorted. This function returns
    // index of minimum element from front to
    // sortIndex
    public static int maxIndex(Queue<SiteStats> list,
                               int sortIndex)
    {
        int max_index = -1;
        int max_value = 0;
        int s = list.size();
        for (int i = 0; i < s; i++)
        {
            SiteStats current = list.peek();

            // This is dequeue() in Java STL
            list.poll();

            // we add the condition i <= sortIndex
            // because we don't want to traverse
            // on the sorted part of the queue,
            // which is the right part.
            if (current.getNumVisits() >= max_value && i <= sortIndex)
            {
                max_index = i;
                max_value = current.getNumVisits();
            }
            list.add(current);
        }
        return max_index;
    }

    // Moves given minimum element
    // to rear of queue
    public static void insertMaxToRear(Queue<SiteStats> list,
                                       int min_index)
    {
        int s = list.size();
        SiteStats maxValue = list.peek();
        for (int i = 0; i < s; i++)
        {
            SiteStats current = list.peek();
            list.poll();
            if (i != min_index)
                list.add(current);
            else
                maxValue = current;
        }
        list.add(maxValue);
    }

    public static void sortQueue(Queue<SiteStats> list)
    {
        for(int i = 1; i <= list.size(); i++)
        {
            int min_index = maxIndex(list,list.size() - i);
            insertMaxToRear(list, min_index);
        }
    }

    // Main method to list top n visited sites
    public static void listTopVisitedSites(Queue<SiteStats> sites, int n) {
        // WRITE CODE HERE
        sortQueue(sites);
        if (!sites.isEmpty()){
            Iterator it = sites.iterator();
            int count = 1;
            while (it.hasNext() && count <= n) {
                SiteStats e = (SiteStats) it.next();
                System.out.println(e.toString());
                count++;
            }
        }
    }

    // Method to find the website in the queue and increment the visited count by 1, adding new node in case website is not found
    public static void updateCount(String url) {
        // WRITE CODE HERE
        if (!sites.isEmpty()){
            Iterator it = sites.iterator();
            Boolean isExists = false;
            while (it.hasNext()) {
                SiteStats e = (SiteStats) it.next();
                if (e.getUrl().equals(url)) {
                    isExists = true;
                    e.setNumVisits(e.getNumVisits()+1);
                }
            }
            if (!isExists){
                sites.add(new SiteStats(url, 1));
            }
        }else {
            sites.add(new SiteStats(url, 1));
        }
    }

    public static void main(String[] args) {
        String[] visitedSites = { "www.google.co.in", "www.google.co.in", "www.facebook.com", "www.upgrad.com", "www.google.co.in", "www.youtube.com",
                "www.facebook.com", "www.upgrad.com", "www.facebook.com", "www.google.co.in", "www.microsoft.com", "www.9gag.com", "www.netflix.com",
                "www.netflix.com", "www.9gag.com", "www.microsoft.com", "www.amazon.com", "www.amazon.com", "www.uber.com", "www.amazon.com",
                "www.microsoft.com", "www.upgrad.com" };

        for (String url : visitedSites) {
            updateCount(url);
        }
        listTopVisitedSites(sites, 5);

    }

}
