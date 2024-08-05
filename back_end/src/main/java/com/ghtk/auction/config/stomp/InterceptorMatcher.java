package com.ghtk.auction.config.stomp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

/**
 * Matches destinations of pattern '/path/{id}' into a map of variables.
 */
public class InterceptorMatcher {
  private final Optional<StompCommand> command;
  private final Optional<List<SegMatcher>> segMatchers;

  public InterceptorMatcher() {
    this(Optional.empty(), Optional.empty());
  }

  public InterceptorMatcher(String pattern) {
    this(Optional.of(pattern), Optional.empty());
  }

  public InterceptorMatcher(StompCommand command) {
    this(Optional.empty(), Optional.of(command));
  }

  public InterceptorMatcher(String pattern, StompCommand command) {
    this(Optional.of(pattern), Optional.of(command));
  }

  public InterceptorMatcher(Optional<String> pattern, Optional<StompCommand> command) {
    this.segMatchers = pattern
        .map(str -> {
            if (!str.startsWith("/")) {
              throw new IllegalArgumentException("Pattern must start with '/'");
            }
            if (!str.equals("/") && str.endsWith("/")) {
              throw new IllegalArgumentException("Pattern must not end with '/'");
            }
            return compilePattern(str);
        });
    this.command = command;
  }

  public Optional<Map<String, String>> tryMatch(StompHeaderAccessor headers) {
    if (headers.getCommand() != this.command.orElse(headers.getCommand())) {
      return Optional.empty();
    }
    String destination = headers.getDestination();
    if (destination == null && this.segMatchers.isPresent()) {
      return Optional.empty();
    }
    if (destination == null) {
      return Optional.of(Map.of());
    }
    Map<String, String> variables = new HashMap<>();
    for (SegMatcher segMatcher : this.segMatchers.get()) {
      Optional<String> remaining = segMatcher.match(destination, variables);
      if (remaining.isEmpty()) {
        return Optional.empty();
      }
      destination = remaining.get();
    }
    if (destination.isEmpty() || destination.equals("/")) {
      return Optional.of(variables);
    } else {
      return Optional.empty();
    }
  }

  private static List<SegMatcher> compilePattern(String pattern) {
    List<SegMatcher> segMatchers = new ArrayList<>();
    String patternLeft = pattern;
    while (!patternLeft.isEmpty()) {
      if (patternLeft.startsWith("{")) {
        int end = patternLeft.indexOf('}');
        if (end == -1) {
          throw new IllegalArgumentException("Invalid pattern: " + pattern);
        }
        String varName = patternLeft.substring(1, end);
        segMatchers.add(new VarSegMatcher(varName));
        patternLeft = patternLeft.substring(end + 1);
      } else {
        int end = lenUntil(patternLeft, '{');
        segMatchers.add(new StaticSegMatcher(patternLeft.substring(0, end)));
        patternLeft = patternLeft.substring(end);
      }
    }
    return segMatchers;
  }

  private static interface SegMatcher {
    Optional<String> match(String segment, Map<String, String> variables);
  }


  private static class StaticSegMatcher implements SegMatcher {
    private final String dest;

    public StaticSegMatcher(String dest) {
      if (!dest.startsWith("/")) {
        throw new IllegalArgumentException("Static segment must start with '/'");
      }
      this.dest = dest;
    }

    @Override
    public Optional<String> match(String segment, Map<String, String> variables) {
      if (segment.startsWith(this.dest)) {
        return Optional.of(segment.substring(this.dest.length()));
      } else {
        return Optional.empty();
      }
    }
  }

  private static class VarSegMatcher implements SegMatcher {
    private final String varName;

    public VarSegMatcher(String varName) {
      this.varName = varName;
    }

    @Override
    public Optional<String> match(String segment, Map<String, String> variables) {
      int len = lenUntil(segment, '/');
      if (len == 0) {
        return Optional.empty();
      }
      variables.put(this.varName, segment);
      return Optional.of(segment.substring(len));
    }

  }

  private static int lenUntil(String segment, char c) {
    int len = segment.indexOf(c);
    return len == -1 ? segment.length() : len;
  }
}
