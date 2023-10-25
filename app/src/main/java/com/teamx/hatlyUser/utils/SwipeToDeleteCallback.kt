package com.teamx.hatlyUser.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyUser.R
import com.teamx.hatlyUser.ui.fragments.profile.Locations.adapter.LocationsListAdapter




//
//class SwipeToDeleteCallback(private val context: Context, private val adapter: LocationsListAdapter) :
//    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
//
//    private val deleteIcon = ContextCompat.getDrawable(context, R.drawable.notification_)
//    private val intrinsicWidth = deleteIcon?.intrinsicWidth
//
//    // Set a threshold for the swipe action
//    private val swipeThreshold = 0.5f
//
//    override fun onMove(
//        recyclerView: RecyclerView,
//        viewHolder: RecyclerView.ViewHolder,
//        target: RecyclerView.ViewHolder
//    ): Boolean {
//        return false
//    }
//
//    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//        // This method is not used in this approach
//    }
//
//    override fun onChildDraw(
//        c: Canvas,
//        recyclerView: RecyclerView,
//        viewHolder: RecyclerView.ViewHolder,
//        dX: Float,
//        dY: Float,
//        actionState: Int,
//        isCurrentlyActive: Boolean
//    ) {
//        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
//            val itemView = viewHolder.itemView
//
//            val iconMargin = (itemView.height - intrinsicWidth!!) / 2
//            val iconTop = itemView.top + (itemView.height - intrinsicWidth) / 2
//            val iconBottom = iconTop + intrinsicWidth
//
//            val iconLeft : Int
//            val iconRight : Int
//
//            if (dX > 0) { // Swiping to the right
//                iconRight = itemView.left + iconMargin + dX.toInt()
//                iconLeft = iconRight - intrinsicWidth
//            }
//
////            else { // Swiping to the left
////                iconLeft = itemView.right - iconMargin + dX.toInt()
////                iconRight = iconLeft + intrinsicWidth
////            }
//
//            else { // Swiping to the left
//                iconLeft = 0
//                iconRight = 0
//            }
//
//            Log.d("deleteIcon", "iconLeft: $iconLeft")
//            Log.d("deleteIcon", "iconRight: $iconRight")
//
//            deleteIcon?.setBounds(iconLeft, iconTop, iconRight, iconBottom)
//            deleteIcon?.draw(c)
//
//            // Allow swipe back if not beyond the threshold
//            if (Math.abs(dX) <= itemView.width * swipeThreshold) {
//                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
//            }
//        }
//    }
//}


class SwipeToDeleteCallback(private val context: Context, private val adapter: LocationsListAdapter) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    private val deleteIcon = ContextCompat.getDrawable(context, R.drawable.notification_)
    private val intrinsicWidth = deleteIcon?.intrinsicWidth

    // Set a threshold for the swipe action
    private val swipeThreshold = 0.5f

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        // This method is not used in this approach
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val itemView = viewHolder.itemView

            val iconMargin = (itemView.height - intrinsicWidth!!) / 2
            val iconTop = itemView.top + (itemView.height - intrinsicWidth) / 2
            val iconBottom = iconTop + intrinsicWidth

            if (dX < 0) { // Swiping to the left
                val iconRight = itemView.right - iconMargin
                val iconLeft = iconRight - intrinsicWidth
                deleteIcon?.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                deleteIcon?.draw(c)

                // Limit the swipe distance
                if (dX < -itemView.width * swipeThreshold) {
                    super.onChildDraw(c, recyclerView, viewHolder, -itemView.width * swipeThreshold, dY, actionState, isCurrentlyActive)
                } else {
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                }
            }

        }
    }
}


//class SwipeToDeleteCallback(private val adapter: YourAdapter) :
//    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
//
//    override fun onMove(
//        recyclerView: RecyclerView,
//        viewHolder: RecyclerView.ViewHolder,
//        target: RecyclerView.ViewHolder
//    ): Boolean {
//        return false // We're not interested in dragging items
//    }
//
//    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//        // Handle item removal here
//        val position = viewHolder.adapterPosition
//        adapter.removeItem(position)
//    }
//}