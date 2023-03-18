package ai.openfabric.api.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(UserAlreadyExistException.class)
//    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(final UserAlreadyExistException ex){
//        ErrorResponse errorResponse = new ErrorResponse();
//        errorResponse.setMessage(ex.getMessage());
//        errorResponse.setStatus(HttpStatus.BAD_REQUEST);
//        errorResponse.setDebugMessage("User already exists");
//        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
//    }
//
//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<ErrorResponse> handleUserNotFoundException(final UserNotFoundException ex){
//        ErrorResponse errorResponse = new ErrorResponse();
//        errorResponse.setMessage(ex.getMessage());
//        errorResponse.setStatus(HttpStatus.NOT_FOUND);
//        errorResponse.setDebugMessage("User not found");
//        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
//    }
//
//    @ExceptionHandler(PasswordIncorrectException.class)
//    public ResponseEntity<ErrorResponse> handlePasswordIncorrectException(final PasswordIncorrectException ex){
//        ErrorResponse errorResponse = new ErrorResponse();
//        errorResponse.setMessage(ex.getMessage());
//        errorResponse.setStatus(HttpStatus.BAD_REQUEST);
//        errorResponse.setDebugMessage("Password incorrect");
//        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
//    }
//
//    @ExceptionHandler(PostsNotFoundException.class)
//    public ResponseEntity<ErrorResponse> handlePostsNotFoundException(final PostsNotFoundException ex){
//        ErrorResponse errorResponse = new ErrorResponse();
//        errorResponse.setMessage(ex.getMessage());
//        errorResponse.setStatus(HttpStatus.NOT_FOUND);
//        errorResponse.setDebugMessage("This post is not available");
//        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
//    }
//
//    @ExceptionHandler(CategoryNotFoundException.class)
//    public ResponseEntity<ErrorResponse> handleCategoryNotFoundException(final CategoryNotFoundException ex){
//        ErrorResponse errorResponse = new ErrorResponse();
//        errorResponse.setMessage(ex.getMessage());
//        errorResponse.setStatus(HttpStatus.NOT_FOUND);
//        errorResponse.setDebugMessage("This category is not available");
//        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
//    }
}
