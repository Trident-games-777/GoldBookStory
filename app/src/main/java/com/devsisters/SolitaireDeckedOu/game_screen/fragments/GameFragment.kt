package com.devsisters.SolitaireDeckedOu.game_screen.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import com.devsisters.SolitaireDeckedOu.R
import com.devsisters.SolitaireDeckedOu.game_screen.anim.fall
import com.devsisters.SolitaireDeckedOu.game_screen.data.icons

class GameFragment : Fragment() {
    private var credits = 10000
    private lateinit var tvCredits: TextView

    private lateinit var iv1: ImageView
    private lateinit var iv2: ImageView
    private lateinit var iv3: ImageView

    private lateinit var buttonSpin: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvCredits = view.findViewById(R.id.textViewCredits)

        iv1 = view.findViewById(R.id.iv1)
        iv2 = view.findViewById(R.id.iv2)
        iv3 = view.findViewById(R.id.iv3)

        buttonSpin = view.findViewById(R.id.buttonSpin)

        tvCredits.text = getString(R.string.credits, credits)
        buttonSpin.isEnabled = false

        frame() {
            buttonSpin.isEnabled = true
        }

        buttonSpin.setOnClickListener {
            buttonSpin.isEnabled = false
            frame {
                if (credits > 0)
                    buttonSpin.isEnabled = true
            }
        }
    }

    private fun frame(onFinish: () -> Unit) {
        credits -= 100
        tvCredits.text = getString(R.string.credits, credits)
        val currents = mutableListOf<Int>()
        repeat(3) {
            currents.add(icons.shuffled().first())
        }
        iv1.setImageResource(currents[0])
        iv2.setImageResource(currents[1])
        iv3.setImageResource(currents[2])
        fall(iv1, iv2, iv3).apply {
            doOnEnd {
                if (currents[0] == currents[1] && currents[1] == currents[2]) {
                    credits += 500
                    tvCredits.text = getString(R.string.credits, credits)
                }
                onFinish()
            }
        }.start()
    }
}