package io.wape.tinkoffnews.screens

import android.app.Activity
import android.support.annotation.IdRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import io.wape.tinkoffnews.R
import java.io.IOException
import java.net.UnknownHostException

/**
 * Вспомогательная функция для отображения ошибки в snackBar
 * @param throwable ошибка
 * @param resId id строки (R.string.id) которую нужно показать на кнопке snackBar(Action)
 * @param listener слушатель для кнопки action в snackBar
 */
fun Fragment.handleError(throwable: Throwable, @IdRes resId: Int, listener: View.OnClickListener): Snackbar {
    when (throwable) {
        is IOException -> return showErrorMessage(R.string.connection_error, resId, listener)
        is UnknownHostException -> return showErrorMessage(R.string.connection_error, resId, listener)
        else -> {
            return showErrorMessage(R.string.undefined_error, resId, listener)
        }
    }
}

/**
 * Returns the content view of this Activity if set, null otherwise.
 */
inline val Activity.contentView: View?
    get() = findOptional<ViewGroup>(android.R.id.content)?.getChildAt(0)

inline fun <reified T : View> Activity.findOptional(@IdRes id: Int): T? = findViewById(id)

/**
 * Отображение текста ошибки в snackBar
 * @param message сообщение которое необходимо вывести
 * @param resId текст кнопки Action
 * @param listener слушатель для кнопки action в snackBar
 */
fun Fragment.showErrorMessage(@IdRes message: Int, @IdRes resId: Int, listener: View.OnClickListener): Snackbar =
        Snackbar.make(view!!, message, Snackbar.LENGTH_INDEFINITE).apply {
            setAction(resId, listener)
            show()
        }

fun Activity.showErrorMessage(@IdRes res: Int) =
        Snackbar.make(contentView!!, res, Snackbar.LENGTH_LONG).apply { show() }

fun Fragment.showErrorMessage(@IdRes res: Int) =
        Snackbar.make(view!!, res, Snackbar.LENGTH_LONG).apply { show() }