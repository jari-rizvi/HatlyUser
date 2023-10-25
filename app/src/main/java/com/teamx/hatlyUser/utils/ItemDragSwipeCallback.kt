package com.teamx.hatlyUser.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView


class ItemDragSwipeCallback(
    context: Context,
    backgroundColor: Int,
    drawable: Int,
    dragDirs: Int,
    swipeDirs: Int,
    private val mOnTouchListener: OnTouchListener
) :
    ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {
    private val mIcon: Drawable?
    private val mBackground: ColorDrawable

    interface OnTouchListener {
        fun onMove(
            recyclerView: RecyclerView?,
            viewHolder: RecyclerView.ViewHolder?,
            target: RecyclerView.ViewHolder?
        ): Boolean

        fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int)
    }

    init {
        mIcon = ContextCompat.getDrawable(context, drawable)
        mBackground = ColorDrawable(context.resources.getColor(backgroundColor))
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return mOnTouchListener.onMove(recyclerView, viewHolder, target)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        mOnTouchListener.onSwiped(viewHolder, direction)
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
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        val itemView = viewHolder.itemView
        val backgroundCornerOffset = 25 //so mBackground is behind the rounded corners of itemView
        val iconMargin = (itemView.height - mIcon!!.intrinsicHeight) / 2
        val iconTop = itemView.top + (itemView.height - mIcon.intrinsicHeight) / 2
        val iconBottom = iconTop + mIcon.intrinsicHeight
        if (dX > 0) { // Swiping to the right
            val iconLeft = itemView.left + iconMargin
            val iconRight = iconLeft + mIcon.intrinsicWidth
            mIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            mBackground.setBounds(
                itemView.left, itemView.top,
                itemView.left + dX.toInt() + backgroundCornerOffset, itemView.bottom
            )
        } else if (dX < 0) { // Swiping to the left
            val iconLeft = itemView.right - iconMargin - mIcon.intrinsicWidth
            val iconRight = itemView.right - iconMargin
            mIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            mBackground.setBounds(
                itemView.right + dX.toInt() - backgroundCornerOffset,
                itemView.top, itemView.right, itemView.bottom
            )
        } else { // view is unSwiped
            mIcon.setBounds(0, 0, 0, 0)
            mBackground.setBounds(0, 0, 0, 0)
        }
        mBackground.draw(c)
        mIcon.draw(c)
    }
}