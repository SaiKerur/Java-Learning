package collections.list.scenarios.media;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Scenario: Build a music playlist where songs are added/removed
 * at front and end frequently.
 *
 * Why LinkedList here?
 * - Efficient add/remove near ends.
 * - Useful for queue/deque-like playlist operations.
 */
public class MusicPlaylistLinkedListDemo {

    public static void demo() {
        System.out.println("\n--- SCENARIO: MUSIC PLAYLIST (LINKEDLIST) ---");

        LinkedList<String> playlist = new LinkedList<>();

        playlist.add("Believer");
        playlist.add("Numb");
        playlist.add("Shape of You");

        // Add urgent song for next playback at front.
        playlist.addFirst("Imagine");
        // Add a song at end.
        playlist.addLast("Perfect");

        System.out.println("Current playlist: " + playlist);
        System.out.println("Now playing: " + playlist.peekFirst());
        System.out.println("Last in queue: " + playlist.peekLast());

        // Simulate user skipping the current song.
        String skipped = playlist.removeFirst();
        System.out.println("Skipped: " + skipped);
        System.out.println("After skip: " + playlist);

        // Traverse in order.
        System.out.println("Play order:");
        ListIterator<String> iterator = playlist.listIterator();
        while (iterator.hasNext()) {
            System.out.println("- " + iterator.next());
        }
    }

    public static void main(String[] args) {
        demo();
    }
}
