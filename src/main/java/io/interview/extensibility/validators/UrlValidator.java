package io.interview.extensibility.validators;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by user on 2015-02-01.
 */
public class UrlValidator {
    public static Set<URL> validateParameter(String parameter) throws ValidationException {
        Set<URL> result = new HashSet<>();
        Path path = null;
        URL url = null;
        path = Paths.get(parameter);
        try {
            url = new URL(parameter);
        } catch (MalformedURLException e) {
            // do nothing
        }
        if (path != null) {
            try {
                BufferedReader bufferedReader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
                String urlFromFile = null;
                while ((urlFromFile = bufferedReader.readLine()) != null) {
                    result.add(new URL(urlFromFile));
                }
            } catch (IOException e) {
                throw new ValidationException(e);
            }
        } else if (url != null) {
            result.add(url);
        }
        return result;
    }
}
