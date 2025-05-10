package aiss.gitminer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Pull not found")
public class PullRequestNotFoundException extends Exception {
}