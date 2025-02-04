package androidx.core.extension.widget.recyclerview

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class SimpleScrollListener(private val onLoadMoreListener: SimpleOnLoadMoreListener) :
    RecyclerView.OnScrollListener() {

    companion object {
        const val NO_MANAGER = -1
        const val LINEAR = 0
        const val STAGGERED_GRID = 1
    }

    interface SimpleOnLoadMoreListener {
        fun onLoadMore()
    }

    private var layoutManagerType: Int = NO_MANAGER
    private var lastVisibleItemPosition: Int = 0
    private var scrollItemCount: Int = 1

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val layoutManager = recyclerView.layoutManager
        if (layoutManagerType == NO_MANAGER) {
            layoutManagerType = when (layoutManager) {
                is LinearLayoutManager -> LINEAR
                is StaggeredGridLayoutManager -> STAGGERED_GRID
                else -> throw RuntimeException("Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager")
            }
        }
        when (layoutManagerType) {
            LINEAR -> lastVisibleItemPosition =
                (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

            STAGGERED_GRID -> lastVisibleItemPosition = findMax(
                (layoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null)
            )
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        val layoutManager = recyclerView.layoutManager
        val visibleItemCount = layoutManager?.childCount ?: 0
        val totalItemCount = layoutManager?.itemCount ?: 0
        if (visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition >= totalItemCount - scrollItemCount) {
            onLoadMoreListener.onLoadMore()
        }
    }

    private fun findMax(lastPositions: IntArray): Int {
        var max = lastPositions[0]
        for (value in lastPositions) {
            if (value > max) {
                max = value
            }
        }
        return max
    }

}