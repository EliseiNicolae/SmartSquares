package com.example.smartsquares
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList
class MainActivity : AppCompatActivity() {
    var ROWS = 2
    var COLUMNS = 2

    var contor_id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val play_again: Button = findViewById(R.id.play_again) // Play Again button
        val level_dinamic: TextView = findViewById(R.id.level_dinamic) // level_dinamic
        val score_dinamic: TextView = findViewById(R.id.score_dinamic) // score dinamic
        val heart_dinamic: TextView = findViewById(R.id.heart_dinamic) // heart dinamic


        //TODO: Trebuie sa pui ce e dupa global.
        //TODO: Vezi ca ai o eraore (intra in out-views) cand sunt mai multe de 4 coloane / rows.
        val game = game_squares() // class game_squares()
        game.heart_down()
        game.heart_down()
        game.heart_down()

        fun new_game(){ // Set default
            heart_static.text = "Heart: "
            heart_static.setTextColor(Color.parseColor("#4d5457"));
            heart_static.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25F)
            heart_static.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
            heart_dinamic.visibility = View.VISIBLE
            linearLayout_squares.visibility = View.VISIBLE
            play_again.visibility = View.GONE
            game.print_level_and_score(level_dinamic, score_dinamic, heart_dinamic)
            createTable(ROWS + game.level, COLUMNS + game.level)
        }
        new_game()

    }
    /* fun create_random_red_squares(rows: Int, cols: Int){
     }*/
    fun game_over(){
        heart_static.text = "Game Over"
        heart_static.setTextColor(Color.parseColor("#eb4034"));
        heart_static.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50F)
        heart_static.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
        heart_dinamic.visibility = View.INVISIBLE
        linearLayout_squares.visibility = View.INVISIBLE
        play_again.visibility = View.VISIBLE

    }

    var list: ArrayList<Button> = ArrayList() // initializare lista


    fun createTable(rows: Int, cols: Int) {
        println("Numarul de rows: $rows")
        println("Numarul de cols: $cols")
        linearLayout_squares.removeAllViews()
        val tableLayout = TableLayout(this);
        val lp = TableLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        tableLayout.setLayoutParams(lp)
        tableLayout.apply { isShrinkAllColumns = true }

        contor_id = 0
        for (i in 0 until rows) {
            val row = TableRow(this)
            row.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            for (j in 0 until cols) {
                val button = Button(this)
                button.apply {
                    id=contor_id
                    setTextColor(Color.WHITE)
                    layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT)
                    text = contor_id.toString()
                }
                button.setOnClickListener { buttonClicked(button) }
                list.add(button) // adaugare button in lista
                row.addView(button)
                contor_id++
            }

            tableLayout.addView(row)
        }
        linearLayout_squares.addView(tableLayout) //TODO: here

        //printf list
        for (element in list){
            /*if(element.id == 2){
                element.apply {
                    setTextColor(Color.BLACK)
                }
            }*/
            println(element.text)
        }
    }

    private fun buttonClicked(button: Button) {
        /*if (game.heart == 0 || game.level >= 7) {
            game_over()   // Set background for game_over
//                Delete_old_Table(ROWS + game.level, COLUMNS + game.level)
            ROWS = 2
            COLUMNS = 2
            play_again.setOnClickListener { new_game() } // When player press "Play again"
        }
        println("button ${button.id}")*/
        println("button ${button.id}")

    }
}
class game_squares{
    var level: Int = 1       // Init level with default: 0;
    var score: Int = 0    // Init score with default: 0;
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
