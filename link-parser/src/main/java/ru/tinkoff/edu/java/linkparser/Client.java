package ru.tinkoff.edu.java.linkparser;

public class Client {
    public static void main(String[] args) {
        AbstractHandler handler = new GitHubHandler(new StackOverflowHandler(null));

        handler.handleRequest(new Request("https://github.com/polis-vk/2022-ads/xuy"));
    }
}
