const express = require('express');
const router = express.Router();
const supplierController = require('../controllers/supplierController');
const verifyToken = require('../middleware/authMiddleware');
const authorizeRole = require('../middleware/authorizeRole');

router.get('/', verifyToken, authorizeRole('admin','gudang'), supplierController.getAllSuppliers);
router.get('/:id', verifyToken, authorizeRole('admin','gudang'), supplierController.getSupplierById);
router.post('/',verifyToken, authorizeRole('admin'),  supplierController.createSupplier);
router.put('/:id', verifyToken, authorizeRole('admin'), supplierController.updateSupplier);
router.delete('/:id', verifyToken, authorizeRole('admin'), supplierController.deleteSupplier);

module.exports = router;
