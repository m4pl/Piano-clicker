package com.mapl.navigation.domain

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.Back
import com.github.terrakok.cicerone.BackTo
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Forward
import com.github.terrakok.cicerone.Replace
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.AppScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.github.terrakok.cicerone.androidx.TransactionInfo

internal class CustomNavigatorImpl : CustomNavigator {

    private val localStackCopy = mutableListOf<TransactionInfo>()

    private var fragmentManager: FragmentManager? = null
        get() {
            return field?.takeIf { !it.isDestroyed }
        }

    private var fragmentFactory: FragmentFactory? = null

    override var containerId: Int? = null

    override var activity: FragmentActivity? = null
        set(value) {
            field = value

            fragmentManager = activity?.supportFragmentManager
            fragmentFactory = fragmentManager?.fragmentFactory
        }

    override fun applyCommands(commands: Array<out Command>) {
        fragmentManager?.executePendingTransactions()

        // copy stack before apply commands
        copyStackToLocal()

        for (command in commands) {
            try {
                applyCommand(command)
            } catch (e: RuntimeException) {
                errorOnApplyCommand(command, e)
            }
        }
    }

    private fun copyStackToLocal() {
        val fragmentManager = this.fragmentManager ?: return

        localStackCopy.clear()

        for (i in 0 until fragmentManager.backStackEntryCount) {
            fragmentManager
                .getBackStackEntryAt(i)
                .name
                ?.let(TransactionInfo::fromString)
                ?.let(localStackCopy::add)
        }
    }

    /**
     * Perform transition described by the navigation command
     *
     * @param command the navigation command to apply
     */
    private fun applyCommand(command: Command) {
        when (command) {
            is Forward -> forward(command)
            is Replace -> replace(command)
            is BackTo -> backTo(command)
            is Back -> back()
        }
    }

    private fun forward(command: Forward) {
        when (val screen = command.screen as AppScreen) {
            is ActivityScreen -> {
                checkAndStartActivity(screen)
            }
            is FragmentScreen -> {
                val type = if (command.clearContainer) TransactionInfo.Type.REPLACE else TransactionInfo.Type.ADD
                commitNewFragmentScreen(screen, type, true)
            }
        }
    }

    private fun replace(command: Replace) {
        when (val screen = command.screen as AppScreen) {
            is ActivityScreen -> {
                checkAndStartActivity(screen)
                activity?.finish()
            }
            is FragmentScreen -> {
                if (localStackCopy.isNotEmpty()) {
                    fragmentManager?.popBackStack()
                    val removed = localStackCopy.removeAt(localStackCopy.lastIndex)
                    commitNewFragmentScreen(screen, removed.type, true)
                } else {
                    commitNewFragmentScreen(screen, TransactionInfo.Type.REPLACE, false)
                }
            }
        }
    }

    private fun back() {
        if (localStackCopy.isNotEmpty()) {
            fragmentManager
                ?.takeIf { !it.isStateSaved }
                ?.let {
                    it.popBackStack()
                    localStackCopy.removeAt(localStackCopy.lastIndex)
                }
        } else {
            activityBack()
        }
    }

    private fun activityBack() {
        activity?.finish()
    }

    private fun commitNewFragmentScreen(
        screen: FragmentScreen,
        type: TransactionInfo.Type,
        addToBackStack: Boolean
    ) {
        val fragment = fragmentFactory?.let(screen::createFragment) ?: return
        val containerId = this.containerId ?: return
        val fragmentManager = this.fragmentManager ?: return

        fragmentManager
            .takeIf { !it.isStateSaved }
            ?.beginTransaction()
            ?.let { transaction ->
                transaction.setReorderingAllowed(true)
                setupFragmentTransaction(
                    transaction,
                    fragmentManager.findFragmentById(containerId),
                    fragment
                )
                when (type) {
                    TransactionInfo.Type.ADD -> transaction.add(containerId, fragment, screen.screenKey)
                    TransactionInfo.Type.REPLACE -> transaction.replace(containerId, fragment, screen.screenKey)
                }
                if (addToBackStack) {
                    val transactionInfo = TransactionInfo(screen.screenKey, type)
                    transaction.addToBackStack(transactionInfo.toString())
                    localStackCopy.add(transactionInfo)
                }
                transaction.commitAllowingStateLoss()
            }
    }

    /**
     * Performs [BackTo] command transition
     */
    private fun backTo(command: BackTo) {
        if (command.screen == null) {
            backToRoot()
        } else {
            val screenKey = command.screen?.screenKey
            val index = localStackCopy.indexOfFirst { it.screenKey == screenKey }
            if (index != -1) {
                val forRemove = localStackCopy.subList(index, localStackCopy.size)
                fragmentManager
                    ?.popBackStack(forRemove.first().toString(), 0)
                    ?.also {
                        forRemove.clear()
                    }
            } else {
                backToUnexisting(command.screen as AppScreen)
            }
        }
    }

    private fun backToRoot() {
        localStackCopy.clear()
        fragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    /**
     * Override this method to setup fragment transaction [FragmentTransaction].
     * For example: setCustomAnimations(...), addSharedElement(...) or setReorderingAllowed(...)
     *
     * @param fragmentTransaction fragment transaction
     * @param currentFragment     current fragment in container
     *                            (for [Replace] command it will be screen previous in new chain, NOT replaced screen)
     * @param nextFragment        next screen fragment
     */
    private fun setupFragmentTransaction(
        fragmentTransaction: FragmentTransaction,
        currentFragment: Fragment?,
        nextFragment: Fragment?
    ) {
        // Do nothing by default
    }

    private fun checkAndStartActivity(screen: ActivityScreen) {
        // Check if we can start activity
        val activity = this.activity ?: return

        val activityIntent = screen.createIntent(activity)
        try {
            activity.startActivity(activityIntent, screen.startActivityOptions)
        } catch (e: ActivityNotFoundException) {
            unexistingActivity(screen, activityIntent)
        }
    }

    /**
     * Called when there is no activity to open `screenKey`.
     *
     * @param screen         screen
     * @param activityIntent intent passed to start Activity for the `screenKey`
     */
    private fun unexistingActivity(screen: ActivityScreen, activityIntent: Intent) {
        // Do nothing by default
    }

    /**
     * Called when we tried to fragmentBack to some specific screen (via [BackTo] command),
     * but didn't found it.
     *
     * @param screen screen
     */
    private fun backToUnexisting(screen: AppScreen) {
        backToRoot()
    }

    /**
     * Override this method if you want to handle apply command error.
     *
     * @param command command
     * @param error   error
     */
    private fun errorOnApplyCommand(command: Command, error: RuntimeException) {
        throw error
    }
}
