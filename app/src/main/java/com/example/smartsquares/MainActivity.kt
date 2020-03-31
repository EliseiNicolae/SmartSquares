package com.example.smartsquares

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val level_dinamic: TextView = findViewById(R.id.level_dinamic) // level_dinamic
        val score_dinamic: TextView = findViewById(R.id.score_dinamic) // score dinamic
        val heart_dinamic: TextView = findViewById(R.id.heart_dinamic) // heart dinamic
        val heart_static: TextView = findViewById(R.id.heart_static) // heart static


        val game = game_squares()

        game.heart_down()
        game.heart_down()
        game.heart_down()

        game.print_level_and_score(level_dinamic, score_dinamic, heart_dinamic)

    }

    public fun game_over(){

        heart_static.text = "Game ".toString()
        heart_dinamic.text = "Over!".toString()

    }

    class game_squares{
        var level: Int = 0       // Init level with default: 0;
        var score: Int = 0      // Init score with default: 0;
        var heart: Int = 3     // Init heart with default: 3;

        fun level_up() : Int {    // level-ul function;
            return level++
        }

        fun score_up() : Int{    // score-up function;
            score += 10
            return score
        }

        fun heart_down() : Int{   // heart-down function
            heart-- // heart-down -1

            if (heart == 0){
                game_over()
                return 0
            }
            return heart
        }

        fun print_level_and_score(level_dinamic: TextView, score_dinamic: TextView, heart_dinamic: TextView){ // print level and score with command: 'game.print_level_and_score()';
            level_dinamic.text = this.level.toString()
            score_dinamic.text = this.score.toString()
            heart_dinamic.text = this.heart.toString()
        }

    }
}
