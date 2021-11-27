package com.hllwrld.calculatedi

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.hllwrld.calculatedi.ui.theme.CalculateDITheme
import android.os.Environment
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.io.File


class MainActivity : ComponentActivity() {

/*
    val mGetContent = this.registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        // Handle the returned Uri
    }
*/
var sdcard: File? = Environment.getExternalStorageDirectory()

    var file: File = File(sdcard, "Download/data.csv")
    val mRows = mutableListOf<List<String>>()

    fun fillRows(path:String) {
        csvReader().open(file) {
            readAllAsSequence().forEach {
                mRows.add(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fillRows("/sdcard/data.csv")

        val coreUiTeamList = getCoreUITeamData(mRows)
        val themeTeamList = getThemeUITeamData(mRows)
        val minusTeamList = getMinusTeamData(mRows)

        var mCoreUISum:Float = 0f
        var mThemeSum:Float = 0f

        coreUiTeamList.onEach {

            mCoreUISum+=it.mNum
        }
        themeTeamList.onEach {
            mThemeSum+=it.mNum
        }

        setContent {
            Column {
                show(coreUiTeamList)
                show(themeTeamList)
                show(minusTeamList)
                Row (Modifier.padding(20.dp)){
                    Text(text = "核心UI: ")
                    SelectionContainer() {
                        Text(text = "${mCoreUISum}")
                    }
                }

                Row(Modifier.padding(20.dp)) {
                    Text(text = "主题壁纸: ")
                    SelectionContainer() {
                        Text(text = "${mThemeSum}")
                    }
                }
                Row(Modifier.padding(20.dp)) {
                    Text(text = "核心体验: ")
                    SelectionContainer() {
                        Text(text = "${mThemeSum+ mCoreUISum+ minusTeamList[0].mNum}")
                    }
                }
            }
        }

       // reme

    }
}

@Composable
fun show(data:List<Team>) {
    Column {
        data.forEach {
            Row {
               Text(text = "${it.mName}: ")
                SelectionContainer() {

                    Text(text = "${it.mNum}")
                }
            }
        }
    }

}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CalculateDITheme {
        Greeting("Android")
    }
}