package com.example.smartsquares

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Size
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    var ROWS = 2
    var COLUMNS = 2

    lateinit var button_middle: Button
    lateinit var score_dinamic: TextView
    lateinit var heart_dinamic: TextView
    lateinit var level_dinamic: TextView

    var contor_id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button_middle = findViewById(R.id.button_middle) // Play Again button;
        level_dinamic = findViewById(R.id.level_dinamic) // level_dinamic;
        score_dinamic = findViewById(R.id.score_dinamic) // score dinamic;
        heart_dinamic = findViewById(R.id.heart_dinamic) // heart dinamic;
        new_game()
        val main_instructtion = findViewById<Button>(R.id.main_instructtion)
        main_instructtion.setOnClickListener {
            val intent = Intent(this, Instructions::class.java)
            startActivity(intent)
        }

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
                button.apply {
                    isEnabled=false
                }
                button_middle.isEnabled = false
                if (button.id == i){
                    println(button.id)
                    button.apply {
                        if (game.level > 5)
                            text = "|||"
                        else{
                            text = "|||||||"
                        }
                        setTextColor(Color.BLACK)
                    }
                }
                Handler().postDelayed({
                    // disable all buttons
                    button.apply {
                        setTextColor(Color.TRANSPARENT)
                        isEnabled=true
                    }
                    //if you have hints
                    if(game.hints > 0)
                        button_middle.isEnabled = true
                }, 1500)
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
        button_middle.text = "Hint(${game.hints})"
        button_middle.isEnabled = true
        game.print_level_and_score(level_dinamic, score_dinamic, heart_dinamic)
        createTable(ROWS + game.level, COLUMNS + game.level)
        create_red_squares()
    }

    fun game_over(){
        button_middle.text = "Play Again!"
        heart_static.text = "Game Over"
        heart_static.setTextColor(Color.parseColor("#eb4034"));
        heart_static.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
        heart_static.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
        heart_dinamic.visibility = View.INVISIBLE
        linearLayout_squares.visibility = View.INVISIBLE
        lista_cu_butoane.clear() // clear old elements from array
        lista_cu_red_buttons.clear() // clear old elements from array
        lista_cu_butoane_valide_apasate.clear() // clear old elements from buttons pressed
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
                // if you don't have hints.
                if(game.hints == 0){
                    button_middle.isEnabled = false
                }
                // if you push hint button.
                button_middle.setOnClickListener { Hint() }

                // if you push a table button.
                button.setOnClickListener { buttonClicked(button) } // when i click button
                lista_cu_butoane.add(button) // adaugare button in lista
                row.addView(button)
                row.gravity = Gravity.CENTER
                contor_id++
            }

            tableLayout.addView(row)
            tableLayout.gravity = Gravity.CENTER
        }
        linearLayout_squares.addView(tableLayout)
    }

    fun Hint(){
        game.hints--
        if(game.hints == 0){
            button_middle.isEnabled = false
        }

        for (button in lista_cu_butoane){
            for (i in (lista_cu_red_buttons - lista_cu_butoane_valide_apasate)) {
                if(i == button.id) {
                    button.apply {
                        isEnabled = false
                    }
                    button_middle.isEnabled = false
                    if (button.id == i) {
                        println(button.id)
                        button.apply {
                            if (game.level > 5)
                                text = "|||"
                            else {
                                text = "|||||||"
                            }
                            setTextColor(Color.BLACK)
                        }
                    }
                    Handler().postDelayed({
                        // disable all buttons
                        button.apply {
                            setTextColor(Color.TRANSPARENT)
                            isEnabled = true
                        }
                        //if you have hints
                        if (game.hints > 0)
                            button_middle.isEnabled = true
                    }, 1500)
                }
            }
        }

        //println(lista_cu_red_buttons - lista_cu_butoane_valide_apasate )

        button_middle.text = "Hint(${game.hints})"
    }

    var lista_cu_butoane_valide_apasate: ArrayList<Int> = ArrayList() // salvare butoane rosii in aceasta lista;
    private fun buttonClicked(button: Button) {

        var a_fost_apasat_corect = false
        for (i in  lista_cu_red_buttons){
            if(button.id == i){
                a_fost_apasat_corect = true
                break
            }
        }

        if (a_fost_apasat_corect){
            button.apply {
                setTextColor(Color.GREEN)
                isEnabled = false
                game.score_up()
            }
            lista_cu_butoane_valide_apasate.add(button.id)
        }else{
            button.apply {
                if (game.level > 5)
                    text = "|||"
                else{
                    text = "||||||"
                }
                setTextColor(Color.RED)
                isEnabled = false
            }
            game.heart_down()
        }

        if (game.stage == 4){
            game.level_up()
            game.stage = 2
        }

        if(lista_cu_butoane_valide_apasate.count() == lista_cu_red_buttons.count()){
            lista_cu_butoane_valide_apasate.clear() // clear old elements from buttons pressed
            lista_cu_butoane.clear() // clear old elements from array
            lista_cu_red_buttons.clear() // clear old elements from array
            game.stage++
            new_game()
        }
        game.print_level_and_score(level_dinamic, score_dinamic, heart_dinamic)

        // Verific daca mai are vieti sau a terminat jocul.
        if (game.heart <= 0 || game.level + ROWS > 8) { // level 8 este maxim (8 + 2(rows) = 10;
            game_over()   // Set background for game_over
            ROWS = 2
            COLUMNS = 2
            game.level = 1
            game.heart = 3
            game.score = 0
            game.hints = 3
            button_middle.isEnabled = true
            button_middle.setOnClickListener { new_game() } // When player press "Play again"
        }
    }
}



class game_squares{
    var level: Int = 1     // Init level with default: 1;
    var score: Int = 0    // Init score with default: 0;
    var heart: Int = 3     // Init heart with default: 3;
    var stage: Int = 2;  // Init stage default 2; it is how much random numbers i make;
    var hints: Int = 3;

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
