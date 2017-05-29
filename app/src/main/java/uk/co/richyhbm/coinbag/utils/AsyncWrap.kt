package uk.co.richyhbm.coinbag.utils

import android.os.AsyncTask

class AsyncWrap<T>(
        val task: () -> T?,
        val onSuccess: (T?) -> Any = { },
        val onFailed: (ex: Exception?) -> Any = {}
) : AsyncTask<Void, Void, T?>() {
    private var exception: Exception? = null

    override fun doInBackground(vararg params: Void): T? {
        try {
            val r = task()
            return r
        } catch (e: Exception) {
            exception = e
            return null
        }
    }

    override fun onPostExecute(result: T?) {
        if (!isCancelled) {
            if (exception != null) {
                onFailed(exception)
            } else {
                onSuccess(result)
            }
        } else {
            onFailed(RuntimeException("Task was cancelled"))
        }
    }
}