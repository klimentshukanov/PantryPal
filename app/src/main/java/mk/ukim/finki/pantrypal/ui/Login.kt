package mk.ukim.finki.pantrypal.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import mk.ukim.finki.pantrypal.MainActivity
import mk.ukim.finki.pantrypal.R

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    public override fun onStart() {
        super.onStart()

        auth = Firebase.auth

        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent);
            finish();
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val registerLink : TextView = findViewById(R.id.registerNow);
        registerLink.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent);
            finish();
        }

        val editTextEmail : EditText = findViewById(R.id.emailEditText);
        val editTextPassword : EditText = findViewById(R.id.passwordEditText);
        val button : Button = findViewById(R.id.loginButton);

        button.setOnClickListener {
            val email: String = editTextEmail.text.toString();
            val password : String = editTextPassword.text.toString()


            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent);
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }

    }
}