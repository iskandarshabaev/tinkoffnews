package io.wape.tinkoffnews.screens

import android.app.Activity
import android.support.annotation.IdRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import java.io.IOException
import java.net.UnknownHostException

fun Fragment.handleError(throwable: Throwable) {
    when (throwable) {
        is IOException -> showErrorMessage("Проблемы с сетью")
        is UnknownHostException -> showErrorMessage("Проблемы с сетью")
        else -> {
            showErrorMessage("Произошла неизвестная ошибка")
        }
    }
}

fun Fragment.handleError(throwable: Throwable, @IdRes resId: Int, listener: View.OnClickListener): Snackbar {
    when (throwable) {
        is IOException -> return showErrorMessage("Проблемы с сетью", resId, listener)
        is UnknownHostException -> return showErrorMessage("Проблемы с сетью", resId, listener)
        else -> {
            return showErrorMessage("Произошла неизвестная ошибка", resId, listener)
        }
    }
}

inline fun <reified T : View> Activity.findOptional(@IdRes id: Int): T? = findViewById(id)

/**
 * Returns the content view of this Activity if set, null otherwise.
 */
inline val Activity.contentView: View?
    get() = findOptional<ViewGroup>(android.R.id.content)?.getChildAt(0)

fun Activity.showErrorMessage(message: String) =
        Snackbar.make(contentView!!, message, Snackbar.LENGTH_LONG).apply { show() }

fun Fragment.showErrorMessage(message: String) =
        Snackbar.make(view!!, message, Snackbar.LENGTH_LONG).apply { show() }

fun Fragment.showErrorMessage(message: String, @IdRes resId: Int, listener: View.OnClickListener): Snackbar =
        Snackbar.make(view!!, message, Snackbar.LENGTH_INDEFINITE).apply {
            setAction(resId, listener)
            show()
        }

fun Activity.showErrorMessage(@IdRes res: Int) =
        Snackbar.make(contentView!!, res, Snackbar.LENGTH_LONG).apply { show() }

fun Fragment.showErrorMessage(@IdRes res: Int) =
        Snackbar.make(view!!, res, Snackbar.LENGTH_LONG).apply { show() }