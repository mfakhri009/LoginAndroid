import android.R
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    // creating variables for our edit text
    private var courseNameEdt: EditText? = null
    private var courseDurationEdt: EditText? = null
    private var courseDescriptionEdt: EditText? = null

    // creating variable for button
    private var submitCourseBtn: Button? = null

    // creating a strings for storing our values from edittext fields.
    private var courseName: String? = null
    private var courseDuration: String? = null
    private var courseDescription: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // initializing our edittext and buttons
        courseNameEdt = findViewById<EditText>(R.id.idEdtCourseName)
        courseDescriptionEdt = findViewById<EditText>(R.id.idEdtCourseDescription)
        courseDurationEdt = findViewById<EditText>(R.id.idEdtCourseDuration)
        submitCourseBtn = findViewById<Button>(R.id.idBtnSubmitCourse)
        submitCourseBtn.setOnClickListener(View.OnClickListener {
            // getting data from edittext fields.
            courseName = courseNameEdt.getText().toString()
            courseDescription = courseDescriptionEdt.getText().toString()
            courseDuration = courseDurationEdt.getText().toString()


            // validating the text fields if empty or not.
            if (TextUtils.isEmpty(courseName)) {
                courseNameEdt.setError("Please enter Course Name")
            } else if (TextUtils.isEmpty(courseDescription)) {
                courseDescriptionEdt.setError("Please enter Course Description")
            } else if (TextUtils.isEmpty(courseDuration)) {
                courseDurationEdt.setError("Please enter Course Duration")
            } else {


                // calling method to add data to Firebase Firestore.
                addDataToDatabase(courseName!!, courseDescription!!, courseDuration!!)
            }
        })
    }

    private fun addDataToDatabase(
        courseName: String,
        courseDescription: String,
        courseDuration: String
    ) {


        // url to post our data
        val url = "http://localhost/courseApp/addCourses.php"


        // creating a new variable for our request queue
        val queue: RequestQueue = Volley.newRequestQueue(this@MainActivity)


        // on below line we are calling a string


        // request method to post the data to our API


        // in this we are calling a post method.
        val request: StringRequest =
            object : StringRequest(Request.Method.POST, url, object : Listener<String?>() {
                fun onResponse(response: String) {
                    Log.e("TAG", "RESPONSE IS $response")
                    try {
                        val jsonObject = JSONObject(response)


                        // on below line we are displaying a success toast message.
                        Toast.makeText(
                            this@MainActivity,
                            jsonObject.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }


                    // and setting data to edit text as empty
                    courseNameEdt!!.setText("")
                    courseDescriptionEdt!!.setText("")
                    courseDurationEdt!!.setText("")
                }
            }, object : ErrorListener() {
                fun onErrorResponse(error: VolleyError) {


                    // method to handle errors.
                    Toast.makeText(
                        this@MainActivity,
                        "Fail to get response = $error",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }) {
                val bodyContentType: String
                    get() =// as we are passing data in the form of url encoded


                        // so we are passing the content type below


                        "application/x-www-form-urlencoded; charset=UTF-8"
                protected val params: Map<String, String>
                    protected get() {


                        // below line we are creating a map for storing


                        // our values in key and value pair.
                        val params: MutableMap<String, String> = HashMap()


                        // on below line we are passing our


                        // key and value pair to our parameters.
                        params["courseName"] = courseName
                        params["courseDuration"] = courseDuration
                        params["courseDescription"] = courseDescription


                        // at last we are returning our params.
                        return params
                    }
            }


        // below line is to make


        // a json object request.
        queue.add(request)
    }
}