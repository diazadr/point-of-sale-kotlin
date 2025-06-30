package com.example.pointofsale.ui.product

import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.pointofsale.data.model.Category
import com.example.pointofsale.data.model.Product
import com.example.pointofsale.databinding.FragmentFormProductBinding
import com.example.pointofsale.ui.category.CategoryViewModel
import com.google.zxing.integration.android.IntentIntegrator
import java.io.File
import java.math.BigDecimal

class FormProductFragment : Fragment() {

    private lateinit var binding: FragmentFormProductBinding
    private lateinit var viewModel: ProductViewModel
    private lateinit var categoryViewModel: CategoryViewModel
    private var data: Product? = null
    private var categoryList: List<Category> = emptyList()
    private var selectedImageUri: Uri? = null

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            selectedImageUri = uri
            Glide.with(this).load(uri).into(binding.imgPreview)
            binding.edtProductImage.setText(uri.toString())
        }
    }

    private val scanBarcodeLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val intentResult = IntentIntegrator.parseActivityResult(result.resultCode, result.data)
        if (intentResult != null && intentResult.contents != null) {
            binding.edtItemCode.setText(intentResult.contents)
        } else {
            Toast.makeText(requireContext(), "Scan dibatalkan", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFormProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        categoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]
        data = FormProductFragmentArgs.fromBundle(arguments as Bundle).data

        categoryViewModel.categories.observe(viewLifecycleOwner) { response ->
            val categories = response.payload ?: emptyList()
            categoryList = categories
            val categoryNames = categories.map { it.name ?: "-" }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoryNames)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerCategory.adapter = adapter

            data?.let {
                val selectedIndex = categories.indexOfFirst { cat -> cat.id == it.categoryId }
                if (selectedIndex >= 0) {
                    binding.spinnerCategory.setSelection(selectedIndex)
                }
            }
        }

        data?.let {
            binding.edtName.setText(it.name)
            binding.edtItemCode.setText(it.itemCode)
            binding.edtStockQuantity.setText(it.stockQuantity.toString())
            binding.edtPurchasePrice.setText(it.purchasePrice.toString())
            binding.edtSellingPrice.setText(it.sellingPrice.toString())
            binding.edtProductImage.setText(it.productImage)
            if (!it.productImage.isNullOrEmpty()) {
                Glide.with(this).load(it.productImage).into(binding.imgPreview)
            }
            binding.btnDelete.visibility = View.VISIBLE
            binding.btnDelete.setOnClickListener {
                deleteData(view)
            }
        }

        binding.btnPickImage.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        binding.btnSave.setOnClickListener {
            if (validateForm()) {
                saveData(view)
            }
        }

        binding.btnScanBarcode.setOnClickListener {
            val integrator = IntentIntegrator.forSupportFragment(this)
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
            integrator.setPrompt("Arahkan kamera ke barcode")
            integrator.setCameraId(0)
            integrator.setBeepEnabled(true)
            integrator.setBarcodeImageEnabled(true)
            scanBarcodeLauncher.launch(integrator.createScanIntent())
        }

        viewModel.storeSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Produk berhasil disimpan", Toast.LENGTH_SHORT).show()
                navigateBack(view)
            }
        }

        viewModel.updateSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Produk berhasil diupdate", Toast.LENGTH_SHORT).show()
                navigateBack(view)
            }
        }

        viewModel.deleteSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Produk berhasil dihapus", Toast.LENGTH_SHORT).show()
                navigateBack(view)
            }
        }

        categoryViewModel.loadCategories()
    }

    private fun validateForm(): Boolean {
        var valid = true
        if (binding.edtName.text.isNullOrEmpty()) {
            binding.edtName.error = "Nama produk wajib diisi"
            valid = false
        }
        if (binding.edtItemCode.text.isNullOrEmpty()) {
            binding.edtItemCode.error = "Kode item wajib diisi"
            valid = false
        }
        if (categoryList.isEmpty()) valid = false
        if (binding.edtStockQuantity.text.isNullOrEmpty()) {
            binding.edtStockQuantity.error = "Stok wajib diisi"
            valid = false
        }
        if (binding.edtPurchasePrice.text.isNullOrEmpty()) {
            binding.edtPurchasePrice.error = "Harga beli wajib diisi"
            valid = false
        }
        if (binding.edtSellingPrice.text.isNullOrEmpty()) {
            binding.edtSellingPrice.error = "Harga jual wajib diisi"
            valid = false
        }
        return valid
    }

    private fun saveData(view: View) {
        val selectedCategory = categoryList.getOrNull(binding.spinnerCategory.selectedItemPosition)
        val product = Product(
            id = data?.id,
            name = binding.edtName.text.toString(),
            itemCode = binding.edtItemCode.text.toString(),
            categoryId = selectedCategory?.id ?: 0,
            stockQuantity = binding.edtStockQuantity.text.toString().toIntOrNull() ?: 0,
            purchasePrice = binding.edtPurchasePrice.text.toString().toBigDecimalOrNull() ?: BigDecimal.ZERO,
            sellingPrice = binding.edtSellingPrice.text.toString().toBigDecimalOrNull() ?: BigDecimal.ZERO,
            productImage = binding.edtProductImage.text.toString()
        )

        val imageFile = selectedImageUri?.let { uri ->
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            val tempFile = File.createTempFile("product_image", ".jpg", requireContext().cacheDir)
            inputStream?.use { input -> tempFile.outputStream().use { output -> input.copyTo(output) } }
            tempFile
        }

        if (data == null) {
            viewModel.storeProduct(product, imageFile)
        } else {
            viewModel.updateProduct(product, imageFile)
        }
    }

    private fun deleteData(view: View) {
        data?.id?.let {
            viewModel.deleteProduct(it)
        }
    }

    private fun navigateBack(view: View) {
        view.findNavController().navigate(
            FormProductFragmentDirections.actionFormProductFragmentToProductFragment()
        )
    }
}
