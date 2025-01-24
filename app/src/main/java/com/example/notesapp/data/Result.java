package com.example.notesapp.data;

public class Result<T> {
    private Result(){}//made private for limit subclassing and intantiating a result.a result shoul be either success or faliure
    public final static class Success<T> extends Result{
        private T data;
        public Success(T data){
            this.data=data;
        }

        public T getData(){
            return  this.data;
        }
    }

    public final static class Error extends Result{
        private Exception error;
        public Error(Exception error){
            this.error=error;
        }

        public Exception getError(){
            return this.error;
        }
    }
}
