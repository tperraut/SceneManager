package io.pixsight.sample.scenemanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import io.pixsight.scenemanager.SceneManager
import io.pixsight.scenemanager.annotations.BuildScenes
import io.pixsight.scenemanager.annotations.Scene
import kotlinx.android.synthetic.main.fragment_loader.*
import kotlinx.android.synthetic.main.fragment_placeholder.*
import kotlinx.android.synthetic.main.sample_fragment_main.*

@BuildScenes(
        value = [
            Scene(scene = Scene.MAIN, layout = R.layout.sample_fragment_main),
            Scene(scene = Scene.SPINNER, layout = R.layout.fragment_loader),
            Scene(scene = Scene.PLACEHOLDER, layout = R.layout.fragment_placeholder)
        ],
        first = Scene.MAIN
)
class SampleFragmentV4 : DialogFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return SceneManager.create(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sample_switch_to_main.setOnClickListener(this)
        sample_switch_to_progress.setOnClickListener(this)
        go_to_main_from_loader.setOnClickListener(this)
        sample_switch_to_placeholder.setOnClickListener(this)
        go_to_main_from_placeholder.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.sample_switch_to_main,
            R.id.go_to_main_from_loader,
            R.id.go_to_main_from_placeholder -> SceneManager.scene(this, Scene.MAIN)
            R.id.sample_switch_to_progress -> SceneManager.scene(this, Scene.SPINNER)
            R.id.sample_switch_to_placeholder -> SceneManager.scene(this, Scene.PLACEHOLDER)
            else -> throw IllegalArgumentException("Nope")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        SceneManager.release(this)
    }

    companion object {

        fun newInstance(): SampleFragmentV4 {
            return SampleFragmentV4()
        }
    }
}
