package collections.list.scenarios;

import collections.list.scenarios.ecommerce.ShoppingCartListDemo;
import collections.list.scenarios.education.StudentManagementArrayListDemo;
import collections.list.scenarios.media.MusicPlaylistLinkedListDemo;
import collections.list.scenarios.support.SupportTicketVectorDemo;
import collections.list.scenarios.web.BrowserBackStackDemo;

/**
 * Run this class to execute all list scenarios together.
 */
public class ListScenariosMasterDemo {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println(" LIST SCENARIOS MASTER DEMO");
        System.out.println("========================================");

        StudentManagementArrayListDemo.demo();
        MusicPlaylistLinkedListDemo.demo();
        SupportTicketVectorDemo.demo();
        BrowserBackStackDemo.demo();
        ShoppingCartListDemo.demo();

        System.out.println("\nTip:");
        System.out.println("Run each class independently as well to focus on one scenario at a time.");
    }
}
