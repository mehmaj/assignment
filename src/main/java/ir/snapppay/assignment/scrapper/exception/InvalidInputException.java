package ir.snapppay.assignment.scrapper.exception;

import org.springframework.http.HttpStatus;

public class InvalidInputException extends Exception {


        // Custom error message
        private String message;

        // Custom error code representing an error in system
        private HttpStatus httpStatus;

        public InvalidInputException (String message) {
            super(message);
            this.message = message;
            this.httpStatus = HttpStatus.BAD_REQUEST;
        }


        @Override
        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public HttpStatus getHttpStatus() {
            return this.httpStatus;
        }

    }

