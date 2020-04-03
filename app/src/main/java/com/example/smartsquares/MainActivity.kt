package com.example.smartsquares

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.print.PrintAttributes
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    var ROWS = 2
    var COLUMNS = 2

    lateinit var play_again: Button
    lateinit var score_dinamic: TextView
    lateinit var heart_dinamic: TextView
    lateinit var level_dinamic: TextView

    var contor_id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         play_again = findViewById(R.id.play_again) // Play Again button;
         level_dinamic = findViewById(R.id.level_dinamic) // level_dinamic;
         score_dinamic = findViewById(R.id.score_dinamic) // score dinamic;
        heart_dinamic = findViewById(R.id.heart_dinamic) // heart dinamic;
        new_game()
    }
    var lista_cu_butoane: ArrayList<Button> = ArrayList() // initializare lista cu butoane;
    var lista_cu_red_buttons: ArrayList<Int> = ArrayList() // salvare butoane rosii in aceasta lista;

    // get random numbers for red squares
    fun get_random_numbers(): List<Int> {
        val total_number_per_table = (ROWS + game.level) * (ROWS + game.level) - 1 // -1 becuase i = 0
        val randomList = (0..total_number_per_table).shuffled().take(game.stage * game.level)
        return randomList
    }


    //create & show red squares
    fun create_red_squares(){
        for (i in get_random_numbers()){
            // adaugare buton rosu in lista cu butoane rosii.
            lista_cu_red_buttons.add(i)

            for (button in lista_cu_butoane){
                if (button.id == i){
                    println(button.id)
                    button.apply {
                        text = "|||||||||||||"
                        setTextColor(Color.GREEN)
                    }
//
//                    Handler().postDelayed({
//                        button.apply {
//                            setTextColor(Color.TRANSPARENT)
//                        }
//                    }, 3000)
                }
            }
        }
    }

    val game = game_squares() // class game_squares()

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
        create_red_squares()
    }

    fun game_over(){
        heart_static.text = "Game Over"
        heart_static.setTextColor(Color.parseColor("#eb4034"));
        heart_static.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50F)
        heart_static.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
        heart_dinamic.visibility = View.INVISIBLE
        linearLayout_squares.visibility = View.INVISIBLE
        play_again.visibility = View.VISIBLE
        lista_cu_butoane.clear() // clear old elements from array
        lista_cu_red_buttons.clear() // clear old elements from array
    }



    fun createTable(rows: Int, cols: Int) {
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
                    layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT)
                    //text = contor_id.toString()

                }
                button.setOnClickListener { buttonClicked(button) } // when i click button
                lista_cu_butoane.add(button) // adaugare button in lista
                row.addView(button)
                contor_id++
            }

            tableLayout.addView(row)
        }
        linearLayout_squares.addView(tableLayout)

        //printf list
        /*for (element in list){
            println(element.text)
        }*/
    }


    private fun buttonClicked(button: Button) {
        //TODO: Bug la afisare, cred ca este de la createTable si afisarea sa

        /*game.heart_down()
        game.heart_down() // Merge
        game.heart_down()*/

        var a_fost_apasat_corect = false
        for (i in  lista_cu_red_buttons){
            if(button.id == i){
                a_fost_apasat_corect = true

            }
        }
        if (a_fost_apasat_corect){
            button.apply {
                text = "|||||||||||||"
                setTextColor(Color.GREEN)
            }
        }else{
            button.apply {
                text = "|||||||||||||"
                setTextColor(Color.RED)
            }
            game.heart_down()
        }
        game.print_level_and_score(level_dinamic, score_dinamic, heart_dinamic)

        println("button ${button.id}")

        // Verific daca mai are vieti sau a terminat jocul.
        if (game.heart <= 0 || game.level + ROWS > 9) { // level 8 este maxim (8 + 2(rows) = 10;
            game_over()   // Set background for game_over
            ROWS = 2
            COLUMNS = 2
            game.level = 1
            game.heart = 3
            play_again.setOnClickListener { new_game() } // When player press "Play again"
        }
    }
}



class game_squares{
    var level: Int = 1     // Init level with default: 0;
    var score: Int = 0    // Init score with default: 0;
    var heart: Int = 3     // Init heart with default: 3;
    var stage: Int = 2;  // Init stage defauld 2; it is how much random numbers i make;

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
