package vn.edu.vlsc.lab03a_vunguyenductri_2500115657

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import vn.edu.vlsc.lab03a_vunguyenductri_2500115657.databinding.ActivityDetailBinding
import vn.edu.vlsc.lab03a_vunguyenductri_2500115657.model.Student

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var currentStudent: Student? = null

    companion object {
        const val EXTRA_STUDENT = "vn.edu.vlsc.lab03a_vunguyenductri_2500115657.EXTRA_STUDENT"

        /**
         * [HUẤN LUYỆN GỠ LỖI - LỖI 2: TRÓI BUỘC CONTEXT (MEMORY LEAK)]
         * Tiêm mã độc: Mở comment biến tĩnh 'leakContext' bên dưới và gán 'leakContext = this' trong onCreate().
         * Thao tác: Xoay thiết bị ngang dọc 5 lần để hủy và tạo lại Activity.
         * Hậu quả: Dù onDestroy() thực thi, Garbage Collector không thể thu hồi Activity cũ vì bị static reference giữ lại.
         * Khắc phục: Gán 'leakContext = null' trong onDestroy() hoặc thay bằng applicationContext.
         */
        @Suppress("unused")
        var leakContext: Context? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mô phỏng / kiểm thử Lỗi 2 nếu cần quan sát Memory Leak:
        // leakContext = this

        applyWindowInsets()
        setupListeners()
        extractAndBindData()
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val types = WindowInsetsCompat.Type.systemBars() or
                WindowInsetsCompat.Type.displayCutout()
            val bars = insets.getInsets(types)
            view.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnShare.setOnClickListener {
            currentStudent?.let { student ->
                executeShareIntent(student)
            }
        }
    }

    /**
     * Bước 4: Giải nén Payload tại DetailActivity (Null Safety & API Compatibility)
     * Xử lý lỗi Deprecation từ API 33 (Android 13 Tiramisu) và kiểm soát an toàn bằng toán tử elvis ?: run
     */
    private fun extractAndBindData() {
        // Validation API Level và ép kiểu an toàn (Type-Safety)
        val student: Student? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_STUDENT, Student::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_STUDENT)
        }

        currentStudent = student

        // Toán tử an toàn Null Safety & khối ?: run hợp lệ theo rubric chấm điểm
        student?.let {
            binding.tvDetailName.text = it.name
            binding.tvDetailMssv.text = getString(R.string.student_mssv, it.mssv)
            binding.imgDetailAvatar.setImageResource(it.avatarRes)
        } ?: run {
            binding.tvDetailName.text = getString(R.string.error_missing_route_data)
            binding.tvDetailMssv.text = ""
            binding.btnShare.isEnabled = false
        }
    }

    /**
     * Bước 5 (Mục 3): Tích hợp Implicit Intent (ACTION_SEND)
     * Chia sẻ thông tin sinh viên ra ngoài qua bảng chọn hệ thống (App Chooser).
     */
    private fun executeShareIntent(student: Student) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, getString(R.string.share_profile_template, student.name, student.mssv))
            type = "text/plain"
        }
        // Ép hiển thị bảng chọn App (App Chooser) để ngăn chặn hành vi mặc định
        val chooser = Intent.createChooser(sendIntent, getString(R.string.share_profile_title))
        startActivity(chooser)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Biện pháp phòng ngừa và giải phóng Memory Leak khi thí nghiệm Lỗi 2:
        if (leakContext === this) {
            leakContext = null
        }
    }
}
