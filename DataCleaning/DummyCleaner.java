import java.util.Scanner;

// Example of cleaner.
public class DummyCleaner {
	public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
	int i = 0;
	while(sc.hasNext())
	{
		String input = sc.nextLine();
		System.out.println("# " + input);
	}
	}
}