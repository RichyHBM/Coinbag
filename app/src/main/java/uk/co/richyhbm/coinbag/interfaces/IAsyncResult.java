package uk.co.richyhbm.coinbag.interfaces;

//Handles an operation on the result of an async task
public interface IAsyncResult<T> {
    void processResult(T result);
}
