package gibran.com.br.dribbleservice;


import org.threeten.bp.Clock;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

/**
 * Verbose logging of network calls, which includes path, headers, and times.
 */
public final class LoggingInterceptor implements Interceptor {
    private final Clock clock;
    private Level logLevel;

    public Level getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }

    public enum Level {
        /**
         * No logs.
         */
        NONE,
        /**
         * Logs request and response lines.
         * <p>
         * Example:
         * <pre>{@code
         * --> POST /greeting HTTP/1.1 (3-byte body)
         *
         * <-- HTTP/1.1 200 OK (22ms, 6-byte body)
         * }</pre>
         */
        BASIC,
        /**
         * Logs request and response lines and their respective headers.
         * <p>
         * Example:
         * <pre>{@code
         * --> POST /greeting HTTP/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         * --> END POST
         *
         * <-- HTTP/1.1 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         * <-- END HTTP
         * }</pre>
         */
        HEADERS,
        /**
         * Logs request and response lines and their respective headers and bodies (if present).
         * <p>
         * Example:
         * <pre>{@code
         * --> POST /greeting HTTP/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         *
         * Hi?
         * --> END GET
         *
         * <-- HTTP/1.1 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         *
         * Hello!
         * <-- END HTTP
         * }</pre>
         */
    }

    public LoggingInterceptor(Clock clock) {
        this.clock = clock;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Level logLevel = getLogLevel();
        if (logLevel == Level.HEADERS) {
            Timber.v("Sending request %s%s", request.url(), prettyHeaders(request.headers()));
        } else if (logLevel == Level.BASIC) {
            Timber.v("Sending request %s", request.url());
        }
        long startMs = clock.millis();
        Response response = chain.proceed(request);
        long tookMs = clock.millis() - startMs;
        if (logLevel == Level.HEADERS) {
            Timber.v("Received response (%s) for %s in %sms%s", response.code(),
                    response.request().url(),
                    tookMs, prettyHeaders(response.headers()));
        } else if (logLevel == Level.BASIC) {
            Timber.v("Received response (%s) for %s in %sms", response.code(),
                    response.request().url(),
                    tookMs);
        }
        return response;
    }

    private String prettyHeaders(Headers headers) {
        if (headers.size() == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("\n  Headers:");
        for (int i = 0; i < headers.size(); i++) {
            builder.append("\n    ").append(headers.name(i)).append(": ").append(headers.value(i));
        }
        return builder.toString();
    }
}
