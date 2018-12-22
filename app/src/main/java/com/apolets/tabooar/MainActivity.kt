package com.apolets.tabooar

import android.content.Context
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode


const val TAG = "MY_APP"

class MainActivity : AppCompatActivity() {
    private lateinit var arFragment: ArFragment
    var renderables = HashMap<String, ModelRenderable>()

    private var scoreboardRenderable: ViewRenderable? = null
    private lateinit var scoreboard: ScoreboardView

    private var wordCardRenderable: ViewRenderable? = null
    private lateinit var wordCard: WordCardView

    private var gameHandler = Handler()

    private val timerRunnable = Runnable()
    {

        while (true) {

            if (scoreboard.time <= 0) {
                currentTeam = if (currentTeam == 1) 2 else 1
                runOnUiThread {
                    scoreboard.time = 30
                    updateCurrentWordNode()}


            }

            Thread.sleep(1000)

            runOnUiThread { scoreboard.time-- }


        }

    }

    private var timerThread = Thread(timerRunnable)

    private var currentTeam = 1

    private lateinit var anchorNode: AnchorNode

    private var currentWordNode: TransformableNode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        arFragment = supportFragmentManager.findFragmentById(R.id.ux_fragment) as ArFragment


        buildWordRenderables()
        initGame()

        //Shuffle the cards every time app launches
        wordBank.shuffle()

        arFragment.setOnTapArPlaneListener { hitResult, plane, _ ->

            if (plane.type != Plane.Type.HORIZONTAL_UPWARD_FACING) {
                "Find an HORIZONTAL and UPWARD FACING plane!".toast(this)
                return@setOnTapArPlaneListener
            }

            if (scoreboardRenderable == null || wordCardRenderable == null) {
                return@setOnTapArPlaneListener
            }

            anchorNode = AnchorNode(hitResult.createAnchor())

            anchorNode.setParent(arFragment.arSceneView.scene)


            TransformableNode(arFragment.transformationSystem)
                    .also {
                        it.setParent(anchorNode)
                        it.renderable = scoreboardRenderable
                        it.localPosition = Vector3(it.localPosition.x-1F,it.localPosition.y+.75F,it.localPosition.z)
                    }

            TransformableNode(arFragment.transformationSystem)
                    .also {
                        it.setParent(anchorNode)
                        it.renderable = wordCardRenderable
                        it.localPosition = Vector3(it.localPosition.x+1F,it.localPosition.y+.75F,it.localPosition.z)
                        Log.d(TAG,"card position: ${it.localPosition}")

                    }


            currentWordNode = TransformableNode(arFragment.transformationSystem)
                    .apply {
                        setParent(anchorNode)
                        renderable = renderables[wordBank[7].word]
                        Log.d(TAG,"New node created, ${wordCard.word.word}, $localPosition")
                    }
        }



    }


    private fun buildWordRenderables() {

        val msg = "Starting renderable building."
        Log.d(TAG, msg)
        msg.toast(this)

        assets.list("").forEach { imageName ->

            if (imageName.endsWith(".sfb")) {
                Log.d(TAG, imageName)
                ModelRenderable.builder()
                        .setSource(this, Uri.parse(imageName))
                        .build()
                        .thenAccept { it ->
                            val newName = imageName.split(".")[0].replace("_", " ")
                    renderables[newName] = it; Log.d(TAG, "$newName renderable completed.")
                }
            }

        }
    }


    private fun initGame() {

        scoreboard = ScoreboardView(this)

        scoreboard.onStartTapped = {

            scoreboard.score1 = 0
            scoreboard.score2 = 0

            scoreboard.time = 30

            updateCurrentWordNode()


            timerThread.start()

        }


        wordCard = WordCardView(this)

        wordCard.onFaulTapped = {

            when (currentTeam) {
                1 -> --scoreboard.score1
                2 -> --scoreboard.score2
            }

            updateCurrentWordNode()

        }

        wordCard.onPassTapped = {
            updateCurrentWordNode()
        }

        wordCard.onCorrectTapped = {

            when (currentTeam) {
                1 -> ++scoreboard.score1
                2 -> ++scoreboard.score2
            }


            updateCurrentWordNode()
        }


        //Build models for ui async
        ViewRenderable.builder()
                .setView(this, scoreboard)
                .build()
                .thenAccept { scoreboardRenderable = it; Log.d(TAG, "Scoreboard renderable ready") }

        ViewRenderable.builder()
                .setView(this, wordCard)
                .build()
                .thenAccept { wordCardRenderable = it; Log.d(TAG, "Word Card renderable ready") }


    }

    private fun updateCurrentWordNode(){
        wordCard.word = nextWord()
        currentWordNode?.renderable = renderables[wordCard.word.word]
        Log.d(TAG,"New node created, ${wordCard.word.word}")
    }


    private fun Any?.toast(ctx: Context): Void? {
        val msg = if (this is Throwable) {
            this.printStackTrace()
            this.localizedMessage
        } else {
            this.toString()
        }

        Toast.makeText(ctx, msg, Toast.LENGTH_LONG)
                .apply { setGravity(Gravity.CENTER, 0, 0) }.show()
        return null
    }
}
