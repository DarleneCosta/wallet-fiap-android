package fiap.com.wallet;

import java.io.IOException;

import okhttp3.Request;
import okio.Timeout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MockCall<T> implements Call<T> {
    public enum ResponseCase {
        success,
        failure
    }

    private T responseData;
    private ResponseCase responseCase;

    public MockCall(ResponseCase responseCase, T responseData) {
        this.responseCase = responseCase;
        this.responseData = responseData;
    }

    @Override
    public Response<T> execute() throws IOException {
        return null;
    }

    @Override
    public void enqueue(Callback<T> callback) {
        if (responseCase == ResponseCase.success)
            callback.onResponse(this, Response.success(responseData));
        else
            callback.onFailure(this, new Throwable("Mock failed successfully"));
    }

    @Override
    public boolean isExecuted() {
        return false;
    }

    @Override
    public void cancel() {
    }

    @Override
    public boolean isCanceled() {
        return false;
    }

    @Override
    public Call<T> clone() {
        return null;
    }

    @Override
    public Request request() {
        return null;
    }

    @Override
    public Timeout timeout() {
        return Timeout.NONE;
    }
}
