const express = require('express');
const router = express.Router();
const productController = require('../controllers/productController');
const verifyToken = require('../middleware/authMiddleware');
const authorizeRole = require('../middleware/authorizeRole');
const { uploadProductImage } = require('../middleware/upload');

router.get('/', verifyToken, authorizeRole('admin', 'gudang', 'kasir'),productController.getAllProducts);
router.get('/:id',verifyToken, authorizeRole('admin', 'gudang'),productController.getProductById);
router.delete('/:id', verifyToken, authorizeRole('admin', 'gudang'), productController.deleteProduct);

router.post('/',verifyToken, authorizeRole('admin', 'gudang'), uploadProductImage, productController.createProduct);
router.put('/:id',verifyToken, authorizeRole('admin', 'gudang'),uploadProductImage, productController.updateProduct);

module.exports = router;