package ru.tinkoff.edu.java.linkparser.handlers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Pattern;

import ru.tinkoff.edu.java.linkparser.Request.Request;
import ru.tinkoff.edu.java.linkparser.dtos.GitHubData;
import ru.tinkoff.edu.java.linkparser.dtos.UrlData;

public class GitHubHandler extends AbstractHandler {

    public GitHubHandler(Handler nextHandler) {
        super(nextHandler);
    }

    @Override
    public boolean defineLink(String link) {
        String regex = "^(https?://)?(www\\.)?github\\.com(/.*)$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(link).find();
    }

    @Override
    public UrlData parse(Request request) {
        if (!defineLink(request.link())) {
            return nextHandler == null ? null : nextHandler.parse(request);
        }
        try {
            URI uri = new URI(request.link());
            String[] pathComponents = uri.getPath().split("/");
            if (pathComponents.length >= 3) {
                return new GitHubData(pathComponents[1], pathComponents[2]);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.err.println("Invalid url: " + request.link());
        }
        return null;
    }
}