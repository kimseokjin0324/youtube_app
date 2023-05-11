package fastcampus.aop.part3.chapter01

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import fastcampus.aop.part3.chapter01.databinding.FragmentPlayerBinding
import kotlin.math.abs

class PlayerFragment : Fragment(R.layout.fragment_player) {

    private var binding: FragmentPlayerBinding? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentPlayerBinding = FragmentPlayerBinding.bind(view)

        binding = fragmentPlayerBinding

        fragmentPlayerBinding.playerMotionLayout.setTransitionListener(object :
            MotionLayout.TransitionListener {

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
                //MainActivity MotionLayout에다가 어디까지 이동되었느지 값을 주는게 목표
                binding?.let {
                    (activity as MainActivity).also { mainActivity ->
                        mainActivity.findViewById<MotionLayout>(R.id.mainMotionLayout).progress =
                            abs(progress) //progress값이 튈수도 있기때문에 절대값으로 줌
                    }
                }
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {}

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
        })


    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null //onDestroy이 될때 binding을 해제시켜줘야함 꼭!
    }

}