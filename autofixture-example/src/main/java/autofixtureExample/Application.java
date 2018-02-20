package autofixtureExample;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        Consumer consumer = new Consumer();

        List<Example> examples = consumer.generateExample();

        for (Example example: examples) {
            System.out.println(String.format("Id:\t\t\t\t\t\t\t%s", example.getId()));
            System.out.println(String.format("Description:\t\t\t\t%s", example.getDescription()));
            System.out.println(String.format("IsExample:\t\t\t\t\t%s", example.getIsExample()));
            System.out.println(String.format("SubExampleList:\t\t\t\t%s", example.getSubExampleList().size()));

            System.out.println("SubExample:");
            System.out.println(String.format("\tId:\t\t\t\t\t\t%s", example.getSubExample().getId()));
            System.out.println(String.format("\tAnotherDescription:\t\t%s", example.getSubExample().getAnotherDescription()));

            System.out.println();
        }

    }
}
