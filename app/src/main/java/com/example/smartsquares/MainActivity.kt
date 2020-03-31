package com.example.smartsquares

import android.graphics.Color
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {

    val ROWS = 3
    val COLUMNS = 3
    val tableLayout by lazy { TableLayout(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val level_dinamic: TextView = findViewById(R.id.level_dinamic) // level_dinamic
        val score_dinamic: TextView = findViewById(R.id.score_dinamic) // score dinamic
        val heart_dinamic: TextView = findViewById(R.id.heart_dinamic) // heart dinamic
        val heart_static: TextView = findViewById(R.id.heart_static) // heart static
        // val constraintLayout: ConstraintLayout = findViewById(R.id.constraintLayout) // De modificat

        val game = game_squares()

        game.heart_down()
        game.heart_down()

        // Game Over
        if (game.heart == 0) {
            heart_static.text = "Game Over"
            heart_static.setTextColor(Color.parseColor("#eb4034"));
            heart_static.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50F)
            heart_static.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
            heart_dinamic.visibility = View.INVISIBLE

            // constraintLayout.visibility = View.INVISIBLE // Set Layout with squares INVISIBLE
        }

        game.print_level_and_score(level_dinamic, score_dinamic, heart_dinamic)


        val lp = TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        tableLayout.apply {
            layoutParams = lp
            isShrinkAllColumns = true
        }


        createTable(ROWS, COLUMNS)
    }

    fun createTable(rows: Int, cols: Int) {

        for (i in 0 until rows) {

            val row = TableRow(this)
            row.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

            for (j in 0 until cols) {

                val button = Button(this)
                button.apply {
                    //TODO: Adauga id la fiecare buton, si verifica daca poti sa iei butonul si sa ii schimbi background-ul.
                    layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT)
                    text = "R $i C $j"
                }
                row.addView(button)
            }
            tableLayout.addView(row)
        }
        linearLayout.addView(tableLayout)
    }

}



    class game_squares{
        var level: Int = 50000       // Init level with default: 0;
        var score: Int = 50000    // Init score with default: 0;
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
