package io.pixsight.scenemanager.animations

import android.util.SparseArray
import android.view.View

import io.pixsight.scenemanager.annotations.Scene

/**
 * [SimpleAnimationAdapter] can be used to override animations.
 */
abstract class SimpleAnimationAdapter<T : ScenesParams> : AnimationAdapter<T> {

    /**
     * Called once when the scenes are created.
     */
    override fun generateScenesParams(scenes: SparseArray<MutableList<View>>): T? {
        return null
    }

    /**
     * Called when a scene has to be displayed.
     *
     * @param view The view holding a scene. See [Scene.layout]
     * @param animate true if you can animate the transition.
     * false if an animation is not recommended. Ex: When the default scene is
     * displayed.
     */
    internal abstract fun showView(view: View, params: T?, animate: Boolean)

    /**
     * Called when a scene has to be hidden.
     *
     * @param view The view holding a scene. See [Scene.layout]
     * @param animate true if you can animate the transition.
     * false if an animation is not recommended. Ex: When the default scene is
     * displayed.
     */
    internal abstract fun hideView(view: View, params: T?, animate: Boolean)

    override fun doChangeScene(scenesIdsToViews: SparseArray<MutableList<View>>,
                               scenesParams: T?,
                               sceneId: Int,
                               animate: Boolean) {
        val currentSceneViews = scenesIdsToViews.get(sceneId)
        for (i in 0 until scenesIdsToViews.size()) {
            // do change scene
            val viewSceneId = scenesIdsToViews.keyAt(i)
            val views = scenesIdsToViews.get(viewSceneId)
            val show = viewSceneId == sceneId
            showOrHideView(show, views, currentSceneViews, scenesParams, animate)
        }
    }

    private fun showOrHideView(show: Boolean,
                               views: List<View>,
                               forceShowIfHidden: List<View>?,
                               scenesParams: T?,
                               animate: Boolean) {
        for (view in views) {
            if (!show && forceShowIfHidden != null && forceShowIfHidden.contains(view)) {
                continue // Skip an forceShowIfHidden view
            }
            showOrHideView(show, view, scenesParams, animate)
        }
    }

    private fun showOrHideView(show: Boolean,
                               views: View,
                               scenesParams: T?,
                               animate: Boolean) {
        if (show) {
            showView(views, scenesParams, animate)
        } else {
            hideView(views, scenesParams, animate)
        }
    }
}
